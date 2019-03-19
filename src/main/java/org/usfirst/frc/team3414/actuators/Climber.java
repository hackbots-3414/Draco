/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
    private int escape = Config.ESCAPE_BUTTON;

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
    DigitalInput frontsensor;
    DigitalInput rearsensor;

    public void init() {
        frontMotor = new TalonSRX(Config.CLIMBER_FRONT);
        rearMotor = new TalonSRX(Config.CLIMBER_REAR);
        middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
        frontsensor = new DigitalInput(Config.CLIMBER_FRONT_SENSOR); // 3
        rearsensor = new DigitalInput(Config.CLIMBER_REAR_SENSOR); // 4
        initEncoders();
        resetEncoders();

    }

    public void climb(int target, int margin) {
        /*-
         * Stage 1 - Climb Evenly 
         * Stage 2 - Move Forward 
         * Stage 3 - Raise Front 
         * Stage 4 - Move Whole Bot Forward 
         * Stage 5 - Raise Rear
         * Stage 6 - Drive Forward on platform
         */
        // target = 16000 for top
        int offset = 0;
        // margin = 13500 for top
        int stage = 1;
        rear(rearMotor);
        front(frontMotor);
        setFront(target);
        while (getEscapeButton()) {
            if (stage == 1) {
                System.out.println("Not at target height (IR)");
                setFront(target);
                setRear(getFrontEncoder() + offset);

                if (getRearEncoder() >= margin) {
                    stage = 2;
                }
            }
            if (stage == 2) {
                setBottom(.3414);
                setFront(target);
                // setRear(getFrontEncoder() + offset);
                setRear(target + offset);
                if (atFrontDistance()) {
                    setBottom(0);
                    stage = 3;

                }

            }
            if (stage == 3) {
                retractFront();
                setRear(target);
                if (getFrontEncoder() <= 100) {
                    stage = 4;

                }
            }
            if (stage == 4) {
                setBottom(.3414);
                setDriveTrain(.10);
                setRear(target);
                if (atRearDistance()) {
                    stage = 5;
                    setDriveTrain(0);
                    setBottom(0);
                }
            }
            if (stage == 5) {
                setBottom(0);
                retractRear();
                if (getRearEncoder() >= 200) {
                    stage = 6;
                }
            }
            if (stage == 6) {
                setDriveTrain(.3414);
                stage = 7;
            } if(stage >= 7 || stage <=0) {
                stop();
                System.out.println("Climb Finished :)");
                break;

            }
        }
        stop();

    }

    // TALON CONFIG
    public void rear(TalonSRX climber) {
        climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        climber.getSensorCollection().setQuadraturePosition(0, 10);
        climber.configMotionAcceleration(4784);
        climber.configMotionCruiseVelocity(1196);// 1196
        climber.configPeakOutputForward(1.0);
        climber.configPeakOutputReverse(-1.0);
        climber.config_kP(0, 20);// (0, 0.175); or 7
        climber.config_kI(0, 0); // (0,0)
        climber.config_kD(0, 0); // 1.75
        // climber.config_kF(0, 0.427799073);
        climber.config_kF(0, 0);
        climber.config_IntegralZone(0, 0);
        climber.configClosedLoopPeakOutput(0, 1.0);
        climber.selectProfileSlot(0, 0);

    }

    public void front(TalonSRX climber) { //PERFECT TUNING ON BETA AS OF 3/15/2019
        climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        climber.getSensorCollection().setQuadraturePosition(0, 10);
        climber.configMotionAcceleration(4784);
        climber.configMotionCruiseVelocity(720); // 1196
        climber.configPeakOutputForward(1.0);
        climber.configPeakOutputReverse(-1.0);
        climber.config_kP(0, 3); // Originally 1
        climber.config_kI(0, 0);
        climber.config_kD(0, 0);
        climber.config_kF(0, 0.427799073);
        climber.config_IntegralZone(0, 0);
        climber.configClosedLoopPeakOutput(0, 1.0);
        climber.selectProfileSlot(0, 0);

    }

    // SETTING MOTORS
    public void setDriveTrain(double speed) {
        DriveTrain.getInstance().set(-speed, -speed);
    }

    public void setBottom(double demand) {
        middleMotor.set(ControlMode.PercentOutput, demand);
    }

    public void setRear(int target) {
        rearMotor.set(ControlMode.Position, target, DemandType.ArbitraryFeedForward, 0);
    }

    public void setFront(int target) {
        frontMotor.set(ControlMode.MotionMagic, target, DemandType.ArbitraryFeedForward, 1196);
    }

    public void retractFront() {

        frontMotor.set(ControlMode.Position, 10, DemandType.ArbitraryFeedForward, 0);
        // rearMotor.set(ControlMode.MotionMagic, 12000,
        // DemandType.ArbitraryFeedForward, 0);
    }

    public void retractRear() {
        rearMotor.set(ControlMode.Position, 0);
    }

    public void percentOutputClimber() {
        int target = 15000;
        frontMotor.set(ControlMode.PercentOutput, .5);
        rearMotor.set(ControlMode.PercentOutput, .5);

        while ((getFrontEncoder() < target) && (!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
            if (getRearEncoder() - getFrontEncoder() > 200) {
                // Slow Rear
                rearMotor.set(ControlMode.PercentOutput, rearMotor.getMotorOutputPercent() - .01);
            } else if (getFrontEncoder() - getRearEncoder() > 200) {
                // Speed Up Rear
                rearMotor.set(ControlMode.PercentOutput, rearMotor.getMotorOutputPercent() + .01);
            }
            // frontMotor.set(ControlMode.Position, 13000);
            // rearMotor.set(ControlMode.Position, getRearEncoder() + 500);
        }
        while (!Teleop.getInstance().getRightJoystick().getRawButton(escape)) {
            frontMotor.set(ControlMode.Position, target);
            rearMotor.set(ControlMode.Position, target);
        }
    }
    // SENSORS

    public int getFrontEncoder() {
        return frontMotor.getSensorCollection().getQuadraturePosition();
    }

    public int getRearEncoder() {
        return rearMotor.getSensorCollection().getQuadraturePosition();
    }

    public boolean atFrontDistance() {
        return !frontsensor.get();
    }

    public boolean atRearDistance() {
        return !rearsensor.get();
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

        frontMotor.getSensorCollection().setQuadraturePosition(0, 10);
        rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
        rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
    }

    public void safetyLimit(int encoder, int limit, TalonSRX talon) {
        if (encoder > limit) {
            talon.set(ControlMode.PercentOutput, 0);
        }
    }

    public void diagnostic() {
                SmartDashboard.putNumber("Climber Front %:", frontMotor.getMotorOutputPercent());
                SmartDashboard.putNumber("Climber Middle %:", middleMotor.getMotorOutputPercent());
                SmartDashboard.putNumber("Climber Rear %:", rearMotor.getMotorOutputPercent());
                SmartDashboard.putNumber("Climber Front Encoder", getFrontEncoder());
                SmartDashboard.putNumber("Climber Rear Encoder", getRearEncoder());
              
    }
    // ESCAPE TRIGGER
    public boolean getEscapeButton() {
        return !Teleop.getInstance().getLeftJoystick().getRawButton(escape)
                || !Teleop.getInstance().getRightJoystick().getRawButton(escape);
    }

}
