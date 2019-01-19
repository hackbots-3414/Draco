package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.Joystick;

public class NewController {
	//New Controller with new mappings
	//TODO update mappings on both controllers
	Joystick pad;
	public void init(){
	pad = new Joystick(Config.CONTROLLER_CHANNEL);
	}
	public void closeOut() {
		pad = null;
	}
	public boolean getAButton() {
		return pad.getRawButton(1);

	}

	public boolean getBButton() {
		return pad.getRawButton(2);

	}

	public boolean getXButton() {
		return false;

	}

	public boolean getYButton() {
		return false;

	}

	public boolean getLBButton() {
		return false;

	}

	public boolean getRBButton() {
		return false;
	}

	public boolean getLT() {
		return false;

	}

	public boolean getRT() {
		return false;

	}

	public double getPov() {
		return 0;
	}
	private static NewController instance;

	public static NewController getInstance()
			{
				if(instance == null)
				{
					instance = new NewController();
				}
				
				return instance;
				
		}
	}

