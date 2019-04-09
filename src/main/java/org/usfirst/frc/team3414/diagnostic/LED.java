/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.diagnostic;

import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class LED {
    public static Spark light = new Spark(0);

    // static Spark light = new Spark(10);
    public static void setSpark(double value) {
        light.set(value);
    }

    static boolean timeBlocked = false;
    static boolean masterBlock = false;

    public static void setMasterBlock(boolean blockAll) {
        masterBlock = blockAll;
    }
    public static void setTimeBlock(boolean blockTimeLED) {
        timeBlocked = blockTimeLED;
    }

    public static void reset() {
        setMasterBlock(false);
        setTimeBlock(false);
        set(LEDColor.PURPLE);
    }

    public static void lineLED() {
        if (!masterBlock) {
            if (Teleop.getInstance().getLineSensor().getAverageVoltage() > 1.0
                    && HatchPanelManipulator.getInstance().isOpen()) {
                LED.set(LEDColor.YELLOW);
                setTimeBlock(true);  
            } else if (Teleop.getInstance().getLineSensor().getAverageVoltage() > 1.0
                    && !HatchPanelManipulator.getInstance().isOpen()) {
                set(LEDColor.GREEN);
               setTimeBlock(true); 

            } 
            
        } else {
            setTimeBlock(false);
        }
    }

    public static void timeWarning() {
        if (!timeBlocked && !masterBlock) {
            if (!DriverStation.getInstance().isAutonomous()) {
                if (Timer.getMatchTime() <= 30 && Timer.getMatchTime() > 10) {
                    blink(LEDColor.WHITE, LEDColor.PURPLE);
                } else if (Timer.getMatchTime() <= 10) {
                    blink(LEDColor.RED, LEDColor.WHITE);

                }
                else{
                    set(LEDColor.PURPLE);
                }
            }
        }
        // put more in, you get the idea

    }

    static long lastBlink = System.currentTimeMillis();
    static int blinkInterval = 500;

    public static void blink(double color, double secondColor) {
        System.out.println(System.currentTimeMillis() - lastBlink);
        if (System.currentTimeMillis() - lastBlink > blinkInterval) {
            System.out.println("PRIMARY");
            LED.set(color);
            lastBlink = System.currentTimeMillis();
        } else {

           set(secondColor);
        }
    }

    public static void set(double value) {
        light.set(value);
    }

}