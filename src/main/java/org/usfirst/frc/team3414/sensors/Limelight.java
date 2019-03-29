/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Limelight {
    AxisCamera camera;
    public static void init(){
        camera = CameraServer.getInstance().addAxisCamera(SmartDashboard.getString("Limelight IP", limelight.local:5800));
        if(Config.PIT_MODE = true){
        pitMode();
        }
        else{
            yieldAll();
        }
    
    }
    public static void refresh(){
        camera = CameraServer.getInstance().addAxisCamera(SmartDashboard.getString("Limelight IP", limelight.local:5800));
    }
    public static void setLED(int state){
        //0-Use pipeline mode 1-Force off 2-Force Blink 3-Force on
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(state);

    }
    public static void yieldAll(){
        setLED(0);
        setMode(0);
        System.out.println("Yield all");
    }
    public static void pitMode(){
        setLED(1);
        setMode(1);
    }
    public static void driveMode(){
        pitMode();
    }
    public static void compMode(){
        yieldAll();
    }
    public static void defaultView(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(0);

    }
    public static void rearView(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2);
    }
    public static void frontView(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(1);

    }
    public static void setMode(int mode){
        //0-Vision Mode 1- Driver Mode
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(mode);

    }
}