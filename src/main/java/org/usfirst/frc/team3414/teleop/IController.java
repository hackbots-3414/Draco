package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Joystick;

public interface IController {
	//This only exists so we can clearly define what a controller is.
	public boolean getAButton();

	public boolean getBButton();

	public boolean getXButton();

	public boolean getYButton();

	public boolean getLBButton();

	public boolean getRBButton();

	public boolean getLT();

	public boolean getRT();

	public double getPov();
	
	public void init();
	public void closeOut();
	public void getInstance();
}
