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

import edu.wpi.first.wpilibj.DigitalInput;
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
    DigitalInput bottomBallSensor;
    DigitalInput topBallSensor;
    //All calls to DigitalInput with the Chinese tunnel sensors need to use !, because false really means true. 
    public void init() {
        tunnelMotor = new TalonSRX(Config.CARGO_MOTOR_ONE);
        tunnelMotor.setInverted(true);
     bottomBallSensor = new DigitalInput(Config.BALL_SENSOR_BOTTOM);
     topBallSensor = new DigitalInput(Config.BALL_SENSOR_TOP);
    }
    public int getBallPos(){
        if((!bottomBallSensor.get())&&(!topBallSensor.get())){
            return 3;
        }
               else if(!topBallSensor.get()){
            return 2;
        }
    else if(!bottomBallSensor.get()){
        return 1;
    }
     
    else{
        return 0;
    }

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

    

    public void diagnostic() {
        SmartDashboard.putNumber("Tunnel Motor Value:", tunnelMotor.getMotorOutputPercent());
        SmartDashboard.putBoolean("Top Tunnel Sensor Output:", !topBallSensor.get());
         SmartDashboard.putBoolean("Bottom Tunnel Sensor Output:", !bottomBallSensor.get());
         SmartDashboard.putNumber("Ball Position", getBallPos());
    }
    

}
