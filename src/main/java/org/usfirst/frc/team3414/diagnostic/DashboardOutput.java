/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.diagnostic;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.auton.Align;
import org.usfirst.frc.team3414.sensors.LimeLightUtil;
import org.usfirst.frc.team3414.actuators.Climber;


/**
 * Add your docs here.
 */
public class DashboardOutput {
    public static void outputIntake(){
        Intake.getInstance().diagnostic();

    }
    public static void outputDriveTrain(){
        DriveTrain.getInstance().diagnostic();

    }
    public static void outputTunnel(){
        Tunnel.getInstance().diagnostic();

    }
    public static void outputClimber(){
        Climber.getInstance().diagnostic();                        
    }
    public static void outputHatchPanels(){
        HatchPanelManipulator.getInstance().diagnostic();
    }
    public static void outputAlign(){
        Align.diagnostic();
    }
    public static void outputLimelightUtil(){
        LimeLightUtil.diagnostic();
    }
    public static void diagnostic(){
        outputIntake();
        outputDriveTrain();
        outputTunnel();
        outputClimber();
        outputHatchPanels();
        outputAlign();
        outputLimelightUtil();

    }
    
}