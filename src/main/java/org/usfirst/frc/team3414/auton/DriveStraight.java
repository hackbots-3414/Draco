/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.auton;

import org.usfirst.frc.team3414.actuators.DriveTrain;

/**
 * Add your docs here.
 */
public class DriveStraight {
    public static void lockout(){
        DriveTrain.getInstance().setBlock(true);
    }
    public static void go(){
        int margin = 500;
        DriveTrain.getInstance().set(.5, .5);
        if(getDiff() >= margin ){
            DriveTrain.getInstance().set(.5, 0);
        }
        if(getDiff() >= margin){
            DriveTrain.getInstance().set(0, .5);
        }
    }
    private static int getDiff(){
        return DriveTrain.getInstance().getLeftEncoder() - DriveTrain.getInstance().getRightEncoder();
    }
    public static void release(){
        DriveTrain.getInstance().setBlock(false);
    }
}

