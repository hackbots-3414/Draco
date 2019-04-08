package org.usfirst.frc.team3414.sensors;

import edu.wpi.first.networktables.NetworkTableInstance;

public class CameraSwitcher {
    public static void setFront(){
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("target").setString("http://fpsrobotics.com");
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("newAddress").setBoolean(true);
    }
    public static void setRear(){
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("target").setString("http://fpsrobotics.com");
        NetworkTableInstance.getDefault().getTable("cameratable").getEntry("newAddress").setBoolean(true);
    }
	public static void init() {
        setFront();
	}

}
