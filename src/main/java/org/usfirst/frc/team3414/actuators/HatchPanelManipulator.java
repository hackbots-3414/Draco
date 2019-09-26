/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.LED;
import org.usfirst.frc.team3414.diagnostic.LEDColor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HatchPanelManipulator {
    public void init(){
        sliderOut = new Solenoid(Config.HORIZONTAL_MANIPULATOR_OUT);
        sliderIn = new Solenoid(Config.HORIZONTAL_MANIPULATOR_IN);
        hatchUp = new Solenoid(Config.VERTICAL_MANIPULATOR_UP);
        hatchDown = new Solenoid(Config.VERTICAL_MANIPULATOR_DOWN);
        button = new DigitalInput(Config.MANIPULATOR_BUTTON_CHANNEL);
    }
    Solenoid sliderOut;
    Solenoid sliderIn;
    Solenoid hatchUp;
    Solenoid hatchDown;
    DigitalInput button;

    private static HatchPanelManipulator instance;

    public static HatchPanelManipulator getInstance() {
        if (instance == null) {
            instance = new HatchPanelManipulator();
        }

        return instance;

    }

    /*
     * public void outandup(){ hatchPanelBackAndForth.set(true);
     * hatchPanelUpAndDown.set(true); } public void outanddown(){
     * hatchPanelBackAndForth.set(true); hatchPanelUpAndDown.set(false); } public
     * void inanddown(){ hatchPanelBackAndForth.set(false);
     * hatchPanelUpAndDown.set(false); }
     */
    public void setOut() {
        sliderOut.set(true);
        sliderIn.set(false);
        //LED.set(LEDColor.GREEN);
      //  LED.set(LEDColor.RED);
      LED.set(LEDColor.GREEN);
    }

    public void setIn() {
        sliderOut.set(false);
        sliderIn.set(true);
       LED.set(LEDColor.PURPLE);
    }
    public void setOpenAssisted(){
        if(isTouchingPanel()){
            setOpen();
        }
    }
    public void setOpenAutomatic(){
        if(isTouchingPanel() && !override){

        }
    }
    private boolean override;
    public boolean isTouchingPanel(){
        
        return !button.get();
    }
    public void updateLED(){
        if(isTouchingPanel()){
            //LED.hatchAbleToBePickedUp();
        }
    }
    public void setClosed() {
        hatchUp.set(true);
        hatchDown.set(false);
    }
    public void setOverride(boolean set){
        override = set;
    }
    public long getLastOpen(){
        return lastOpen;
        }
    
    long lastOpen = 0;
    public void setOpen() {
        lastOpen = System.currentTimeMillis();
        hatchUp.set(false);
        hatchDown.set(true);
    }
    public boolean isOut(){
        return sliderOut.get(); 
    }
    public boolean isOpen(){
        return hatchDown.get();
    }

    public void diagnostic() {
        SmartDashboard.putBoolean("Set out?", sliderOut.get());
        SmartDashboard.putBoolean("Set in?", sliderIn.get());
        SmartDashboard.putBoolean("Set up?", hatchUp.get());
        SmartDashboard.putBoolean("Set down?", hatchDown.get());
        SmartDashboard.putBoolean("Pettengil Button", isTouchingPanel());
    }

}
