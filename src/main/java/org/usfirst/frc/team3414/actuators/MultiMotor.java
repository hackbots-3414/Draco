package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MultiMotor {
	TalonSRX front;
	TalonSRX middle;
	TalonSRX rear;
	
	public MultiMotor(int front_channel,int middle_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		middle = new TalonSRX(middle_channel);
		rear = new TalonSRX(rear_channel);

	}
	public MultiMotor(int front_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		rear = new TalonSRX(rear_channel);
	}
	public void set(double speed) {
		front.set(ControlMode.PercentOutput, speed);
		middle.set(ControlMode.PercentOutput, speed);
		rear.set(ControlMode.PercentOutput, speed);
	}
	public void setFront(double speed) {
		front.set(ControlMode.PercentOutput, speed);
	}
	public void setMiddle(double speed) {
		middle.set(ControlMode.PercentOutput, speed);
	}
	public void setRear(double speed) {
		rear.set(ControlMode.PercentOutput, speed);
	}
	//Encoder Specific Code Goes Here
	public void resetEncoder() {
		
	}
	public double getEncoder() {
		return 0;
	}
}
