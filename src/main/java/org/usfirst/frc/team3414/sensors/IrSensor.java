/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class IrSensor {
    AnalogInput sensor;

    // short-range values
    private static double SR_MIN_RANGE = 0;

    public IrSensor (int port) {
        sensor = new AnalogInput(port);
    }

    public double getVoltage() {
        double voltage = 0;
        if (System.currentTimeMillis() % 100 == 0) {
             voltage = sensor.getAverageVoltage();
            return voltage;
    }
    else{
        return voltage;
    }
    
}
}



// Distance = 60.374 * POW(Volt , -1.16)