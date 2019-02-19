/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Climber {
    private static Climber instance;

    public static Climber getInstance()
    {
        if(instance == null)
        {
            instance = new Climber();
        }
        
        return instance;
        
}
    TalonSRX frontMotor;
    TalonSRX rearMotor;
    TalonSRX middleMotor;

   public void init(){
     frontMotor = new TalonSRX(Config.CLIMBER_MOTOR_ONE);
     rearMotor = new TalonSRX(Config.CLIMBER_MOTOR_TWO);
     middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
     frontMotor.setNeutralMode(NeutralMode.Brake);
     rearMotor.setNeutralMode(NeutralMode.Brake);
     middleMotor.setNeutralMode(NeutralMode.Brake);
     initEncoders();

   }
   public void lockDriveTrain(){
    DriveTrain.getInstance().setBlock(true); //Put a block on the drivetain so Teleop Control doesn't mess with this
   }
   public void extendAll(){
       frontMotor.set(ControlMode.PercentOutput, 1);
       rearMotor.set(ControlMode.PercentOutput, 1);
       
   }
   public void moveBottomForward(){
       middleMotor.set(ControlMode.PercentOutput, 1);
   }
   public void moveForward(){
    DriveTrain.getInstance().set(.5,.5);
   }
   public void unlockDriveTrain(){
       DriveTrain.getInstance().setBlock(false); //Release drivetrain block
   }
   public void retractFront(){
       frontMotor.set(ControlMode.PercentOutput, -1);
   }
   public void retractRear(){
       rearMotor.set(ControlMode.PercentOutput, -1);
   }
   public void stop(){
       frontMotor.set(ControlMode.PercentOutput, 0);
       rearMotor.set(ControlMode.PercentOutput, 0);
       middleMotor.set(ControlMode.PercentOutput, 0);

   }
   public void initEncoders(){
       frontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 9, 10);
       rearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 9, 10);
       middleMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 9, 10);    
   }
   public void resetEncoders(){
       frontMotor.getSensorCollection().setQuadraturePosition(0, 10);
       rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
       rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
      }
    public double getFrontEncoder(){
      return  frontMotor.getSensorCollection().getQuadraturePosition();
    }
    public double getRearEncoder(){
       return  rearMotor.getSensorCollection().getQuadraturePosition();
    }
    public double getMiddleEncoder(){
      return  middleMotor.getSensorCollection().getQuadraturePosition();
    }
    public void diagnostic(){
    SmartDashboard.putNumber("Climber One %:", frontMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Two %:", rearMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Three %:", middleMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Front Encoder", getFrontEncoder());
    SmartDashboard.putNumber("Climber Rear Encoder", getRearEncoder());
    SmartDashboard.putNumber("Climber Middle Encoder", getMiddleEncoder());
    
    }
}
