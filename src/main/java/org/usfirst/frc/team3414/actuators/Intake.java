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

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class Intake {

    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;

    }

    TalonSRX intakeMotor;
    Solenoid intakePiston;

    public Intake() {
        TalonSRX intakeMotor = new TalonSRX(Config.INTAKE_TALON);
        Solenoid intakePiston = new Solenoid(Config.INTAKE_PISTON);
    }

    public void set(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void on() {
        intakeMotor.set(ControlMode.PercentOutput, 1.0);
    }

    public void off() {
        intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void reverse() {
        intakeMotor.set(ControlMode.PercentOutput, -1.0);
    }

    public void positive() {
        intakeMotor.set(ControlMode.PercentOutput, 1.0);
    }
    public void goDown(){
        intakePiston.set(true);
    }
    public void goUp(){
        intakePiston.set(false);   }

	public void stop() {
	}
}
