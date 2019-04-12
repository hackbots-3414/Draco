package org.usfirst.frc.team3414.sensors;

import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraSwitcher {
    static VideoSink server;
	public static void init() {
        NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection").setString("limelight");
    }
    public static void initStreams(){
        server = CameraServer.getInstance().getServer();
    }
    public static void setFront(){
        if(server != null){
            server.setSource(Limelight.getAxisCam());
        }
    }
    public static void setRear(){
        if(server != null){
            server.setSource(Lifecam.getCam());
        }
    }
    /*
    public static void setFront(){\
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("target").setString(SmartDashboard.getString("limelight_Stream", "http://10.34.14.11:5800"));
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("newAddress").setBoolean(true);
    }
    public static void setRear(){
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("target").setString("http://fpsrobotics.com");
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("newAddress").setBoolean(true);
    }
	public static void init() {
        setFront();
    }
    */
}
