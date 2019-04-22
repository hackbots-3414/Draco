package org.usfirst.frc.team3414.actuators;

import java.io.IOException;

import org.usfirst.frc.team3414.auton.AutonReplayRecord;
import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {

	double leftJoySpeed = 0;
	double rightJoySpeed = 0;
	private static DriveTrain instance;
	public MultiMotor left = new MultiMotor(Config.LEFT_FRONT, Config.LEFT_REAR);
	public MultiMotor right = new MultiMotor(Config.RIGHT_FRONT, Config.RIGHT_REAR);
   /*
	AnalogInput longRangeIRLeft = new AnalogInput(0);
	AnalogInput longRangeIRRight = new AnalogInput(1);
	AnalogInput lineSensor = new AnalogInput(2);
*/
AnalogInput longRangeIRLeft;
AnalogInput longRangeIRRight;
AnalogInput lineSensor;
	public void autocorrect() {
		left.set(0);
		right.set(0);
		// Timer.delay(1);
		// Back up robot to white line if it was passed over
		while (lineSensor.getAverageVoltage() < 1) {
			left.set(-0.10);
			right.set(-0.10);
		}
		// Stop Movement
		left.set(0);
		right.set(0);
		// Rotate robot to left until straight towards target
		while ((longRangeIRLeft.getAverageVoltage() - longRangeIRRight.getAverageVoltage()) > 0.05) {
			right.set(-0.25);
			left.set(0.25);
		}
		// Rotate robot to right until straight towards target
		while ((longRangeIRRight.getAverageVoltage() - longRangeIRLeft.getAverageVoltage()) > 0.05) {
			right.set(0.25);
			left.set(-0.25);
		}
		// Timer.delay(1);
	}

	boolean blocked = false;

	public void setBlock(boolean block) {
		blocked = block;
	}
	boolean isrecording = Config.REPLAY_MODE;
	public void teleop(double leftSpeed, double rightSpeed) { 
		left.set(leftSpeed);
		right.set(rightSpeed);
		if(driveState != state.teleop){ //sets the state to teleop
			driveState = state.teleop;
		}
		if (isrecording) {
			try {
				AutonReplayRecord.getInstance().record(leftSpeed, rightSpeed);
			} catch (IOException e) {
				e.printStackTrace();
			}

			leftJoySpeed = leftSpeed;
			rightJoySpeed = rightSpeed;
		}
	}



	public void set(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
	}

	public void teleopLimit(double limit, double leftSpeed, double rightSpeed) {
		left.set(leftSpeed * limit);
		right.set(rightSpeed * limit);

	}

	public void setLeft(double speed) {
		left.set(speed);
	}

	public void setRight(double speed) {
		right.set(speed);
	}

	public double getLeft() {
		return leftJoySpeed;
	}

	public double getRight() {
		return rightJoySpeed;
	}

	public MultiMotor getLeftMotor() {
		return left;
	}

	public MultiMotor getRightMotor() {
		return right;
	}

	public int getRightEncoder() {
		return right.getEncoder();
	}

	public int getLeftEncoder() {
		return left.getEncoder();
	}
	public void resetEncoders(){
		left.resetEncoder();
		right.resetEncoder();
	}

	public void init() {
		left.setInverted(true);

	}

	public static DriveTrain getInstance() {
		if (instance == null) {
			instance = new DriveTrain();
		}

		return instance;

	}

	public void stop() {
		teleop(0, 0);
	}
	public enum state {
		teleop,automated,assisted;
	}
	private state driveState = state.teleop;
	int tolerance = Config.DRIVE_STRAIGHT_TOLERANCE;
	int error = 0;
	public void driveStraight(double leftSpeed, double rightSpeed){
	if(driveState == state.teleop){
		driveState = state.assisted; //If was previously in teleop mode, reset encoders for assisted mode
		resetEncoders();
	}
	 error = getRightEncoder() - getLeftEncoder(); //Positive means right bias. Negative means left bias
	 if(error > tolerance){ //Right bias
		 left.set(leftSpeed);
		 right.set(0);
	 }
	 if(error < tolerance && error < 0) { //Left bias
		right.set(rightSpeed);
		left.set(0);
	 }
	 else{
		 set(leftSpeed, rightSpeed);
	 }

	}

	public void diagnostic() {
		SmartDashboard.putNumber("Left Motor Front %:", left.getFront());
		SmartDashboard.putNumber("Left Motor Rear %:", left.getRear());
		SmartDashboard.putNumber("Right Motor Front %:", right.getFront());
		SmartDashboard.putNumber("Right Motor Rear %:", right.getRear());
		SmartDashboard.putBoolean("Teleop Blocked?", blocked); // Blocking is being temporily disabled in favor of while
																// looping
	}
}
