/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

/**
 * Add your docs here.
 */
public class LongRangeIR extends IrSensor{
    public LongRangeIR(int port){
        super(port);
    }
    public double getDistance(){
        return (60.495 * Math.pow(getVoltage(), -1.1904));
    }
}
