/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.auton;

import org.usfirst.frc.team3414.teleop.Controller;

/**
 * Add your docs here.
 */
public class Assist {
    public static boolean isRunning(){
        return Controller.getInstance().getRT();
    }
}
