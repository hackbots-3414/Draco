package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MultiMotor {
	TalonSRX front;
	TalonSRX middle;
	TalonSRX rear;
	int front_id;
	public MultiMotor(int front_channel,int middle_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		middle = new TalonSRX(middle_channel);
		rear = new TalonSRX(rear_channel);
		front_id = front_channel;

	}
	public MultiMotor(int front_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		middle = null;
		rear = new TalonSRX(rear_channel);
		front_id = front_channel;
		front.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	}
	public void set(double speed) {
		front.set(ControlMode.PercentOutput, speed);
		//middle.set(ControlMode.PercentOutput, speed);
		rear.set(ControlMode.Follower, front_id);
	}
	public void setFront(double speed) {
		front.set(ControlMode.PercentOutput, speed);
	}
	public void setMiddle(double speed) {
		//middle.set(ControlMode.PercentOutput, speed);
	}
	public void setRear(double speed) {
		rear.set(ControlMode.PercentOutput, speed);
	}
	public void setInverted(boolean inverted){
		front.setInverted(inverted);
	//	middle.setInverted(inverted);
		rear.setInverted(inverted);

	}
	//Encoder Specific Code Goes Here
	public void resetEncoder() {
		front.getSensorCollection().setQuadraturePosition(0, 10);
		}
	public int getEncoder(){
        return front.getSensorCollection().getQuadraturePosition();
		}
	public double getFront(){
		return front.getMotorOutputPercent();
	}
	public double getRear(){
		return front.getMotorOutputPercent();
	}
	public void setNeutralMode(NeutralMode mode){
	
		front.setNeutralMode(mode);
		rear.setNeutralMode(mode);
	}
}
