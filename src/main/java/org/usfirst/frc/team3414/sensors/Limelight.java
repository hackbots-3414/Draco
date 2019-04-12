/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Limelight {
    static AxisCamera camera;
    public static void init(){
        if(Config.PIT_STREAM){
            camera = CameraServer.getInstance().addAxisCamera("10.34.14.11:5800");
        }
        if(Config.PIT_MODE = true){
        pitMode();
        }
        else{
            yieldAll();
        }
    
    }
    public static void streamPit(){
    NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection").setString(camera.getName());
    }
    public static void stream(){
        NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection").setString("limelight");
        }
    public static AxisCamera getAxisCam(){
        return camera;
    }
    public static void resetStream(){
        camera = CameraServer.getInstance().addAxisCamera("10.34.14.11:5800");
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
    public static void setLight(boolean light){
        if(light = false){
        setLED(1);
        }
        else{
            setLED(0);
        }
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