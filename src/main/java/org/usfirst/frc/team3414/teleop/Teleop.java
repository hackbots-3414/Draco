package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.Diagnostic;

import edu.wpi.first.wpilibj.Joystick;

public class Teleop {
	private static Teleop instance;

	public static Teleop getInstance()
			{
				if(instance == null)
				{
					instance = new Teleop();
				}
				
				return instance;
				
		}
	Joystick left = new Joystick(Config.LEFT_STICK);
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller gamepad = new Controller();
	public void drive() {
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
		gamepad.getInstance().getPov();
	}
	
}
