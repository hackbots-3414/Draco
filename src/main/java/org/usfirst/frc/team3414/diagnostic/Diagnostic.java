package org.usfirst.frc.team3414.diagnostic;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.teleop.Teleop;

public class Diagnostic {
	static boolean driveEnabled = false;
	static double speed = 0;
	static boolean recording;
	public static int position;
	public static boolean newController = false;
	public static String message;
	public static void checkInput() {
		DiagnosticServer.execute();
		if(DiagnosticServer.getInput().equalsIgnoreCase("exit")){
			message = "Diagnostic Stopped";
			System.out.println(message);
			reset();
			DiagnosticServer.sendMessage(message);
		}
	
		if(DiagnosticServer.getInput().equalsIgnoreCase("stop")) {
			message = "Diagnostic Stopped";
			System.out.println(message);
			reset();
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase("kill")) {
			message = "Killing Robot";
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
			System.exit(0);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "runmotor")) {
			message = "Use the command runmotor1, runmotor2";
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase("speed1")) {
			speed = 1;
			message = ("speed is "+speed);
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "speed0")) {
			speed = 0;
			message = ("speed is "+speed);
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "speed-1")) {
			speed = -1;
			message = ("speed is "+speed);
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase("runmotor1")){
			message = "Running Motor 1 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().left.setFront(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase("runmotor2")) {
			message = "Running Motor 2 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().left.setMiddle(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "runmotor3")) {
			message = "Running Motor 3 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().left.setRear(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "runmotor4")){
			message = "Running Motor 4 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().right.setFront(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "runmotor5")) {
			message = "Running Motor 5 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().right.setMiddle(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "runmotor6")) {
			message = "Running Motor 6 at " + (speed * 100) + "%.";
			System.out.println(message);
			DriveTrain.getInstance().left.setRear(speed);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "encoderleft")){
			message = "Gettting Encoder on Motor 1";
			System.out.println(message);
			DriveTrain.getInstance().left.getEncoder();
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "encoderRight")) {
			message = "Gettting Encoder on Motor 4";
			System.out.println(message);
			DriveTrain.getInstance().right.getEncoder();
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "encoderReset")){
			message = "Resetting Encoders";
			System.out.println(message);
			DriveTrain.getInstance().left.resetEncoder();
			DriveTrain.getInstance().right.resetEncoder();
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "enabledrive")) {
			message = "Enabling drivetrain";
			System.out.println(message);
			driveEnabled = true;
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "disabledrive")) {
			message = "Disabling drivetrain";
			System.out.println(message);
			driveEnabled = false;
			reset();
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "record")) {
			message = "Recording";
			System.out.println(message);
			recording = true;
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "stoprecord")) {
			message = "Recording Stopped";
			System.out.println(message);
			recording = false;
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "pos1")) {
			position = 1;
			message =  "Position "+position;
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "pos2")) {
			position = 2;
			message =  "Position "+position;
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "pos3")) {
			position = 3;
			message =  "Position "+position;
			System.out.println(message);
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "tradcontrol")) {
			message = "Got it, using traditional controls";
			System.out.println(message);
			newController = false;
			DiagnosticServer.sendMessage(message);
		}
		if(DiagnosticServer.getInput().equalsIgnoreCase( "newcontrol")) {
			message = "Got it, using new controls";
			System.out.println(message);
			newController = true;
		}
			}
		
	

	public static boolean isRunning() {
		if ((DiagnosticServer.getInput() == null) || (DiagnosticServer.getInput() == "exit")
				|| (DiagnosticServer.getInput() == "stop")) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean isNewControls() {
		return newController;
	}
	public static void reset() {
		speed = 0;
		driveEnabled = false;
		DriveTrain.getInstance().left.setFront(0);
		DriveTrain.getInstance().left.setMiddle(0);
		DriveTrain.getInstance().left.setRear(0);
		DriveTrain.getInstance().right.setFront(0);
		DriveTrain.getInstance().right.setMiddle(0);
		DriveTrain.getInstance().right.setRear(0);
		DriveTrain.getInstance().left.resetEncoder();
		DriveTrain.getInstance().right.resetEncoder();
		recording = false;
		newController = false;
	}

	public static void runTeleop() {
		if (driveEnabled) {
			Teleop.getInstance().drive();
		}
	}

	public static boolean isRecording() {
		return recording;
	}
}
