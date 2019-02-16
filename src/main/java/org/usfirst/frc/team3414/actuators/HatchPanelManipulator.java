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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HatchPanelManipulator {
    Solenoid sliderOut = new Solenoid(Config.HORIZONTAL_MANIPULATOR_OUT);
    Solenoid sliderIn = new Solenoid(Config.HORIZONTAL_MANIPULATOR_IN);
    Solenoid hatchUp = new Solenoid(Config.VERTICAL_MANIPULATOR_UP);
    Solenoid hatchDown = new Solenoid(Config.VERTICAL_MANIPULATOR_DOWN);
    

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
            sliderOut.set(true);
            sliderIn.set(false);
        }
        public void setIn(){
            sliderOut.set(false);
            sliderIn.set(true);
                }
        public void setUp(){
        hatchUp.set(true);
        hatchDown.set(false);
            
                }
        public void setDown(){
            hatchUp.set(false);
            hatchDown.set(true);
        }
        public void diagnostic(){
      // SmartDashboard.putBoolean("Set Out , hatchPanelBackAndForth.get());
       //SmartDashboard.putBoolean("Set Up?:", hatchPanelUpAndDown.get());
        }
        

    }

