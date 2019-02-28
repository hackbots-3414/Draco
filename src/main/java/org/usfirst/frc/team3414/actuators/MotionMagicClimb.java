    //Special thanks to Mr. Z, Marcus, Sravani, Kevin and Ross for this. 
//This serves only as a references


/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * <p>
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 *
 * <p>
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use TimedRobot or Command-Based
 * instead if you're new.
 */
public class MotionMagicClimb extends Thread {
  public void test() {
    TalonSRX talon = new TalonSRX(21);
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    talon.getSensorCollection().setQuadraturePosition(0, 10);
    talon.set(ControlMode.PercentOutput, 1);
    long startTime = System.currentTimeMillis();
    while (System.currentTimeMillis() - startTime < 1000) {
      if (System.currentTimeMillis() % 100 == 0) {
        System.out.println("Encoder ticks\t" + talon.getSensorCollection().getQuadraturePosition());
      }
    }
    talon.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Initial effort at PID tuning for Motion Magic on climber.
   */
  public void motionmagicclimber() {
    TalonSRX upDownRear = new TalonSRX(31);
    talonConfig(upDownRear);
    TalonSRX upDownFront = new TalonSRX(21);
    front(upDownFront);
    upDownFront.configMotionAcceleration(4784 / 2);
    upDownRear.set(ControlMode.MotionMagic, 12000, DemandType.ArbitraryFeedForward, 1196);
    long startTime = System.currentTimeMillis();
    while (System.currentTimeMillis() - startTime < 10000) {
      // if(System.currentTimeMillis() % 100 ==0){
      // System.out.println("updownFront\t" +
      // upDownFront.getSensorCollection().getQuadraturePosition());
      // }
      upDownFront.set(ControlMode.Position, upDownRear.getSensorCollection().getQuadraturePosition(),
          DemandType.ArbitraryFeedForward, 0);

    }
    // target 8000 ended on
    // upDownFront.set(ControlMode.Position, 12000, DemandType.ArbitraryFeedForward,
    // 0);
    // System.out.println("updownFront\t" +
    // upDownFront.getSensorCollection().getQuadraturePosition());
    // Timer.delay(20);
    System.out.println("updownFront\t" + upDownFront.getSensorCollection().getQuadraturePosition());
    upDownRear.set(ControlMode.PercentOutput, 0);
    upDownFront.set(ControlMode.PercentOutput, 0);

  }

  
  public void talonConfig(TalonSRX climber) {
    climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    climber.getSensorCollection().setQuadraturePosition(0, 10);
    climber.configMotionAcceleration(4784);
    climber.configMotionCruiseVelocity(1196);
    climber.configPeakOutputForward(1.0);
    climber.configPeakOutputReverse(-1.0);
    climber.config_kP(0, 0.175);
    climber.config_kI(0, 0);
    climber.config_kD(0, 1.75);
    climber.config_kF(0, 0.427799073);
    climber.config_IntegralZone(0, 0);
    climber.configClosedLoopPeakOutput(0, 1.0);
    climber.selectProfileSlot(0, 0);

  }

  public void front(TalonSRX climber) {
    climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    climber.getSensorCollection().setQuadraturePosition(0, 10);
    climber.configMotionAcceleration(4784);
    climber.configMotionCruiseVelocity(1196);
    climber.configPeakOutputForward(1.0);
    climber.configPeakOutputReverse(-1.0);
    climber.config_kP(0, 1);
    climber.config_kI(0, 0);
    climber.config_kD(0, 0);
    climber.config_kF(0, 0);
    climber.config_IntegralZone(0, 0);
    climber.configClosedLoopPeakOutput(0, 1.0);
    climber.selectProfileSlot(0, 0);

  }

}
