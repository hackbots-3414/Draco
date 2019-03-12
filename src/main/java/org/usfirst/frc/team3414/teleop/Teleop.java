package org.usfirst.frc.team3414.teleop;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.MotionMagicClimb;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.auton.Align;
import org.usfirst.frc.team3414.auton.Auton;
import org.usfirst.frc.team3414.auton.MoveStraight;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.sensors.LimeLightUtil;
import org.usfirst.frc.team3414.sensors.Limelight;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	private static Teleop instance;

	public static Teleop getInstance() {
		if (instance == null) {
			instance = new Teleop();
		}

		return instance;

	}

	Auton auton = new Auton();
	Joystick left = new Joystick(Config.LEFT_STICK);
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller pad = new Controller(Config.CONTROLLER_CHANNEL);

	AnalogInput irLeft = new AnalogInput(Config.LEFT_IR);
	AnalogInput irRight = new AnalogInput(Config.RIGHT_IR);
	DigitalInput lineSensor; 		 // = new DigitalInput(Config.LINE_SENSOR);

	int rbpresses = 0;
	int recordcounter = 0;
	int replaycounter = 0;
	int stopcounter = 0;
	public void freeDriveTrain() {
		if (left.getRawButton(1)) {
			DriveTrain.getInstance().setBlock(false); // Emergency release for drivetrain lockout in case somebody
														// messes up calling it elsewhere.
		}
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
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
	}

	public void ball() {
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
		if (pad.getPov() == 0) {
			HatchPanelManipulator.getInstance().setOut();
		} else if (pad.getPov() == 180) {
			HatchPanelManipulator.getInstance().setIn();
		} else if (pad.getLBButton()) {
			HatchPanelManipulator.getInstance().setClosed();
		} else if (pad.getLT()) {
			HatchPanelManipulator.getInstance().setOpen();
		}
	}

	public void climber() {

		if (right.getRawButton(6) && left.getRawButton(6)) {
			//Climber.getInstance().motionmagicclimber(); // Brings the climber up to whatever height it needs to be
			Climber.getInstance().newTopClimb();
		} else if (left.getRawButton(7) && right.getRawButton(7)) {
			Climber.getInstance().motionmagicclimberMidplatform();
		} else if (left.getRawButton(8) && right.getRawButton(8)) {
			Climber.getInstance().percentOutputClimber();
		}
	}

	public double getLeftJoy() {
		return left.getY();
	}

	public double getRightJoy() {
		return right.getY();
	}

	public void align() {
		if (right.getRawButton(3)) {
			Limelight.compMode();
			LimeLightUtil.driveToTarget(DriveTrain.getInstance().getLeftMotor(),
					DriveTrain.getInstance().getRightMotor(), irLeft, irRight, Teleop.getInstance().getRightJoystick(),
					lineSensor);
		} else {
			DriveTrain.getInstance().setBlock(false);
			Limelight.pitMode();
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
				// TODO Auto-generated catch block
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
		Limelight.init();
		if (pad.getXButton()) {
			Limelight.rearView();
		} else if (pad.getYButton()) {
			Limelight.frontView();
		} else {
			Limelight.defaultView();
		}
	}
}
