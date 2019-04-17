package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Controller {
	//Normal Controllers with Normal Mappings. There is a difference.
	
    F310 pad;
    XboxController xbox;
	public Controller(){
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
		return pad.getBButton() || xbox.getAButton();

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
		return pad.getRT() || xbox.getTriggerAxis(Hand.kRight) > .25;

	}
	public boolean getLSButton(){
        return pad.getLSButton() || xbox.getStickButton(Hand.kLeft);
    	}
	public boolean getRSButton(){
        return pad.getRSButton() || xbox.getStickButton(Hand.kRight);
	}

	public double getPov() {
		return pad.getPov();
    }
    public double getXPov(){
        return xbox.getPOV();
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
