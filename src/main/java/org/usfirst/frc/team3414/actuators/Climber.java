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
import com.ctre.phoenix.motorcontrol.DemandType;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Climber {
    double masterthrottle = 1;
    double frontThrottle = 1;
    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;

    }

    TalonSRX frontMotor;
    TalonSRX rearMotor;
    TalonSRX middleMotor;

    public void init() {
        frontMotor = new TalonSRX(Config.CLIMBER_FRONT);
        rearMotor = new TalonSRX(Config.CLIMBER_REAR);
        middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
        initEncoders();
        resetEncoders();

    }
    public void motionmagicclimber() {
        talonConfig(rearMotor);
        front(frontMotor);
        frontMotor.configMotionAcceleration(4784 / 2);
        rearMotor.set(ControlMode.MotionMagic, 12000, DemandType.ArbitraryFeedForward, 1196);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 20000) {
          // if(System.currentTimeMillis() % 100 ==0){
          // System.out.println("frontMotor\t" +
          // frontMotor.getSensorCollection().getQuadraturePosition());
          // }
          frontMotor.set(ControlMode.Position, rearMotor.getSensorCollection().getQuadraturePosition(),
              DemandType.ArbitraryFeedForward, 0);
    
        }
        // target 8000 ended on
        // frontMotor.set(ControlMode.Position, 12000, DemandType.ArbitraryFeedForward,
        // 0);
        // System.out.println("frontMotor\t" +
        // frontMotor.getSensorCollection().getQuadraturePosition());
        // Timer.delay(20);
        System.out.println("frontMotor\t" + frontMotor.getSensorCollection().getQuadraturePosition());
        rearMotor.set(ControlMode.PercentOutput, 0);
        frontMotor.set(ControlMode.PercentOutput, 0);
    
      }
    
      public void talonConfig(TalonSRX climber) {
        climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        climber.getSensorCollection().setQuadraturePosition(0, 10);
        climber.configMotionAcceleration(4784);
        climber.configMotionCruiseVelocity(1196);
        climber.configPeakOutputForward(1.0);
        climber.configPeakOutputReverse(-1.0);
        climber.config_kP(0, 0.175);
        climber.config_kI(0, 0);
        climber.config_kD(0, 1.75);
        climber.config_kF(0, 0.427799073);
        climber.config_IntegralZone(0, 0);
        climber.configClosedLoopPeakOutput(0, 1.0);
        climber.selectProfileSlot(0, 0);
    
      }
    
      public void front(TalonSRX climber) {
        climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        climber.getSensorCollection().setQuadraturePosition(0, 10);
        climber.configMotionAcceleration(4784);
        climber.configMotionCruiseVelocity(1196);
        climber.configPeakOutputForward(1.0);
        climber.configPeakOutputReverse(-1.0);
        climber.config_kP(0, 1);
        climber.config_kI(0, 0);
        climber.config_kD(0, 0);
        climber.config_kF(0, 0);
        climber.config_IntegralZone(0, 0);
        climber.configClosedLoopPeakOutput(0, 1.0);
        climber.selectProfileSlot(0, 0);
    
      }
      double frontrate = 1;
      double rearrate = 1;
    public void extendAll() {
        
        // Begin Even Climb code
        frontMotor.set(ControlMode.PercentOutput, 1);
        rearMotor.set(ControlMode.PercentOutput, 1);
        
        if ((getDiff() >= 0) && (Math.abs(getDiff()) >= encoderdiff)) {
            //frontMotor.set(ControlMode.PercentOutput, 0);
            frontrate += getDiff()/100;
        } else if ((getDiff() <= 0) && (Math.abs(getDiff()) >= encoderdiff)) {
           // rearMotor.set(ControlMode.PercentOutput, 0);
           rearrate -= getDiff()/100;
        } 
        
       
      //  Begin Maintain Height Code*/
 /*  else if (getMaxOffset() >= encodermax) {
            frontMotor.set(ControlMode.PercentOutput, 0);
            rearMotor.set(ControlMode.PercentOutput, 0);
        } else if (getMaxOffset() <= encoderdiff) {
            frontMotor.set(ControlMode.PercentOutput, 0);
            rearMotor.set(ControlMode.PercentOutput, 0);
        } */
    }

    public void lockDriveTrain() {
        DriveTrain.getInstance().setBlock(true); // Put a block on the drivetain so Teleop Control doesn't mess with
                                                 // this
    }

    int encoderdiff = 1000;
    int encodermax = 12500;
    int encodermin = 15000;
    int maxdiff = 1000;

    public void newExtendAll() {
        // Begin Even Climb code
        frontMotor.set(ControlMode.PercentOutput, .7);
        rearMotor.set(ControlMode.PercentOutput, frontThrottle);

        while (rearMotor.getSensorCollection().getQuadraturePosition() < encodermin
                && !Teleop.getInstance().getRightJoystick().getRawButton(3)) {
            diagnostic();
            if (getDiff() >= encoderdiff) {
                frontThrottle -= .001;
                frontMotor.set(ControlMode.PercentOutput, frontThrottle);
            } else if (getDiff() <= 0) {
                frontThrottle += .001;
                frontMotor.set(ControlMode.PercentOutput, frontThrottle);
            }
        }
        frontMotor.set(ControlMode.PercentOutput, 0);
        rearMotor.set(ControlMode.PercentOutput, 0);
        diagnostic();
        // frontMotor.set(ControlMode.PercentOutput, frontThrottle);
        // rearMotor.set(ControlMode.PercentOutput, 1);
        // if((getDiff() >= 0) && (Math.abs(getDiff()) >= encoderdiff)){
        // frontMotor.set(ControlMode.PercentOutput, 0);
        // }
        // else if((getDiff() <= 0) && (Math.abs(getDiff()) >= encoderdiff)){
        // rearMotor.set(ControlMode.PercentOutput, 0);
        // }
        // //Begin Maintain Height Code
        // else if(getMaxOffset() >= encodermax){
        // frontMotor.set(ControlMode.PercentOutput, 0);
        // rearMotor.set(ControlMode.PercentOutput, 0);
        // }
        // else if(getMaxOffset() <= encoderdiff ){
        // frontMotor.set(ControlMode.PercentOutput, 0);
        // rearMotor.set(ControlMode.PercentOutput, 0);
        // }
    }

    public void moveBottomForward() {
        middleMotor.set(ControlMode.PercentOutput, 1);
    }

    public void moveForward() {
        DriveTrain.getInstance().set(.5, .5);
    }

    public void unlockDriveTrain() {
        DriveTrain.getInstance().setBlock(false); // Release drivetrain block
    }

    public void retractFront() {
        frontMotor.set(ControlMode.PercentOutput, -1);
    }

    public void retractRear() {
        rearMotor.set(ControlMode.PercentOutput, -1);
    }

    public void retractAll() {

        // Begin Even Climb code
        frontMotor.set(ControlMode.PercentOutput, -frontThrottle);
        rearMotor.set(ControlMode.PercentOutput, -1);
        if ((getDiff() >= 0) && (Math.abs(getDiff()) >= encoderdiff)) {
            frontMotor.set(ControlMode.PercentOutput, 0);
        } else if ((getDiff() <= 0) && (Math.abs(getDiff()) >= encoderdiff)) {
            rearMotor.set(ControlMode.PercentOutput, 0);
        }
        if ((frontMotor.getSensorCollection().getQuadraturePosition() <= 200)
                && (rearMotor.getSensorCollection().getQuadraturePosition() <= 200)) {
            unlockDriveTrain();
        }
    }

    public int getMaxOffset() {
        int average = ((frontMotor.getSensorCollection().getQuadraturePosition()
                + rearMotor.getSensorCollection().getQuadraturePosition()) / 2);
        return encodermax - average - encodermin;

    }

    public void stop() {
        frontMotor.set(ControlMode.PercentOutput, 0);
        rearMotor.set(ControlMode.PercentOutput, 0);
        middleMotor.set(ControlMode.PercentOutput, 0);

    }

    public void initEncoders() {
        frontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        middleMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    }

    public void resetEncoders() {
        frontrate =1;
        rearrate =1;
        frontMotor.getSensorCollection().setQuadraturePosition(0, 10);
        rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
        rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
    }

    public double getFrontEncoder() {
        return frontMotor.getSensorCollection().getQuadraturePosition();
    }

    public double getRearEncoder() {
        return rearMotor.getSensorCollection().getQuadraturePosition();
    }

    public double getMiddleEncoder() {
        return middleMotor.getSensorCollection().getQuadraturePosition();
    }

    public double getDiff() {
        return frontMotor.getSensorCollection().getQuadraturePosition()
                - rearMotor.getSensorCollection().getQuadraturePosition();
        // If this is positive, it means the front is ahead by x ticks. Negative rear is
        // ahead by x ticks
    }

    public void diagnostic() {
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
