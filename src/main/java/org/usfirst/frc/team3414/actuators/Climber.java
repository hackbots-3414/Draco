/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.config.Config;

/**
 * Add your docs here.
 */
public class Climber {
    private static Climber instance;

    public static Climber getInstance()
    {
        if(instance == null)
        {
            instance = new Climber();
        }
        
        return instance;
        
}
    TalonSRX climberOne = new TalonSRX(Config.CLIMBER_MOTOR_ONE);
    TalonSRX climberTwo = new TalonSRX(Config.CLIMBER_MOTOR_TWO);
    TalonSRX climberThree = new TalonSRX(Config.CLIMBER_MOTOR_THREE);

    public void setSpeed(double speed) {
        climberOne.set(ControlMode.PercentOutput, speed);
        climberTwo.set(ControlMode.PercentOutput, speed);
        climberThree.set(ControlMode.PercentOutput, speed);        
    }
    public void up(){
        climberOne.set(ControlMode.PercentOutput, 1.0);
        climberTwo.set(ControlMode.PercentOutput, 1.0);
        climberThree.set(ControlMode.PercentOutput, 1.0);
    }
    public void down(){
        climberOne.set(ControlMode.PercentOutput, -1.0);
        climberTwo.set(ControlMode.PercentOutput, -1.0);
        climberThree.set(ControlMode.PercentOutput, -1.0);    
    }
    public void stop(){
        climberOne.set(ControlMode.PercentOutput, 0.0);
        climberTwo.set(ControlMode.PercentOutput, 0.0);
        climberThree.set(ControlMode.PercentOutput, 0.0);   
    }
}
