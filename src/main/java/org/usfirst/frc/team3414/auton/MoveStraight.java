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
public class MoveStraight {
    
    public static void go(){
       
    }
    public static double getLeftSpeed(){
        return 0;
        }
    public static double getRightSpeed(){
        return 0;
    }
    private static int getDiff(int leftEncoder, int rightEncoder){
        return leftEncoder-rightEncoder;
    }
    public static void release(){
        DriveTrain.getInstance().setBlock(false);
    }
    public static void lockout(){
        DriveTrain.getInstance().setBlock(true);
    }
 }

