/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HCompressor {
    public static Compressor c = new Compressor(Config.COMPRESSOR);
    public static void init(){
        c.enabled();
    }
    public static void output(){
        SmartDashboard.putNumber("Compressor", c.getCompressorCurrent());

    }
}
