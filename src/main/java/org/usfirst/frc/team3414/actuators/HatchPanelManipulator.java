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
    Solenoid hatchPanelBackAndForth = new Solenoid(Config.MANIPULATOR_ONE);
    Solenoid hatchPanelUpAndDown = new Solenoid(Config.MANIPULATOR_TWO);

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
            hatchPanelBackAndForth.set(true);
            hatchPanelUpAndDown.set(true);
        }
        public void outanddown(){
            hatchPanelBackAndForth.set(true);
            hatchPanelUpAndDown.set(false);
        }
        public void inanddown(){
            hatchPanelBackAndForth.set(false);
            hatchPanelUpAndDown.set(false);
        }
        */
        public void setOut(){
            hatchPanelBackAndForth.set(true);
        }
        public void setIn(){
            hatchPanelBackAndForth.set(false);
        }
        public void setUp(){
            hatchPanelUpAndDown.set(true);
        }
        public void setDown(){
            hatchPanelUpAndDown.set(false);
        }

        

    }

