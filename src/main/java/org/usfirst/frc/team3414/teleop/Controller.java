package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class Controller {
	// Normal Controllers with Normal Mappings. There is a difference.

	F310 pad;
	XboxController xbox;

	public Controller() {
		pad = new F310(Config.CONTROLLER_CHANNEL);
		xbox = new XboxController(Config.XBOX_CONTROLLER_CHANNEL);
	}

	public void closeOut() {
		pad = null;
	}

	public boolean getAButton() {
		return pad.getAButton() || xbox.getAButton();

	}

	public boolean getBButton() {
		return pad.getBButton() || xbox.getBButton();

	}

	public boolean getXButton() {
		return pad.getXButton() || xbox.getXButton();

	}

	public boolean getYButton() {
		return pad.getYButton() || xbox.getYButton();
	}

	public boolean getLBButton() {
		return pad.getLBButton() || xbox.getBumper(Hand.kLeft);
	}

	public boolean getRBButton() {
		return pad.getRBButton() || xbox.getBumper(Hand.kRight);
	}

	public boolean getLT() {
		return pad.getLT() || xbox.getTriggerAxis(Hand.kLeft) > .25;

	}

	public boolean getRT() {
		return pad.getRT() || xbox.getTriggerAxis(Hand.kRight) > .75;

	}

	public boolean getLSButton() {
		return pad.getLSButton() || xbox.getStickButton(Hand.kLeft);
	}

	public boolean getRSButton() {
		return pad.getRSButton() || xbox.getStickButton(Hand.kRight);
	}

	public double getPov() {
		return pad.getPov();
	}

	public double getXPov() {
		return xbox.getPOV();
	}

	public void setRumble(RumbleType type, double value) {
		xbox.setRumble(type, value);
	}
	long startTime = System.currentTimeMillis();
	double targetTime = 0;
	public void timedRumble(double time, double power){
		startTime = System.currentTimeMillis();
		targetTime = time*1000;
	}
	public void rumbleDriver(){
		if(System.currentTimeMillis() - startTime < targetTime){
			xbox.setRumble(RumbleType.kLeftRumble, 1);
			xbox.setRumble(RumbleType.kRightRumble, 1);
		}
		else{
			xbox.setRumble(RumbleType.kLeftRumble, 0);
			xbox.setRumble(RumbleType.kLeftRumble, 0);
		}
	}

	private static Controller instance;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;

	}

	public void setSuperRumble(double power) {
		xbox.setRumble(RumbleType.kLeftRumble, power);
			xbox.setRumble(RumbleType.kLeftRumble, power);
	}

	public boolean getYButtonReleased(){
		return xbox.getYButtonReleased();
	}

	public boolean getAButtonReleased() {
		return xbox.getAButtonReleased();
	}
}
