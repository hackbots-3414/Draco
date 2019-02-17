package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	private static Teleop instance;

	public static Teleop getInstance() {
		if (instance == null) {
			instance = new Teleop();
		}

		return instance;

	}

	Joystick left = new Joystick(Config.LEFT_STICK);
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller pad = new Controller(Config.CONTROLLER_CHANNEL);
	public void drive() {
		if(Auton.isDriveActive){

		}
		else{

		
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
		}
	}
	public void ball(){
		SmartDashboard.putNumber("POV", pad.getPov());
		if(pad.getAButton()){ //Turn on Intake, run tunnel
		Intake.getInstance().on();
		Tunnel.getInstance().on();
		}
		else if(pad.getBButton()){
			Tunnel.getInstance().on();
		}
		else{
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		if(pad.getYButton()){
			Intake.getInstance().goUp();
		}
		else if(pad.getXButton()){
			Intake.getInstance().goDown();
		}
		}
		public void manipulator(){
			if(pad.getPov() == 0){
				HatchPanelManipulator.getInstance().setOut();
			}
			else if(pad.getPov() == 180){
				HatchPanelManipulator.getInstance().setIn();
			}
			else if(pad.getLBButton()){
				HatchPanelManipulator.getInstance().setUp();
			}
			else if(pad.getLT()){
				HatchPanelManipulator.getInstance().setDown();
			}
		}
		/*
	public void runIntake(){
		SmartDashboard.putNumber("POV", pad.getPov());
		if(pad.getAButton()){ //Turn on Intake
		System.out.println("Intake running)");
		Intake.getInstance().on();
		Tunnel.getInstance().on();
		HatchPanelManipulator.getInstance().setOut();
		}
		else{
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}

		if(pad.getYButton())	{
			System.out.println("I should be going up");
			Intake.getInstance().goUp();
		}
		else if(pad.getXButton()){
			System.out.println("I should be going down");
			Intake.getInstance().goDown();
		}

		else{
		}
	}
	
	/*
	public void shooter(){
		if(pad.getBButton()){
			System.out.println("shooting");
			CargoTransport.getInstance().positive();
		}
		else{
			CargoTransport.getInstance().stop();
		}
	}
	*/
	//BEGIN LEGACY CODE
	public void legacyIntake() {
		if (pad.getYButton()) {
			Intake.getInstance().on();

		} else {
			Intake.getInstance().off();
		}
	}

	

	public void legacyClimber() { 
		if (pad.getRBButton()) {
			Climber.getInstance().up();
		} else if (pad.getLBButton()) {
			Climber.getInstance().down();
		} else {
			Climber.getInstance().stop();
		}

	}
/*
	public void legacyManipulator() {
		if (pad.getLT() && pad.getRT()) {
			HatchPanelManipulator.getInstance().outandup();
			}
			else if (pad.getLT()) {
				HatchPanelManipulator.getInstance().outanddown();
			}
			else{
				HatchPanelManipulator.getInstance().inanddown();
			}

	}
	*/ 
	

	}
