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
import org.usfirst.frc.team3414.auton.Auton;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.DashboardOutput;
import org.usfirst.frc.team3414.diagnostic.LED;
import org.usfirst.frc.team3414.sensors.Limelight;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	// testing
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */

	// Fc. Compressor c = new Compressor(Config.COMPRESSOR);
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		// HCompressor.init();
		// c.enabled();
		// c.setClosedLoopControl(true);
		DriveTrain.getInstance().init();
		Intake.getInstance().init();
		Tunnel.getInstance().init();
		Climber.getInstance().init();
		Limelight.init();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Teleop.getInstance().replaySystem();
		//Teleop.getInstance().align();
		Teleop.getInstance().driveStraight();
		//WORKING OFFICIALLY
		Teleop.getInstance().drive();
		Teleop.getInstance().freeDriveTrain();
		Teleop.getInstance().ball();
		Teleop.getInstance().manipulator();
		Teleop.getInstance().camera();
		//SHOULD WORK
	    Teleop.getInstance().climber();
		//BONUS
		DashboardOutput.diagnostic();
		
	}

	public void teleopInit() {
		if (Config.REPLAY_MODE) {
			try {
				Auton.getInstance().replayInit();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (!Config.REPLAY_MODE) {
			try {
				Auton.getInstance().recordInit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Limelight.init();
		LED.setPurple();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Limelight.init();
	}
}
