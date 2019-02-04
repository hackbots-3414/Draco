/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Infrared {
    IrSensor longRangeIR;
	IrSensor shortRangeIR;
    public void init(){
    longRangeIR = new LongRangeIR(0);
    shortRangeIR = new ShortRangeIR(1);
    }

public void outputValues(){
    SmartDashboard.putNumber("Long Distance", longRangeIR.getDistance());
    SmartDashboard.putNumber("Short Distance", shortRangeIR.getDistance());
}
    private static Infrared instance = new Infrared();
    public static Infrared getInstance()
		{
			if(instance == null)
			{
				instance = new Infrared();
			}
			
			return instance;
			
    }

}
