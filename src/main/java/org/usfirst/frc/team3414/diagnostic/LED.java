/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.diagnostic;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class LED {
   public static Spark light = new Spark(0);
   //static  Spark light = new Spark(10);

    public static void setPurple() { // Passive color
        light.set(.91);
    }

    public static void setWhite() { // Passive color 2
        light.set(.93);
    }

    public static void setOrange() {
        light.set(.65);
    }

    public static void setGreen() { // 25 to 20 seconds left; See if we can make these match ending colors light
                                    // chase (slowly depleting until the next color)
        light.set(.77);
    }

    public static void setYellow() { // 20 to 15 seconds left
        light.set(.69);
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

    // In match time sets
    public static void checkClimberTime() {
        climberFirstWarning();
        climberSecondWarning();
        climberLastWarning();
                                     
    }

    private static void climberFirstWarning() {
    }
//6 second warning
    private static void climberSecondWarning() {
    }

    static long lastTime = 0;

    public static void climberRise() {
        setFire(); 
    }

    public static void climberLastWarning() {
        lastTime = System.currentTimeMillis();
        if(Timer.getMatchTime()>= 115){

        if(System.currentTimeMillis() - lastTime > 500){
        setWhite();
        lastTime = System.currentTimeMillis();
        }
    }
        else{
        setRed();
        }
    }

    public static void climberMoveBottom() {
        //No color selected
    }

    public static void climberRaiseFront() {
        //No color selected
    }

    public static void climberMoveForward() {
        //No color selected
    }

    public static void climberRaiseRear() {
        //No color selected
    }  

    public static void climberFinished() {
    }

    public static void alignActivated() {
        setStrobeGold();
    }

    public static void hatchAbleToBePickedUp() {
        setGreen();

    }

    public static void hatchOpen() {
        setOrange();
    }

    public static void hatchClosed() {
        setWhite();
    }
    public static void setStatic(){
        setPurple();
    }
    // put more in, you get the idea

}
