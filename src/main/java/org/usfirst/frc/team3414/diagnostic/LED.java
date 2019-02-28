/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.diagnostic;

import edu.wpi.first.wpilibj.Spark;

/**
 * Add your docs here.
 */
public class LED {
    public static Spark light = new Spark(0);
    public static void setPurple(){
        light.set(.91);
    }
}
