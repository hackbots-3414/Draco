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
import org.usfirst.frc.team3414.diagnostic.LED;
import org.usfirst.frc.team3414.diagnostic.LEDColor;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
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
    Joystick left = Teleop.getInstance().getLeftJoystick();
    Joystick right = Teleop.getInstance().getRightJoystick();

    public void init() {
        frontMotor = new TalonSRX(Config.CLIMBER_FRONT);
        rearMotor = new TalonSRX(Config.CLIMBER_REAR);
        middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
        frontsensor = new DigitalInput(Config.CLIMBER_FRONT_SENSOR); // 3
        rearsensor = new DigitalInput(Config.CLIMBER_REAR_SENSOR); // 4
        middleMotor.setInverted(true);
        initEncoders();
        resetEncoders(); // Because the reset only occurs at the time of enable, starting the climber mid
                         // climb will work fine :) //TODO test this on the hardware.
        updateDashboard(0);

    }

    public void climb(int target, int margin, double finalDriveTime, boolean operatorControlEnabled) {
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
        long start = System.currentTimeMillis();
        long stage2Start = 0;
        long stage4Start = 0;
        long stage6Start = 0;
        int safetyLimit = 5000;
        double frontRetractTime = .5; // Stage 3 won't be triggered until
        double rearRetractTime = .5;
        double topClimbTime = 8;
        double midClimbTime = 4;
        double midToTopClimbTime = 4;
        while (getEscapeButton()) {
            updateDashboard(stage);
            if (stage == 1) {
                LED.set(LEDColor.RED); // Sets the LEDs
                setFront(target);
                setRear(getFrontEncoder() + offset);
                /*
                 * if (Math.abs(getFrontEncoder() - getRearEncoder()) > safetyLimit) { //
                 * experimental break;
                 * 
                 * }
                 */
                if (getRearEncoder() >= margin) {
                    // if (getRearEncoder() >= margin) { //code to switch to stage 2
                    LED.set(LEDColor.RED);
                    stage2Start = System.currentTimeMillis();
                    stage = 2;
                }
            }
            if (stage == 2) {
                setBottom(.3414);
                setFront(target);
                // setRear(getFrontEncoder() + offset);
                setRear(target + offset);
                if ((atFrontDistance() || checkOverride())
                        && (System.currentTimeMillis() - stage2Start > frontRetractTime * 1000)) { // If the front
                                                                                                   // sensor is
                                                                                                   // triggered or, the
                                                                                                   // override is
                    // pressed, go to the next stage
                    setBottom(0);
                    stage = 3;

                }

            }
            if (stage == 3) {
                retractFront();
                setRear(target);
                if (getFrontEncoder() <= 100) {
                    stage4Start = System.currentTimeMillis();
                    stage = 4;

                }
            }
            if (stage == 4) {
                setBottom(.3414); // originally .3414
                setDriveTrain(.10);
                setRear(target);
                if ((atRearDistance() || checkOverride())
                        && (System.currentTimeMillis() - stage4Start > rearRetractTime * 1000)) { // If the rear sensor
                                                                                                  // is triggered or the
                                                                                                  // override is
                    // pressed go to the next stage
                    stage = 5;
                    setDriveTrain(0);
                    setBottom(0);
                }
            }
            if (stage == 5) {
                setBottom(0);
                retractRear();
                if (getRearEncoder() <= 100) {
                    stage = 6;
                    stage6Start = System.currentTimeMillis();
                }
            }
            if (stage == 6) {
                if (!operatorControlEnabled) {
                    setDriveTrain(.3414); // .3414 originally
                    // Timer.delay(finalDriveTime); //originally .7, a .7 wait. Replaced with an if
                    // statement to allow for breakout
                    // stage = 7;
                    double delaytime = finalDriveTime * 1000;
                    if (System.currentTimeMillis() - stage6Start > delaytime) { // Our own timer.delay, only difference
                        
                        stage = 7;
                    }
                } else {
                    stage = 7;
                }
            }
            if (stage >= 7 || stage <= 0) {
                updateDashboard(stage);
                stop();
                LED.setMasterBlock(false); // is that this loops every couple of
                LED.setParty();
                System.out.println("Climb Finished :)");
                SmartDashboard.putNumber("Climb Finished in:", (System.currentTimeMillis() - start) / 1000);
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
        climber.config_kP(0, 8);// (0, 0.175); or 7, 20 on beta
        climber.config_kI(0, 0); // (0,0)
        climber.config_kD(0, 0); // 1.75
        // climber.config_kF(0, 0.427799073);
        climber.config_kF(0, 0);
        climber.config_IntegralZone(0, 0);
        climber.configClosedLoopPeakOutput(0, 1.0);
        climber.selectProfileSlot(0, 0);

    }

    public void front(TalonSRX climber) { // P̶E̶R̶F̶E̶C̶T̶ ̶T̶U̶N̶I̶N̶G̶ ̶O̶N̶ ̶B̶E̶T̶A̶ ̶A̶S̶ ̶O̶F̶ ̶3̶/̶1̶5̶/̶2̶0̶1̶9
                                          // pretty good tuning on beta as of 3/25/19 and perfect tuning on alpha 
                                          // s of 3/23/19
        climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        climber.getSensorCollection().setQuadraturePosition(0, 10);
        climber.configMotionAcceleration(4784);
        climber.configMotionCruiseVelocity(650); // 1196, 720 on beta
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

    public boolean checkOverride() {
        if (left.getRawButton(8) && left.getRawButton(9)) {
            return true;
        } else if (right.getRawButton(8) && right.getRawButton(9)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateDashboard(int stage) {
        if (stage == 1) {
            SmartDashboard.putBoolean("Rising", true);
        } else {
            SmartDashboard.putBoolean("Rising", false);
        }
        if (stage == 2) {
            SmartDashboard.putBoolean("Move Bottom Forward", true);
        } else {
            SmartDashboard.putBoolean("Move Bottom Forward", false);
        }
        if (stage == 3) {
            SmartDashboard.putBoolean("Retract Front", true);
        } else {
            SmartDashboard.putBoolean("Retract Front", false);
        }
        if (stage == 4) {
            SmartDashboard.putBoolean("Move DriveTrain Forward", true);
        } else {
            SmartDashboard.putBoolean("Move DriveTrain Forward", false);
        }
        if (stage == 5) {
            SmartDashboard.putBoolean("Retract Rear", true);
        } else {
            SmartDashboard.putBoolean("Retract Rear", false);
        }
        if (stage == 6) {
            SmartDashboard.putBoolean("Drive Forward", true);
        } else {
            SmartDashboard.putBoolean("Drive Forward", false);
        }
        if (stage == 7) {
        }
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

}
