/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.auton;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.teleop.Teleop;

/**
 * Add your docs here.
 */
public class MoveStraight {
    
    public static void go(){
        double ls = .5;
        double rs = .5;
        while(Teleop.getInstance().getLeftJoystick().getRawButton(7)){
            DriveTrain.getInstance().set(ls, rs);
            if(getDiff() < 0){
                ls -=.1;
            }
            else if(getDiff() > 0){
                rs -= .1;
            }   
        }
       
    }
    public static double getLeftSpeed(){
        return Teleop.getInstance().getLeftJoystick().getY();
        }
    public static double getRightSpeed(){
        return Teleop.getInstance().getRightJoystick().getY();
    }
    private static int getDiff(){
        return DriveTrain.getInstance().getRightEncoder() - DriveTrain.getInstance().getLeftEncoder(); //If positive, right is too big. Is negative left is too big
    }
    public static void release(){
        DriveTrain.getInstance().setBlock(false);
    }
    public static void lockout(){
        DriveTrain.getInstance().;
    }
 }

