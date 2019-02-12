package org.usfirst.frc.team3414.config;

import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Config {
	public static final int LEFT_FRONT = 1;
	//public static final int LEFT_MIDDLE = 2;
	public static final int LEFT_REAR = 2;
	public static final int RIGHT_FRONT = 4;
	//public static final int RIGHT_MIDDLE = 5;
	public static final int RIGHT_REAR = 5;
	
	public static final int LEFT_STICK = 0;
	public static final int RIGHT_STICK = 1;
	public static final int CONTROLLER_CHANNEL = 2;

	public static final int COMPRESSOR = 60;
	public static String autoFile = (Diagnostic.position+"-"+DriverStation.getInstance().getGameSpecificMessage());
	public static String getAutoFile(){
	return	Diagnostic.position+"-"+SmartDashboard.getString("auton", "defaultValue");
	}

	public static final int TUNNEL_TALON_BOTTOM = 51;
	public static final int TUNNEL_TALON_TOP = 52;

	public static final int INTAKE_TALON = 41;
	public static final int INTAKE_PISTON = 3;

	public static final int MANIPULATOR_ONE = 7;
	public static final int MANIPULATOR_TWO = 0;
	
	public static final int CARGO_MOTOR_ONE = 12;
	public static final int CARGO_MOTOR_TWO = 13;
	
	public static final int CLIMBER_MOTOR_ONE = 34;
	public static final int CLIMBER_MOTOR_TWO = 72;
	public static final int CLIMBER_MOTOR_THREE = 11;
	
}
