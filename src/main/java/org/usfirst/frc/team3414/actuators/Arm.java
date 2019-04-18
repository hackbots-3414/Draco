/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class Arm {
    private static Arm instance;
    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }

        return instance;

    }
    Solenoid sliderOut;
    Solenoid sliderIn;
    public void init(){
        sliderOut = new Solenoid(Config.HORIZONTAL_MANIPULATOR_OUT);
        sliderIn = new Solenoid(Config.HORIZONTAL_MANIPULATOR_IN);
    }
    public void setOut() {
        sliderOut.set(true);
        sliderIn.set(false);
    }

    public void setIn() {
        sliderOut.set(false);
        sliderIn.set(true);
    }

}
