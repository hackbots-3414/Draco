package org.usfirst.frc.team3414.teleop;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.Arm;
import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HBVacuum;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.MotionMagicClimb;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.auton.Align;
import org.usfirst.frc.team3414.auton.AutonReplayRecord;
import org.usfirst.frc.team3414.auton.MoveStraight;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.LED;
import org.usfirst.frc.team3414.diagnostic.LEDColor;
import org.usfirst.frc.team3414.diagnostic.MatchTimer;
import org.usfirst.frc.team3414.sensors.CameraSwitcher;
import org.usfirst.frc.team3414.sensors.Lifecam;
import org.usfirst.frc.team3414.sensors.LimeLightUtil;
import org.usfirst.frc.team3414.sensors.Limelight;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	private static Teleop instance;

	public static Teleop getInstance() {
		if (instance == null) {
			instance = new Teleop();
		}

		return instance;

	}

	/** 
	 * START HERE
	 * Below you'll find the stuff that is properly called, and we deemed at one point necessary to teleop. The AnalogInputs and AutonReplayRecord are probably not, but I won't
	 * remove them without looking at the hardware first.
	 * 
	 */
	AutonReplayRecord auton = new AutonReplayRecord();
	Joystick left = new Joystick(Config.LEFT_STICK); 
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller pad = new Controller(); //See this class for comments
	AnalogInput irLeft = new AnalogInput(Config.LEFT_IR);
	AnalogInput irRight = new AnalogInput(Config.RIGHT_IR);
	AnalogInput lineSensor = new AnalogInput(Config.LINE_SENSOR);

	int rbpresses = 0;
	int recordcounter = 0;
	int replaycounter = 0;
	int stopcounter = 0;

	public void freeDriveTrain() {
		if (left.getRawButton(1) || right.getRawButton(1)) {
			DriveTrain.getInstance().setBlock(false); // Emergency release for drivetrain lockout in case somebody
														// messes up calling it elsewhere.
		}
	}

	public void driverInfo() { //Should always be called, this gives us all the driver info via LEDs or dashboard
		MatchTimer.outputTime(); //This puts the Match Time on the dashboard. It does not need to be in its own class but that's just the way it was left.
		LED.timeWarning(); //driverInfo is run periodically, which means the LEDs check for time every iteration.
		LED.lineLED(); //Every iteration, check for line contact via ground IR sensors.
	}
	@Deprecated
	public void record() throws IOException { //We don't use this method anymore :)

		if (pad.getRSButton() && pad.getLSButton()) {
			stopcounter++;
			auton.endRecording();
		}
	}
	@Deprecated
	public void replay() throws IOException { //We don't use this method anymore :)
		if (pad.getXButton()) {
			auton.replayInit();
		}
		if (pad.getRT()) {
			auton.replay();
		}
		if (pad.getRBButton()) {
			stopcounter++;
			auton.endReplay();
		}
	}

	public void drive() { //Actual drivetrain conrtrols go right here. 
		if (left.getRawButton(1) || right.getRawButton(1)) { //If both triggers are pressed, (1 is the trigger) we lower the rate to 34.14%
			DriveTrain.getInstance().teleop(left.getY() * .3414, right.getY() * .3414);
			LED.blink(LEDColor.PURPLE, LEDColor.WHITE); //To indicate this, LEDs blink purple.
		} else if (left.getRawButton(2) || right.getRawButton(2)) { //If we want to do a straight drive with no sensors, but just even motor power, we hold the 2 button
			System.out.println("Average Mode");
			DriveTrain.getInstance().driveStraight((left.getY() * .4 + right.getY() * .4) / 2); //Average of both joysticks at 40%
			LED.set(LEDColor.YELLOW); //Yellow to indicate average mode
			// DriveTrain.getInstance().teleop((left.getY() + right.getY()) /2)
		} else 

			DriveTrain.getInstance().teleop(left.getY(), right.getY()); //Normal drive mode. 1 joystick = 1 side, 1 speed on the joystick = 1 motor speed
		}
	
	boolean tunnelClear = true;
	public void ball() { //Tunnel handling (we should really call this tunnel)
		//Filled with a lot of fancy logic to raise the arm automatically
		if(pad.getAButtonReleased()){ //SUPPOSED to reset everything is you press the A button (lower arm and suck in ball), WPILib doesn't consistenly work with this.
			tunnelClear = true;
		}
		SmartDashboard.putNumber("POV", pad.getPov()); //Diagnostic feature that comes up too often to easily remove :). Puts the D-PAD position on screen.
		boolean isBallMiddle = false;
		if (pad.getAButton() && (Tunnel.getInstance().getBallPos() == 0)) { // Turn on Intake, run tunnel. This means there's no ball anywhere and we want 1. 
			Intake.getInstance().on();
			Intake.getInstance().goDown();
			Tunnel.getInstance().on();
		}
		 else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 1 && tunnelClear) { //BALL NEEDS TO STOP HERE, it has reached the lower threshold.
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().on();
			Tunnel.getInstance().off(); // Stops the ball at 1 and will stop at 2 anyway
			tunnelClear = false;

		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 2) { //Just in case the ball makes it past the lower threshold and is still moving.
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		} else if (pad.getBButton()) { //This just makes the ball move through the tunnel.
			Intake.getInstance().off();
			Tunnel.getInstance().on();
		} else {
			Intake.getInstance().goUp(); //When nobody's pressing anything, we don't do anything. 
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		if (pad.getYButton()) { //Legacy arm control pre-automation shown above.
			// Intake.getInstance().goUp(); //No longer works because it is part of the else
			// for the A button
		}
	}

	public void legacyBall() {
		SmartDashboard.putNumber("POV", pad.getPov());
		boolean isBallMiddle = false;
		if (pad.getAButton() && (Tunnel.getInstance().getBallPos() == 0)) { // Turn on Intake, run tunnel
			Intake.getInstance().on();
			Intake.getInstance().goDown();
			Tunnel.getInstance().on();
		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 1) {
			Intake.getInstance().goUp();
			Tunnel.getInstance().on();
			isBallMiddle = true;
		} else if ((pad.getAButton() && (Tunnel.getInstance().getBallPos() != 2)) && isBallMiddle) {
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().on();
		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 2) {
			isBallMiddle = false;
			Intake.getInstance().off();
			Intake.getInstance().goUp();
			Tunnel.getInstance().off();
		} else if (pad.getBButton()) {
			Intake.getInstance().off();
			Tunnel.getInstance().on();
		} else {
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		if (pad.getYButton()) {
			// Intake.getInstance().goUp(); //No longer works because it is part of the else
			// for the A button
		}
	}

	public void manipulator() { //Legacy Manipulator code
		if (pad.getPov() == 0 || pad.getXPov() == 0) {
			HatchPanelManipulator.getInstance().setOut();
		} else if (pad.getXPov() == 180 || pad.getPov() == 180) {
			HatchPanelManipulator.getInstance().setIn();
		} else if (pad.getLBButton()) {
			HatchPanelManipulator.getInstance().setClosed(); // Traditional
			HatchPanelManipulator.getInstance().setOverride(true);
		} else if (pad.getLT()) {
			HatchPanelManipulator.getInstance().setOpen(); // Traditional
			HatchPanelManipulator.getInstance().setOverride(true);
		} else if (pad.getRT()) {
			/*
			 * HatchPanelManipulator.getInstance().setOverride(false); //Pettengil button
			 * controls HatchPanelManipulator.getInstance().setOpenAssisted();
			 */
		}

	}

	public void climber() {
		// Want to shave off time? Change the second parameter(margin) to a smaller
		// value. Risk is the robot doesn't get as high as you want
		if (right.getRawButton(6) && left.getRawButton(6)) { // Top Climb
			Climber.getInstance().climb(16000, 14500, .6); // Should be 16000 on alpha. 16500 on beta
		} else if (left.getRawButton(7) && right.getRawButton(7)) { // Lower Climb
			// Climber.getInstance().motionmagicclimberMidplatform();
			Climber.getInstance().climb(6000, 4500, .6);
		} else if (left.getRawButton(10) && right.getRawButton(10)) {
			Climber.getInstance().climb(11500, 10500, .4); // Lower to Top
		} else if (left.getRawButton(11) && right.getRawButton(11)) {
			Climber.getInstance().climb(11500, 10500, 0); // Lower to Top with operator control.
		}
		/*
		 * if (left.getRawButton(8) && right.getRawButton(8)) { //Encoder Climb Don't
		 * worry about this one for LEDs Climber.getInstance().percentOutputClimber(); }
		 */
	}

	public double getLeftJoy() {
		return left.getY();

	}

	public double getRightJoy() {
		return right.getY();
	}

	public boolean getEscape() {
		return !left.getRawButton(Config.ESCAPE_BUTTON) || !right.getRawButton(Config.ESCAPE_BUTTON);
	}

	public AnalogInput getLeftIR() {
		return irLeft;
	}

	public AnalogInput getRightIR() {
		return irRight;
	}

	public AnalogInput getLineSensor() {
		return lineSensor;
	}

	public void align() { //Legacy alignment code
		if (right.getRawButton(4) && left.getRawButton(4)){
			// while(!left.getRawButton(Config.ESCAPE_BUTTON)){
				while(!left.getRawButton(Config.ESCAPE_BUTTON)){
			Limelight.compMode();
			LimeLightUtil.turnToTarget(DriveTrain.getInstance().getLeftMotor(), DriveTrain.getInstance().getRightMotor());
			LED.set(LEDColor.WHITE);
					}	/*
			LimeLightUtil.driveToTarget(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),
					lineSensor);
			LED.set(LEDColor.WHITE);
			// }
			*/
		} else {
			DriveTrain.getInstance().setBlock(false);
			Limelight.pitMode();
		}
		/*}
		if (right.getRawButton(4) && left.getRawButton(4)) {
			LimeLightUtil.findTheLineLeft(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),
					lineSensor);
		
		if (right.getRawButton(5) && right.getRawButton(5)) {
			LimeLightUtil.findTheLineRight(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),
					lineSensor);
		}
		if (right.getRawButton(2) && left.getRawButton(2)) {
			LimeLightUtil.straightenRobotToTarget(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick());
		} */

	}

	public Joystick getLeftJoystick() {
		return left;
	}

	public Joystick getRightJoystick() {
		return right;
	}
	@Deprecated
	public void replaySystem() { //We don't use this anymore
		if (!Config.REPLAY_MODE) {
			try {
				record();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (Config.REPLAY_MODE) {
			try {
				replay();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void camera() { //Switches the camera.
		if (left.getRawButtonReleased(3) || right.getRawButtonReleased(3) || pad.getYButtonReleased()) {
			if (CameraSwitcher.getState() == CameraSwitcher.state.lifecam) {
				CameraSwitcher.setLimelight();
			} else if (CameraSwitcher.getState() == CameraSwitcher.state.limelight) {
				CameraSwitcher.setLifecam();
			} else {
			}
		}
	}

	public void stopAll() { //Stops all Talons, especially the climber. If a robot is disabled, a motor can still hold a value and once reenabled, 
		//if that value isn't changed, it will continue as it was before. This is why after disabling, a climber would still climb
		DriveTrain.getInstance().set(0, 0);
		Intake.getInstance().stop();
		Tunnel.getInstance().stop();
		Climber.getInstance().stop();
	}

	public void vacuum() {
		HBVacuum.getInstance().periodic();
		if (pad.getLBButton()) { // Imported Release
			HBVacuum.getInstance().releaseGamePiece();
		} else if (pad.getLT()) { // modified grab
			HBVacuum.getInstance().grab();
		} else if (pad.getRT()) {
			HBVacuum.getInstance().holdGamePiece();
		} else if (pad.getRBButton()) {
			HBVacuum.getInstance().releaseGamePiece();
		} else {
			HBVacuum.getInstance().closeVent();
		}

	}

	public Controller getController() { //Gets the controller object.
		return pad;
	};

	public void arm() {
		// if (pad.getPov() == 0 || pad.getXPov() == 0) {
		if (pad.getXPov() == 0) {
			Arm.getInstance().setOut();

		} else if (pad.getXPov() == 180 || pad.getPov() == 180) {
			Arm.getInstance().setIn();
		}
	}
}