/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class HatchPanelManipulator {
    Solenoid horizontal = new Solenoid(Config.VERTICAL_MANIPULATOR);
    Solenoid vertical = new Solenoid(Config.HORIZONTAL_MANIPULATOR);

    private static HatchPanelManipulator instance;

    public static HatchPanelManipulator getInstance()
    {
        if(instance == null)
        {
            instance = new HatchPanelManipulator();
        }
        
        return instance;
        
}
/*
        public void outandup(){
            horizontal.set(true);
            vertical.set(true);
        }
        public void outanddown(){
            horizontal.set(true);
            vertical.set(false);
        }
        public void inanddown(){
            horizontal.set(false);
            vertical.set(false);
        }
        */
        public void setOut(){
            horizontal.set(true);
        }
        public void setIn(){
            horizontal.set(false);
        }
        public void setUp(){
            vertical.set(true);
        }
        public void setDown(){
            vertical.set(false);
        }
        

        

    }

