/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.diagnostic;

import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class DriveChecklist {
    public static void joysticks(){
        if(Teleop.getInstance().getLeftJoystick().getRawButton(1)){
            SmartDashboard.putBoolean("Left Joystick:", true);
        }
        else{
            SmartDashboard.putBoolean("Left Joystick:", false);
        }
        if(Teleop.getInstance().getRightJoystick().getRawButton(1)){
            SmartDashboard.putBoolean("Right Joystick:", true);
        }
        else{
            SmartDashboard.putBoolean("Right Joystick:", false);
        }
    
        if((Math.abs(Teleop.getInstance().getLeftJoystick().getY())>.1)|| (Math.abs(Teleop.getInstance().getRightJoystick().getY())>.1) ){
            SmartDashboard.putBoolean("Joysticks Zeroed", false);
                    }
        else{
            SmartDashboard.putBoolean("Joysticks Zeroed", true);
        }
    }
    
}
