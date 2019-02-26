/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.auton;


import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.sensors.Limelight;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Align {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";

  public static void align() {
      enabled = true;
    Limelight.yieldAll();
      double leftJoystickvalue = DriveTrain.getInstance().getLeft();
      double rightJoystickvalue = DriveTrain.getInstance().getRight();
      double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      System.out.println("tx = " +tx);

        double Kp = 0.03;
        double min_command = 0.05;
        double heading_error = -tx;
        double steering_adjust = 0.0;

        if (tx > 1.0)
        {
                steering_adjust = Kp*heading_error - min_command;
        }
        else if (tx < 1.0)
        {
                steering_adjust = Kp*heading_error + min_command;
        }
        leftJoystickvalue += steering_adjust;
        rightJoystickvalue -= steering_adjust;
      
        
     
        DriveTrain.getInstance().set(leftJoystickvalue,rightJoystickvalue);

    
      Timer.delay(0.005);
    }
    static boolean enabled = false;
    public static void diagnostic(){
        SmartDashboard.putBoolean("Align enabled?", enabled);
    }



}
