package org.usfirst.frc.team3414.config;

import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Config {
	static Preferences prefs = Preferences.getInstance();
	/*public static double getIntakeThrottle(){
	//	double input = prefs.getDouble("Throttle?", .5);
		//OVERRIDE
		double input = .5;
		return input;
	}
	*/
	public static final double INTAKE_THROTTLE = .55;
	public static final int LEFT_FRONT = 1;
	//public static final int LEFT_MIDDLE = 2;
	public static final int LEFT_REAR = 2;
	public static final int RIGHT_FRONT = 4;
	//public static final int RIGHT_MIDDLE = 5;
	public static final int RIGHT_REAR = 5;
	
	public static final int LEFT_STICK = 0;
	public static final int RIGHT_STICK = 1;
	public static final int CONTROLLER_CHANNEL = 2;

	public static final int COMPRESSOR = 0;
	public static String autoFile = (Diagnostic.position+"-"+DriverStation.getInstance().getGameSpecificMessage());
	public static String getAutoFile(){
	return	Diagnostic.position+"-"+SmartDashboard.getString("auton", "defaultValue");
	}

	public static final int TUNNEL_TALON_TOP = 25;

	public static final int INTAKE_TALON = 41;
	public static final int INTAKE_PISTON = 0;
	public static final int INTAKE_PISTON_TWO = 1;
//TODO implement bottom tunnel is 51
//TODO SHould be 41
	public static final int HORIZONTAL_MANIPULATOR = 3;
	public static final int VERTICAL_MANIPULATOR = 2;
	
	public static final int CARGO_MOTOR_ONE = 51;
	public static final int CARGO_MOTOR_TWO = 45;
	
	public static final int CLIMBER_MOTOR_ONE = 21;
	public static final int CLIMBER_MOTOR_TWO = 31;
	public static final int CLIMBER_MOTOR_THREE = 11;
	
}
