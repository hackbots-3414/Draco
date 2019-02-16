/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.sensors.IrSensor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Tunnel {

    private static Tunnel instance;

    public static Tunnel getInstance() {
        if (instance == null) {
            instance = new Tunnel();
        }

        return instance;

    }

    TalonSRX tunnelMotor;
    //IrSensor bottomBallSensor;
    //IrSensor topBallSensor;
    public void init() {
        tunnelMotor = new TalonSRX(Config.CARGO_MOTOR_ONE);
        tunnelMotor.setInverted(true);
     //  bottomBallSensor = new IrSensor(Config.BALL_SENSOR_BOTTOM);
      // topBallSensor = new IrSensor(Config.BALL_SENSOR_TOP);
    }

    public void set(double speed) {

        tunnelMotor.set(ControlMode.PercentOutput, speed);
    }

    public void on() {
        tunnelMotor.set(ControlMode.PercentOutput, 1.0);
    }

    public void off() {
        tunnelMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void reverse() {
        tunnelMotor.set(ControlMode.PercentOutput, -1.0);
    }

    public void positive() {
        tunnelMotor.set(ControlMode.PercentOutput, 1.0);
    }

    public void stop() {
    }

    public boolean isBallTop() {
        // if (topBallSensor.getVoltage() >= 3.0) {
        //     return true;
        // }
        // else{
            return false;
        // }
    }
    
    public boolean isBallBottom() {
        // if (bottomBallSensor.getVoltage() >= 3.0) {
        //     return true;
        // }
        // else{
            return false;
        // }
    }

    public void diagnostic() {
        SmartDashboard.putNumber("Tunnel Motor Value:", tunnelMotor.getMotorOutputPercent());
        // SmartDashboard.putNumber("Top Tunnel Sensor Output:", topBallSensor.getVoltage());
        // SmartDashboard.putNumber("Bottom Tunnel Sensor Output:", bottomBallSensor.getVoltage());
    }
    

}
