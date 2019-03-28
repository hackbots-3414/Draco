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

    public static void setPurple() { // Passive color
        light.set(.01); // Manual calls for .91
        // THIS IS NOT WHAT PURPLE SHOULD BE @Mr. Peterson
    }

    public static void setWhite() { // Passive color 2
        light.set(.93);
    }

    public static void setOrange() {
        light.set(.65);
    }

    public static void setGreen() { // 25 to 20 seconds left; See if we can make these match ending colors light
                                    // chase (slowly depleting until the next color)
        light.set(.77); // THIS IS ACTUALLY RED
    }

    public static void setYellow() { // 20 to 15 seconds left
        light.set(.69); // given
        // light.set(.67); // Corrected value, given goes to lawn green. All of them go
        // to lawn green :(
    }

    public static void setRed() { // 15 to 10 seconds left
        light.set(.61);
    }

    public static void setBlue() {
        light.set(.87);
    }

    public static void setHBWhite() { // Waiting to start
        light.set(-.21);
    }

    public static void setHBPurple() { // Waiting to start
        light.set(.27); // Physically set Color two to PURPLE
    }

    public static void setRainbow() { // Successful Climb
        light.set(-.99);
    }

    public static void setParty() {
        light.set(-.97);
    }

    static boolean lineBlocked = false;
    static boolean timeBlocked = false;
    static boolean masterBlock = false;

    public static void setMasterBlock(boolean blockAll){
        masterBlock = blockAll;
    }
    public static void setLineBlock(boolean blockLineLED) {
        lineBlocked = blockLineLED;
    }

    public static void setTimeBlock(boolean blockTimeLED) {
        timeBlocked = blockTimeLED;
    }

    public static void setFire() { // Fire is a safety hazard :(
        light.set(-.57);
    }

    public static void setStrobeRed() {
        light.set(-.11); // "Push your defence bot out of the way mode" -bobby (AngryBot mode)
    }

    public static void setStrobeGold() {
        light.set(-.05);
    }

    public static void setGameColor() {
        Timer.getMatchTime();
    }

    public static void setTurquoise() {
        light.set(.79);
    }

    public static void setLightChase() {
        light.set(.21);
    }

    


    public static void climberLastWarning() {
        // LED.setRed();
        /*
         * lastTime = System.currentTimeMillis(); if(Timer.getMatchTime()>= 115){
         * 
         * if(System.currentTimeMillis() - la stTime > 500){ setWhite(); lastTime =
         * System.currentTimeMillis(); } } else{ setRed(); }
         */
    }

    public static void climberMoveBottom() {
        // No color selected
    }

    public static void climberRaiseFront() {
        // No color selected
    }

    public static void climberMoveForward() {
        // No color selected
    }

    public static void climberRaiseRear() {
        // No color selected
    }

    public static void climberFinished() {
    }

    public static void alignActivated() {
        setStrobeGold();
    }

    public static void hatchAbleToBePickedUp() {
        // setGreen();

    }

    public static void hatchOpen() {
        setOrange();
    }

    public static void hatchClosed() {
        setWhite();
    }

    public static void setStatic() {
        setPurple();
    }
    public static void reset(){
        setMasterBlock(false);
        setLineBlock(false);
        setTimeBlock(false);
        setPurple();
    }
    public static void lineLED() {
        if (!lineBlocked && !masterBlock) {
            if (Teleop.getInstance().getLineSensor().getAverageVoltage() > 1.0
                    && HatchPanelManipulator.getInstance().isOpen()) {
                LED.setYellow();
                setTimeBlock(true);
            } else if (Teleop.getInstance().getLineSensor().getAverageVoltage() > 1.0
                    && !HatchPanelManipulator.getInstance().isOpen()) {
                setGreen();
                setTimeBlock(true);

            } else {
                // This should go purple not green or other colors
                setPurple();
            }
        }else {
            setTimeBlock(false);
        }
    }

    public static void setConfetti() {
        light.set(-.87);
    }

    public static void setTwinklePartyPallete() {
        light.set(-.53);
    }

    public static void climbWarning() {
        if (!timeBlocked && !masterBlock) {
            if (!DriverStation.getInstance().isAutonomous()) {
                if (Timer.getMatchTime() <= 30 && Timer.getMatchTime() > 10) {
                    blinkPurple();
                } else if (Timer.getMatchTime() < 10) {
                    blinkRed();
                }
            }
        }
        // put more in, you get the idea

    }
    static long lastBlink = System.currentTimeMillis();
    static int blinkInterval = 500;
    private static void blinkRed() {
        if(System.currentTimeMillis() - lastBlink > blinkInterval){
            setRed();
            lastBlink = System.currentTimeMillis();
        }
        else{
            setWhite();
        }
    }

    private static void blinkPurple() {
        if(System.currentTimeMillis() - lastBlink > blinkInterval){
            setPurple();
            lastBlink = System.currentTimeMillis();
        }
        else{
            setWhite();
        }
    }

    double[] colors = { -.45, 0.01, };

    public static void setHBRainbow() {
        
    }

    public static void set(double value) {
        light.set(value);
    }
}