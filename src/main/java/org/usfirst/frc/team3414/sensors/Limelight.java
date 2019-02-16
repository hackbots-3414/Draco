/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class Limelight {
    public static void init(){
        setLED(1);
        setMode(1);
    }
    public static void setLED(int state){
        //0-Use pipeline mode 1-Force off 2-Force Blink 3-Force on
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(state);

    }
    public static void setMode(int mode){
        //0-Vision Mode 1- Driver Mode
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);

    }
}