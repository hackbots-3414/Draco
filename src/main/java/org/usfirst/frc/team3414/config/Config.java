package org.usfirst.frc.team3414.config;

import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Config {

	public static final int LEFT_STICK = 0;
	public static final int RIGHT_STICK = 1;
	public static final int CONTROLLER_CHANNEL = 2;
	//Values that anyone can change
	public static final double INTAKE_THROTTLE = .75; //Originally .75. 
	public static final boolean PIT_STREAM = true; //Reads the Limelight as an IPCamera, used in pitmode. Will add a small delay during comp. 
	
	
	//Various modes, Replay has been removed
	public static final boolean REPLAY_MODE = false;
	public static boolean PIT_MODE = true;
	//Drivetrain Talons
	public static final int LEFT_FRONT = 1;
	public static final int LEFT_REAR = 2;
	public static final int RIGHT_FRONT = 4;
	public static final int RIGHT_REAR = 5;

	public static String getAutoFile() {
		return "/home/lvuser/Output.txt";
	}

	//Intake Solenoid and Talons
	public static final int INTAKE_TALON = 41;
	public static final int INTAKE_PISTON = 0;
	public static final int INTAKE_PISTON_TWO = 1;
	//Manipulator Solenoid IDs
	public static final int HORIZONTAL_MANIPULATOR_IN = 2;
	public static final int HORIZONTAL_MANIPULATOR_OUT = 3;
	public static final int VERTICAL_MANIPULATOR_DOWN = 5;
	public static final int VERTICAL_MANIPULATOR_UP = 4;
	public static final int MANIPULATOR_BUTTON_CHANNEL = 4;

	//Tunnel Talons
	public static final int TUNNEL_TALON_TOP = 25;
	public static final int CARGO_MOTOR_ONE = 51;
	public static final int CARGO_MOTOR_TWO = 45;

	//Deprecated Offsets and targeting system
	public static final int TOP_REAR_CLIMBER_OFFSET = 1500; // 1500;
	public static final int MID_FRONT_CLIMBER_OFFSET = -400; // S2500;

	
	//Climber Talons
	public static final int CLIMBER_FRONT = 21;
	public static final int CLIMBER_REAR = 31;

	public static final int CLIMBER_MOTOR_THREE = 11;

	//Digital Inputs
	public static final int CLIMBER_FRONT_SENSOR = 3;
	public static final int CLIMBER_REAR_SENSOR = 2;
	public static final int BALL_SENSOR_TOP = 1;
	public static final int BALL_SENSOR_BOTTOM = 0;

	//Analog Inputs
	public static final int LEFT_IR = 1;
	public static final int RIGHT_IR = 0;
	public static final int LINE_SENSOR = 2;

	//Autonomous Functions
	public static final int ESCAPE_BUTTON = 1;
	public static final double LEVEL_2_TIME = 10;
	public static final double LEVEL_3_TIME = 10;

	//Vacuum 
	public static final int VACUUM_TALON = 52 ; //52  6
	public static final int VENT_SOLENOID = 6; //change back to 6
	public static final int VACUUM_PUMP_SPIN_UP_TIME = 500;
	public static final double VACUUM_GAME_PIECE_DETECTED_CONDUCTANCE = 0.29; //.2 , ours was .3 //Bigger number
	public static final double VACUUM_MOTOR_PRESENT_CONDUCTANCE = 0.03; //.05 , ours was .6 //Smaller Number
	public static final double VACUUM_INITIAL_HOLD_SPEED = .50; //1
	public static final double VACUUM_SUSTAIN_HOLD_SPEED = 0.1; //.2
	public static final double VACUUM_SOLENOID_ON_TIME_TO_VENT_VACUUM = 2500; //5000
	public static int GAME_PIECE_DETECTION_MINIMUM_COUNTS = 20;

}
