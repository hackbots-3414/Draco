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
public abstract class IrSensor {
    AnalogInput infrared;

    int iPort = 0;
    IrSensor longRangeIR;
    IrSensor shortRangeIR;

    // short-range values
    private static double SR_MIN_RANGE = 0;

    public IrSensor(int port) {
        iPort = port;
        infrared = new AnalogInput(iPort);
    }

    public double getVoltage() {
        double voltage = infrared.getAverageVoltage();
        return voltage;

    }

    public abstract double getDistance();

    public double getShowDistance() {

        double distance = getDistance();
        if (System.currentTimeMillis() % 100 == 0) {
            SmartDashboard.putNumber(getClass().getSimpleName() + "(" + iPort + ")", distance);
        }
        return distance;
    }
}

// Distance = 60.374 * POW(Volt , -1.16)