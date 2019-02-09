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

	public void intake() {
		if (pad.getAButton()) {
			Intake.getInstance().on();

		} else {
			Intake.getInstance().off();
		}
	}

	

	public void climber() {
		if (pad.getXButton()) {
			Climber.getInstance().up();
		} else if (pad.getYButton()) {
			Climber.getInstance().down();
		} else {
			Climber.getInstance().stop();
		}

	}

	public void manipulator() {
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
	public void cargo(){
		if(pad.getLBButton()){
			CargoTransport.getInstance().positive();
		}
		else if(pad.getRBButton()){
			CargoTransport.getInstance().reverse();
		}
	}
}
