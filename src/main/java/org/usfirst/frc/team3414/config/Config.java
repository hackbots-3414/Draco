package org.usfirst.frc.team3414.config;

import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Config {
	//public static final double INTAKE_THROTTLE = .65;
	public static double intake_throttle(){
		//return SmartDashboard.getNumber("Throttle %", .65);
		return .75;
	}
	public static final boolean REPLAY_MODE = false;
	public static boolean PIT_MODE = true; 

	public static final int LEFT_STICK = 0;
	public static final int RIGHT_STICK = 1;
	public static final int CONTROLLER_CHANNEL = 2;

	public static final int LEFT_FRONT = 1;
	public static final int LEFT_REAR = 2;
	public static final int RIGHT_FRONT = 4;
	public static final int RIGHT_REAR = 5;

	public static String getAutoFile() {
		return "/home/lvuser/Output.txt";
	}

	public static final int TUNNEL_TALON_TOP = 25;

	public static final int INTAKE_TALON = 41;
	public static final int INTAKE_PISTON = 0;
	public static final int INTAKE_PISTON_TWO = 1;
	// TODO implement bottom tunnel is 51
	// TODO SHould be 41
	public static final int HORIZONTAL_MANIPULATOR_IN = 2;
	public static final int HORIZONTAL_MANIPULATOR_OUT = 3;
	public static final int VERTICAL_MANIPULATOR_DOWN = 5;
	public static final int VERTICAL_MANIPULATOR_UP = 4;

	public static final int CARGO_MOTOR_ONE = 51;
	public static final int CARGO_MOTOR_TWO = 45;

	public static final int TOP_REAR_CLIMBER_OFFSET     = 1500; //1500;
	public static final int MID_FRONT_CLIMBER_OFFSET = -400; //S2500;

	public static final int TOP_REAR_TARGET = 0;
	public static final int MID_REAR_TARGET = 0;

	public static final int CLIMBER_FRONT = 21;
	public static final int CLIMBER_REAR = 31;
	
	public static final int CLIMBER_MOTOR_THREE = 11;
	public static final int CLIMBER_FRONT_SENSOR = 3;
	public static final int CLIMBER_REAR_SENSOR = 2;
//TODO change 30 to 11
	public static final int BALL_SENSOR_TOP = 1;
	public static final int BALL_SENSOR_BOTTOM = 0;

	public static final int LEFT_IR = 0;
	public static final int RIGHT_IR = 1;
	public static final int LINE_SENSOR = 2;
	public static final int ESCAPE_BUTTON = 1;
	public static final double LEVEL_2_TIME = 0;
	public static final double LEVEL_3_TIME = 0;
	
}
