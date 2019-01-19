package org.usfirst.frc.team3414.actuators;

import org.usfirst.frc.team3414.config.Config;

public class DriveTrain {
	double leftJoySpeed = 0;
	double rightJoySpeed = 0;
	private static DriveTrain instance;
	public MultiMotor left = new MultiMotor(Config.LEFT_FRONT,Config.LEFT_MIDDLE,Config.LEFT_REAR);
	public MultiMotor right = new MultiMotor(Config.RIGHT_FRONT,Config.RIGHT_MIDDLE,Config.RIGHT_REAR);
	

	public void teleop(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
		leftJoySpeed = leftSpeed;
		rightJoySpeed = rightSpeed;
	}
	public void teleopLimit(double limit, double leftSpeed, double rightSpeed) {
		left.set(leftSpeed*limit);
		right.set(rightSpeed*limit);

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
		public static DriveTrain getInstance()
		{
			if(instance == null)
			{
				instance = new DriveTrain();
			}
			
			return instance;
			
	}
		public void stop() {
			teleop(0,0);
		}
}
