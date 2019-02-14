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

import edu.wpi.first.wpilibj.Solenoid;

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
    public void init() {
         tunnelMotor = new TalonSRX(Config.CARGO_MOTOR_ONE);
         tunnelMotor.setInverted(true);
        }

    public void set(double speed) {

        tunnelMotor.set(ControlMode.PercentOutput, speed);
    }

    public void on() {
        tunnelMotor.set(ControlMode.PercentOutput, 1.0);
    }
    boolean tunnelBlocker = false;
    public void setBlock(boolean block){
        tunnelBlocker = block;
    }
    public void off() {
        if(tunnelBlocker == false) {
            tunnelMotor.set(ControlMode.PercentOutput, 0.0);
    }
    else{
        
    }
}

    public void reverse() {
        tunnelMotor.set(ControlMode.PercentOutput, -1.0);
    }

    public void positive() {
        tunnelMotor.set(ControlMode.PercentOutput, 1.0);
    }

	public void stop() {
	}
}
