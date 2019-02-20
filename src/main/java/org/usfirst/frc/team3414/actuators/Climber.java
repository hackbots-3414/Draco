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
     frontMotor = new TalonSRX(Config.CLIMBER_FRONT);
     rearMotor = new TalonSRX(Config.CLIMBER_REAR);
     middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
     frontMotor.setNeutralMode(NeutralMode.Brake);
     rearMotor.setNeutralMode(NeutralMode.Brake);
     middleMotor.setNeutralMode(NeutralMode.Brake);
     initEncoders();
     resetEncoders();

   }
   @Deprecated
   public void legacyExtendAll(){
       frontMotor.set(ControlMode.PercentOutput, 1);
       rearMotor.set(ControlMode.PercentOutput, 1);
   }
   public void lockDriveTrain(){
    DriveTrain.getInstance().setBlock(true); //Put a block on the drivetain so Teleop Control doesn't mess with this
   }
   int encoderdiff = 500;
    int encodermax = 12500;
    int encodermin = 10000;
    int maxdiff = 1000;
   public void extendAll(){
    double masterthrottle = 1;
    double frontThrottle = .7;
    
    frontMotor.set(ControlMode.PercentOutput, frontThrottle);
    rearMotor.set(ControlMode.PercentOutput, 1);
    if((getDiff() >= 0) && (Math.abs(getDiff()) >= encoderdiff)){
        frontMotor.set(ControlMode.PercentOutput, 0);
    }
    else if((getDiff() <= 0) && (Math.abs(getDiff()) >= encoderdiff)){
        rearMotor.set(ControlMode.PercentOutput, 0);
    }
    else if(getMaxOffset() >= encodermax){
        frontMotor.set(ControlMode.PercentOutput, 0);
        rearMotor.set(ControlMode.PercentOutput, 0);
    }
    else if(getMaxOffset() <= encoderdiff ){
        frontMotor.set(ControlMode.PercentOutput, 0);
        rearMotor.set(ControlMode.PercentOutput, 0);
    }
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
   public int getMaxOffset(){
    int average = ((frontMotor.getSensorCollection().getQuadraturePosition() + rearMotor.getSensorCollection().getQuadraturePosition())/2); 
    return encodermax - average;
  
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
    public double getDiff(){
        return frontMotor.getSensorCollection().getQuadraturePosition() - rearMotor.getSensorCollection().getQuadraturePosition(); 
        //If this is positive, it means the front is ahead by x ticks. Negative rear is ahead by x ticks
    }
    public void diagnostic(){
    SmartDashboard.putNumber("Climber Front %:", frontMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Middle %:", middleMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Rear %:", rearMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Climber Front Encoder", getFrontEncoder());
    SmartDashboard.putNumber("Climber Rear Encoder", getRearEncoder());
    SmartDashboard.putNumber("Climber Middle Encoder", getMiddleEncoder());
    SmartDashboard.putNumber("Climber Encoder Difference", getDiff());
    SmartDashboard.putNumber("Difference between Max and current", getMaxOffset());
    }
}
