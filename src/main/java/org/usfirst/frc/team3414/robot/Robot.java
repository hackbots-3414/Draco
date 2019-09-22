/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.robot;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.Arm;
import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HBVacuum;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.diagnostic.DashboardOutput;
import org.usfirst.frc.team3414.diagnostic.LED;
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
	 * START HERE 
	 * The robotInit method is the code that runs when the robot boots.
	 * The subsystems' init methods should be done this way for a few reasons.
	 * Firstly, having them like this means that whatever objects within the
	 * subsystem are only initiated if you need them to be initiated, you don't need
	 * to have to run it if you don't need to. Solenoids for example, will crash the
	 * program if they're not plugged in. If the robot was made to use a solenoid on
	 * boot, and it wasn't plugged in, it would crash. To fix this, you'd comment
	 * out the module that uses the solenoid, like the vacuum, here and in
	 * teleopPeriodic. It would never be called and never be an issue.
	 * See Teleop.java as well as all classes mentioned here that have the (Comments) annotation.
	 * See MultiMotor and Controller
	 * 
	 */
	@Override
	public void robotInit() {

		DriveTrain.getInstance().init(); // Loads Talons. (Comments)
		CameraSwitcher.initStreams(); // Turns on the cameras (Comments)
		HBVacuum.getInstance().init(); // Loads the Talon and Solenoid for the vacuum
		Arm.getInstance().init(); // Loads the Solenoids for the arm (Comments)
		Intake.getInstance().init(); // Loads the Talon for the intake (Comments)
		Tunnel.getInstance().init(); // Loads the Talons and IR sensors for the tunnel. (Comments)
		Climber.getInstance().init(); // Loads the Talons and IR sensors for the climber.
		teleopInit(); // Runs the method that is also run ONCE during teleop. Running it here makes
						// the limelight LED turn off like 10% of the time.
	}

	/**
	 * Automatically triggered by the robot being disabled after auton or teleop.
	 */
	@Override
	public void disabledInit() {
		Teleop.getInstance().getController().setSuperRumble(0); // Sets the controller to no rumble.
		teleopInit(); // Runs the method that is also run ONCE during teleop. Running it here makes
						// the limelight LED turn off like 10% of the time.
	}

	/**
	 * Runs periodically when the robot is disabled. Ideally this also will set the
	 * limelight to be off, again doesn't always work, so we call it elsewhere.
	 * Altough most of those calls are legacy from when we didn't know about this
	 * method :)
	 */
	@Override
	public void disabledPeriodic() {
		Limelight.pitMode(); // Turns off the limelight
		Teleop.getInstance().camera(); // Same camera switcher as used during teleop, but
		if (isDisabled()) { // Extra check to make sure robot is disabled, probably doesnt' need to be here,
							// but I won't remove it without testing
			Teleop.getInstance().stopAll(); // Even though a robot is disabled, motors can still be set to a certain
											// speed. It won't move, but once you enable it, it might. This stops all
											// motors (clmber included)
			if ((Math.abs(Teleop.getInstance().getLeftJoystick().getY()) > .1)
					|| (Math.abs(Teleop.getInstance().getRightJoystick().getY()) > .1)) {
				SmartDashboard.putBoolean("Joysticks Zeroed", false); // When the robot is disabled, this will show if
																		// the joysticks aren't correctly zeroed on the
																		// dashboard. It checks their position and if
																		// it's .1 or more from zero, they aren't zeroed
			} else {
				SmartDashboard.putBoolean("Joysticks Zeroed", true);
			}
		}
	}

	@Override
	public void robotPeriodic() {
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
	public void autonomousInit() { //In the 2019 mode, autonomous is what the Sandstorm mode was. 
		HBVacuum.getInstance().grabGamePiece(); //During sandstorm, turn on the vacuum.
		teleopInit();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
	}

	public void autonomousPeriodic() { //During sandstorm, enable all other controls as if we were in teleop.
		teleopPeriodic();

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Teleop.getInstance().driverInfo(); // Please don't delete this. Updates LEDs for line guiding and time warning,
											// and dashboard updates for timing.
		// Teleop.getInstance().replaySystem(); cancelled autonomous assist tools
		// Teleop.getInstance().align();
		// Teleop.getInstance().driveStraight();
		// WORKING OFFICIALLY
		/**
		 * this is what you'd comment out line by line if a subsystem failed.
		 */
		Teleop.getInstance().drive(); //Drivetrain code
		Teleop.getInstance().freeDriveTrain(); //If an autonomous system took control of the drivetrain, this would free it again. Runs every loop.
		Teleop.getInstance().ball(); //Enables the ball intake controls. 
		// Teleop.getInstance().manipulator(); Controls for the legacy manipulator.
		Teleop.getInstance().arm(); //Enables the arm controls.
		Teleop.getInstance().camera(); //Camera switch buttons (also works during disable)
		Teleop.getInstance().vacuum(); //Enables the vacuum controls.
		// SHOULD WORK
		Teleop.getInstance().climber(); //Enables the climber controls
		// BONUS
		// DashboardOutput.diagnostic(); //Enables the diagnostic outputs, showing the speeds and settings of all devices, and all sensor input.

	}

	public void teleopInit() {
		// if (Config.REPLAY_MODE) {
		// try {
		// Auton.getInstance().replayInit();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// else if (!Config.REPLAY_MODE) {
		// try {
		// Auton.getInstance().recordInit();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// }
		Limelight.init();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Limelight.init();
	}
}
