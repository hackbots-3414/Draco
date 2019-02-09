package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	//Normal Controllers with Normal Mappings. There is a difference.
	
	Joystick pad;
	public void init(){
		pad = new Joystick(Config.CONTROLLER_CHANNEL);
		}
		public void closeOut() {
			pad = null;
		}
	public boolean getAButton() {
		return pad.getRawButton(0);

	}

	public boolean getBButton() {
		return pad.getRawButton(1);

	}

	public boolean getXButton() {
		return pad.getRawButton(2);

	}

	public boolean getYButton() {
		return pad.getRawButton(3);

	}

	public boolean getLBButton() {
		return pad.getRawButton(4);

	}

	public boolean getRBButton() {
		return pad.getRawButton(5);
	}

	public boolean getLT() {
		return pad.getRawButton(6);

	}

	public boolean getRT() {
		return pad.getRawButton(7);

	}

	public double getPov() {
		return 0;
	}

private static Controller instance;

public static Controller getInstance()
		{
			if(instance == null)
			{
				instance = new Controller();
			}
			
			return instance;
			
	}
}