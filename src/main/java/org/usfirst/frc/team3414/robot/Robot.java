/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.robot;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.DashboardOutput;
import org.usfirst.frc.team3414.diagnostic.DriveChecklist;
import org.usfirst.frc.team3414.diagnostic.LED;
import org.usfirst.frc.team3414.diagnostic.LEDColor;
import org.usfirst.frc.team3414.sensors.CameraSwitcher;
import org.usfirst.frc.team3414.sensors.Lifecam;
import org.usfirst.frc.team3414.sensors.Limelight;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */

	// Fc. Compressor c = new Compressor(Config.COMPRESSOR);
	@Override
	public void robotInit() {
		//Actuators 
		DriveTrain.getInstance().init();
		Intake.getInstance().init();
		Tunnel.getInstance().init();
		Climber.getInstance().init();
		CameraSwitcher.init();
		CameraSwitcher.initStreams();
		teleopInit();

		//LEDS and Cameras
		Limelight.init();
	//	Lifecam.init();
		LED.reset();
	}
	@Override
	public void disabledInit() {
		teleopInit();
	}
	@Override
	public void disabledPeriodic() {
		Limelight.pitMode();
		Teleop.getInstance().camera();
		
			Teleop.getInstance().stopAll();
			DriveChecklist.joysticks();
	}
	@Override
	public void robotPeriodic() {
		}
	

	
	@Override
	public void autonomousInit() {
		teleopInit();
		
	}
	public void autonomousPeriodic() {
		teleopPeriodic();
		
	}

	
	@Override
	public void teleopPeriodic() {
		System.out.println(CameraSwitcher.getName());
		Teleop.getInstance().driverInfo(); //Please don't delete this. Updates LEDs for line guiding and time warning, and dashboard updates for timing. 
		//Teleop.getInstance().replaySystem();
	//	Teleop.getInstance().align(); 
		//Teleop.getInstance().driveStraight();
		//WORKING OFFICIALLY
		Teleop.getInstance().drive();
		Teleop.getInstance().freeDriveTrain();
		Teleop.getInstance().ball();
		Teleop.getInstance().manipulator();
		Teleop.getInstance().camera();
		//SHOULD WORK
	    Teleop.getInstance().climber();
		//BONUS
		//DashboardOutput.diagnostic();
		
	}

	public void teleopInit() {
		LED.reset();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Limelight.init();
	}
}
