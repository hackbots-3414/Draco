package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.actuators.CargoTransport;
import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.Joystick;

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
	Controller pad = new Controller();
	public void init(){
		pad.init();
	}
	public void drive() {
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
	}
	public void intake(){
		if(pad.getAButton()){ //Turn on intake
		Intake.getInstance().on();
		HatchPanelManipulator.getInstance().setOut();
		}
		else{
			Intake.getInstance().off();
		}
		if(pad.isPovPositive())	{
			Intake.getInstance().goUp();
		}
		else if(pad.isPovNegative()){
			Intake.getInstance().goDown();
		}

		else{
			Intake.getInstance().stop();
		}
	}
	public void shooter(){
		if(pad.getBButton()){
			CargoTransport.getInstance().positive();
		}
		else{
			CargoTransport.getInstance().stop();
		}
	}
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
	public void legacyCargo(){
		if(pad.getAButton()){
			CargoTransport.getInstance().positive();
		}
		else if(pad.getBButton()){
			CargoTransport.getInstance().reverse();
		}
	}
}