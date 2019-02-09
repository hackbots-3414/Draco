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

/**
 * Add your docs here.
 */
// Possibility for another Motor for assistance
public class CargoTransport {
    private static CargoTransport instance;

    public static CargoTransport getInstance()
    {
        if(instance == null)
        {
            instance = new CargoTransport();
        }
        
        return instance;
        
}
    TalonSRX CargoTransportOne = new TalonSRX(Config.CARGO_MOTOR);

    public void set(double speed) {

        CargoTransportOne.set(ControlMode.PercentOutput, speed);
    }

    public void reverse() {
        CargoTransportOne.set(ControlMode.PercentOutput, -1.0);
    }

    public void positive() {
        CargoTransportOne.set(ControlMode.PercentOutput, 1.0);
    }
}
