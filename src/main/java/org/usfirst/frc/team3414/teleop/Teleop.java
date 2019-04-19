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
import org.usfirst.frc.team3414.diagnostic.MatchTimer;
import org.usfirst.frc.team3414.sensors.CameraSwitcher;
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

	AutonReplayRecord auton = new AutonReplayRecord();
	Joystick left = new Joystick(Config.LEFT_STICK);
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller pad = new Controller();
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

	public void driverInfo() {
		MatchTimer.outputTime();
		LED.timeWarning();
		LED.lineLED();
	}

	public void record() throws IOException {

		if (pad.getRSButton() && pad.getLSButton()) {
			stopcounter++;
			auton.endRecording();
		}
	}

	public void replay() throws IOException {
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

	public void drive() {
		if(left.getRawButton(1) || right.getRawButton(1)){
			DriveTrain.getInstance().teleop(left.getY() * .3, right.getY() * .3);
		}
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
	}

	public void ball() {
		SmartDashboard.putNumber("POV", pad.getPov());
		boolean isBallMiddle = false;
		if (pad.getAButton() && (Tunnel.getInstance().getBallPos() == 0)) { // Turn on Intake, run tunnel
			Intake.getInstance().on();
			Intake.getInstance().goDown();
			Tunnel.getInstance().on();
		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 1 ) {
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().on();
			//Tunnel.getInstance().off();
			//At Kyle's request, tunnel is back to its original state, it keeps going until the ball gets up there
		}
		else if(pad.getAButton() && Tunnel.getInstance().getBallPos() == 2){
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		 else if (pad.getBButton()) {
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

	public void manipulator() {
				if (pad.getPov() == 0 || pad.getXPov() == 0) {
			HatchPanelManipulator.getInstance().setOut();
		} else if (pad.getXPov() == 180 || pad.getPov()  == 180) {
			HatchPanelManipulator.getInstance().setIn();
		} else if (pad.getLBButton()) {
			HatchPanelManipulator.getInstance().setClosed(); //Traditional
			HatchPanelManipulator.getInstance().setOverride(true);
		} else if (pad.getLT()) {
			HatchPanelManipulator.getInstance().setOpen(); //Traditional
			HatchPanelManipulator.getInstance().setOverride(true);
		}
		else if (pad.getRT()){
			/*
			HatchPanelManipulator.getInstance().setOverride(false); //Pettengil button controls
			HatchPanelManipulator.getInstance().setOpenAssisted();
			*/
		}

		
	}

	public void climber() {
		//Want to shave off time? Change the second parameter(margin) to a smaller value. Risk is the robot doesn't get as high as you want
		if (right.getRawButton(6) && left.getRawButton(6)) { //Top Climb
			Climber.getInstance().climb(16500, 14500,.6); //Should be 16000 on alpha. 
		} else if (left.getRawButton(7) && right.getRawButton(7)) { //Lower Climb
			// Climber.getInstance().motionmagicclimberMidplatform();
			Climber.getInstance().climb(6000, 4500,.6);
		}
		else if(left.getRawButton(10) && right.getRawButton(10)){
			Climber.getInstance().climb(11500,10500,.4); //Lower to Top
		}
		else if(left.getRawButton(11) && right.getRawButton(11)){
			Climber.getInstance().climb(11500, 10500, 0); //Lower to Top with operator control.
		}
		/*
		if (left.getRawButton(8) && right.getRawButton(8)) { //Encoder Climb Don't worry about this one for LEDs
			Climber.getInstance().percentOutputClimber();
		} */
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
	public AnalogInput getLineSensor(){
		return lineSensor;
	}

	public void align() {
		if (right.getRawButton(3) && left.getRawButton(3)) {
			// while(!left.getRawButton(Config.ESCAPE_BUTTON)){
			Limelight.compMode();
			LimeLightUtil.driveToTarget(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),
					lineSensor);
			// }
		} else {
			DriveTrain.getInstance().setBlock(false);
			Limelight.pitMode();
		}
		if (right.getRawButton(4) && left.getRawButton(4)) {
			LimeLightUtil.findTheLineLeft(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),lineSensor);
		}
		if (right.getRawButton(5) && right.getRawButton(5)) {
			LimeLightUtil.findTheLineRight(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(),irLeft, irRight, Teleop.getInstance().getRightJoystick(),lineSensor);
		}
		if (right.getRawButton(2) && left.getRawButton(2)) {
			LimeLightUtil.straightenRobotToTarget(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick());
		}

	}

	public Joystick getLeftJoystick() {
		return left;
	}

	public Joystick getRightJoystick() {
		return right;
	}

	public void replaySystem() {
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

	public void camera() {
		// Limelight.init();
		CameraSwitcher.init();
		if (pad.getXButton()) {
			CameraSwitcher.setFront();
		//	Limelight.rearView();
		} else if (pad.getYButton()) {
			CameraSwitcher.setRear();
			//Limelight.frontView();
		} else {
		//	Limelight.defaultView();
		}
	}

	public void stopAll() {
		DriveTrain.getInstance().set(0, 0);
		Intake.getInstance().stop();
		Tunnel.getInstance().stop();
		Climber.getInstance().stop();
	}

	public void vacuum() {
		HBVacuum.getInstance().periodic();
		if(pad.getLBButton()){ //Imported Release
			HBVacuum.getInstance().releaseGamePiece();
		}
		else if(pad.getLT()){ //modified grab
			HBVacuum.getInstance().grab();
		}
		else if(pad.getRT()){
			HBVacuum.getInstance().holdGamePiece();
		}

	}

	public Controller getController() {
		return pad;
	}

	public void arm() {
		//if (pad.getPov() == 0 || pad.getXPov() == 0) {
			if(pad.getXPov() == 0){
			Arm.getInstance().setOut();
			
		} else if (pad.getXPov() == 180 || pad.getPov()  == 180) {
			Arm.getInstance().setIn();
	}
}
}
