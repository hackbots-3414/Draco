package org.usfirst.frc.team3414.sensors;

import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraSwitcher {
    static VideoSink server;
	public static void init() {
        NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection").setString("limelight");
    }
    public static void initStreams(){
        Limelight.init();
        Lifecam.init();
        //server = CameraServer.getInstance().getServer(); C++ guide
     //   server = CameraServer.getInstance().addServer("Switched Cam"); //Followed the guide here. https://www.chiefdelphi.com/t/switching-cameras-with-code/344360/4
        //Thanks Peter_Johnson
     //   server.setSource(Limelight.getAxisCam());
        //Limelight.getAxisCam().setConnectionStrategy(ConnectionStrategy.kKeepOpen);
       // Lifecam.getCam().setConnectionStrategy(ConnectionStrategy.kKeepOpen);
     //   server.setSource(Lifecam.getCam());


    }
    public static String getName(){
        return server.getName();
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