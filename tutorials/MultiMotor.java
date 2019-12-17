

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MultiMotor {
	TalonSRX front;
	TalonSRX middle;
	TalonSRX rear;
	public MultiMotor(int front_channel,int middle_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		middle = new TalonSRX(middle_channel);
		rear = new TalonSRX(rear_channel);
		front_id = front.getDeviceID();

	}
	public MultiMotor(int front_channel, int rear_channel) {
		front = new TalonSRX(front_channel);
		middle = null;
		rear = new TalonSRX(rear_channel);
		front_id = front.getDeviceID();
		front.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	}
	public void set(double speed) {
		front.set(ControlMode.PercentOutput, speed);
		rear.set(ControlMode.Follower, front_id);
		if(middle != null){
			middle.set(ControlMode.Follower, front_id);
		}
	}
	public void setFront(double speed) {
		front.set(ControlMode.PercentOutput, speed);
	}
	public void setMiddle(double speed) {
		if(middle != null){
			middle.set(ControlMode.PercentOutput, speed);
		}
	}
	public void setRear(double speed) {
		rear.set(ControlMode.PercentOutput, speed);
	}
	public void setInverted(boolean inverted){
		front.setInverted(inverted);
		rear.setInverted(inverted);
		if(middle != null){
			middle.setInverted(inverted);
		}

	}
	//Encoder Specific Code Goes Here
	public void resetEncoder() {
		front.getSensorCollection().setQuadraturePosition(0, 10);
		}
	public int getEncoder(){
        return front.getSensorCollection().getQuadraturePosition();
		}
	public double getFrontSpeed(){
		return front.getMotorOutputPercent();
	}
	public double getRearSpeed(){
		return front.getMotorOutputPercent();
	}
	public double getMiddleSpeed(){
		return middle.getMotorOutputPercent();
	}

	public TalonSRX getFrontTalon(){
		return front;
	}
	public TalonSRX getMiddleTalon(){
		return front;
	}
	public TalonSRX getRearTalon(){
		return front;
	}
	public void setNeutralMode(NeutralMode mode){
	
		front.setNeutralMode(mode);
		rear.setNeutralMode(mode);
		if(middle != null){
			middle.setNeutralMode(mode);
		}
	}
}
