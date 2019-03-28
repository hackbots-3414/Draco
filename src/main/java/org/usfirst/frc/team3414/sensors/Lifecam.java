/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * Add your docs here.
 */
public class Lifecam {
    static UsbCamera camera;
    public static void init(){
        camera = CameraServer.getInstance().startAutomaticCapture();
    }
    public static void startRear(){
            camera.setFPS(15);
            camera.setResolution(360, 240);
            camera.setVideoMode(PixelFormat.kGray, 360, 240, 30);
        }
    public static void stopRear(){ //TODO test this, possibly a BAD IDEA
        camera.free();
    }
        
    
}
