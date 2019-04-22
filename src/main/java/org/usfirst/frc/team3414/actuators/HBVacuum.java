// Thank you team 1481 The Riveters for the code   
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.actuators;

import java.awt.Robot;
import java.util.Collections;
import java.util.LinkedList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.config.Config;
import org.usfirst.frc.team3414.teleop.Teleop;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HBVacuum extends Subsystem {
    private static HBVacuum instance;

    public static HBVacuum getInstance() {
        if (instance == null) {
            instance = new HBVacuum(Config.VACUUM_TALON, Config.VENT_SOLENOID, "Hatch Vacuum");
        }

        return instance;

    }

    TalonSRX vacuumTalon;
    Solenoid ventSolenoid;
    private String m_subsystemName;
    private long m_timeStampOfEnable = 0;
    private RunningAverage m_averageConductance = new RunningAverage();
    private Debounce m_debouncedDetectedState = new Debounce();
    private boolean m_newGamePieceDetected = false;

    private double m_vacuumGamePieceDetectedConductance;
    private double m_vacuumMotorPresentConductance;

    public enum state {
        off /* motor is off */, grabbing /* trying to pickup a piece */, holding
        /* maintaining hold on held piece */}

    private state m_vacuumState = state.off;

    public void init() {
        vacuumTalon = new TalonSRX(Config.VACUUM_TALON);
        ventSolenoid = new Solenoid(Config.VENT_SOLENOID);
    }

    private HBVacuum(int TalonCANID, int solenoidID, String name) {

        /*
         * Create the talon and solenoid and assign them to their respective references
         * so we can use them later.
         */
        /*
        vacuumTalon = new TalonSRX(TalonCANID);
        ventSolenoid = new Solenoid(solenoidID);
        */

        m_subsystemName = name;

        /*
         * Set the subsystem name to Vacuum + the configured name. This distinguishes
         * one vacuum from another vacuum.
         * 
         * e.g.: VacuumCargo VacuumHatchCover
         */
        // setName("Vacuum" + name);

        /*
         * Set the threshold for this motor's "I have a game piece" vs
         * "I don't have a game piece" conductance value (which is related to current
         * and the system voltage)
         * 
         * Every pump's motor is different, so look this value up on this robot for this
         * pump.
         * 
         * If we don't have a calibration for this pump stored in a preference, just use
         * the default value from RobotMap, which is vacuumGamePieceDetectedConductance.
         */
        m_vacuumGamePieceDetectedConductance = Preferences.getInstance().getDouble(
                "vacuum" + name + "GamePieceDetectedConductance", Config.VACUUM_GAME_PIECE_DETECTED_CONDUCTANCE);

        /*
         * Set the threshold for this motor's "I am properly wired" vs "I am miswired"
         * conductance value (which is related to current and the system voltage)
         * 
         * Every pump's motor is different, so look this value up on this robot for this
         * pump.
         * 
         * If we don't have a calibration for this pump stored in a preference, just use
         * the default value from RobotMap, which is vacuumMotorPresentConductance.
         */
        m_vacuumMotorPresentConductance = Preferences.getInstance()
                .getDouble("vacuum" + name + "MotorPresentConductance", Config.VACUUM_MOTOR_PRESENT_CONDUCTANCE);
    }

    public void grabGamePiece() {
        /*
         * When we turn on the vacuums, get a timestamp of the first instant that the
         * vacuum motor turns on. We'll use this timestamp to figure out how long the
         * motor is running later.
         */
        if (m_timeStampOfEnable == 0) {
            m_timeStampOfEnable = System.currentTimeMillis();
        }

        /*
         * Close the venting solenoid so we can start pulling a vacuum with the vacuum
         * motor.
         */
        ventSolenoid.set(false);
        /*
         * Turn on the vacuum motor to start pulling a vacuum and grab onto a game
         * piece.
         */
        vacuumTalon.set(ControlMode.PercentOutput, Config.VACUUM_INITIAL_HOLD_SPEED);

        m_vacuumState = state.grabbing;

    }

    public void grab() {
        // Grab Code goes here
        grabGamePiece();
    }
    long lastpulse = System.currentTimeMillis();
    public void holdGamePiece() {

        /*  
         * Close the venting solenoid so we can maintain a vacuum with the vacuum motor.
         */
        ventSolenoid.set(false);
        /*
         * Slow down the vacuum motor to quiet down the system, but still maintain a
         * hold on a game piece already in the suction cups.
         */
        vacuumTalon.set(ControlMode.PercentOutput, Config.VACUUM_SUSTAIN_HOLD_SPEED);
       

        
        m_vacuumState = state.holding;
      }
      public void releaseGamePiece() {
        /*
         * Turn off the motor to eliminate a vacuum. We want to drop the game piece.
         */
        vacuumTalon.set(ControlMode.PercentOutput, 0.0);
    
        /*
         * The Pneumatic Control Module can only sink a total of 500 mA, total, across
         * all solenoids. However, each of the solenoids draws 4.8 W (0.4 A).
         * 
         * If we drive both of them, we're overbudget on current for the PCM. Try to
         * avoid this.
         * 
         * If the vacuum is already off or isn't holding a game piece, don't activate
         * the solenoid as there's no vacuum to release from the system. This conserves
         * current going through the PCM's solenoid driver for solenoids that shouldn't
         * need release control.
         */
       // if (m_vacuumState == state.holding){// m_vacuumState == state.grabbing) { 
        if(true){ //Will always run instead of only while in the holding state
          /*
           * Open the venting solenoid for this vacuum system to allow the game piece to
           * fall off the suction cup by breaking the vacuum in this system. The solenoid
           * will run for a short length of time, vacuumSolenoidOnTimeToVentVacuum, and
           * automatically turn off later.
           */
          //ORIGINAL ventSolenoid.setPulseDuration(Config.VACUUM_SOLENOID_ON_TIME_TO_VENT_VACUUM / 1000.0);
          //ORIGINAL ventSolenoid.startPulse();
          ventSolenoid.set(true);
        }
        /*
         * Reset the timestamp to 0 so we can detect the first time we turn on the
         * vacuum motor (to measure the time the motor is on)
         */
        m_timeStampOfEnable = 0;
    
        m_vacuumState = state.off;
      }
      public boolean isDetectsGamePiece() {
        return m_debouncedDetectedState.getDebounceState();
      }
      public state getState() {
        return m_vacuumState;
      }
      private boolean testForGamePiece() {

        boolean isDetected = false;
    
        if (m_timeStampOfEnable > 0 && (System.currentTimeMillis() - m_timeStampOfEnable) > Config.VACUUM_PUMP_SPIN_UP_TIME) {
          /*
           * If the motor is running for at least vacuumPumpSpinUpTime ms AND the output
           * current is BELOW ~2 amps (vacuumGamePieceDetectedCurrent) but above a minimum
           * "motor is wired correctly" conductance, you probably have a game piece.
           * 
           * If the motor is running for at least vacuumPumpSpinUpTime ms AND the output
           * current is ABOVE ~2 amps (vacuumGamePieceDetectedCurrent), you haven't got a
           * game piece.
           * 
           * These pumps work *less* when they're unable to pump air (because e.g. they've
           * pulled a vacuum.)
           * 
           * It's weird. Trust me. This is how it works.
           * 
           * The vacuumPumpSpinUpTime ms gives the motor on the vacuum pump to spin up so
           * the current measurement from the Talon has had time to settle out to a
           * meaningful number.
           * 
           */
    
          double conductance = m_averageConductance.getAverage();
    
          isDetected = (conductance < m_vacuumGamePieceDetectedConductance) //Biggest 
              && (conductance > m_vacuumMotorPresentConductance); //Smallest
        }
    
        return isDetected;
      }
      @Override
      public void periodic() {                                                                                //BIGGEST                                                                                                             //SMALLEST
          if((m_vacuumState == state.grabbing) && (m_averageConductance.getAverage() < Config.VACUUM_GAME_PIECE_DETECTED_CONDUCTANCE) && m_averageConductance.getAverage() >Config.VACUUM_MOTOR_PRESENT_CONDUCTANCE){
            Teleop.getInstance().getController().setSuperRumble(1);
            if(m_vacuumState == state.holding || m_vacuumState == state.grabbing ){
                SmartDashboard.putBoolean("Has Panel", true);
            }

          }
          else{
            Teleop.getInstance().getController().setSuperRumble(0);
            SmartDashboard.putBoolean("Has Panel", false);
          }
        /*
         * Periodically review if the game piece has been captured by this vacuum
         * system. If it has, set the vacuum system to the minimum sustaining vacuum
         * level. This level is enough to hold vacuum on the game piece but is not as
         * noisy as full vacuum speed.
         * 
         * Also, request that the driver and operator controller rumble for a few
         * milliseconds so they know right away when a game piece has been detected and
         * held.
         */
    
        m_debouncedDetectedState.addNewValue(testForGamePiece());
    
        if (isDetectsGamePiece()) {

          if (!m_newGamePieceDetected) {
            m_newGamePieceDetected = true;
            //Robot.m_oi.rumbleDriver(Config.VACUUM_GAME_PIECE_DETECTED_JOYSTICK_RUMBLE_TIME);
           // Robot.m_oi.rumbleOperator(Config.VACUUM_GAME_PIECE_DETECTED_JOYSTICK_RUMBLE_TIME);
          }
    
         // holdGamePiece(); Original code, we just want rumble instead of actually taking the piece
    
          m_timeStampOfEnable = 0;
        } else {
          m_newGamePieceDetected = false;
        }
    
        double currentBatteryVoltage = vacuumTalon.getBusVoltage();
        double currentTalonCurrent = vacuumTalon.getOutputCurrent();
    
        /*
         * Compute the conductance of the pump, which is less affected by drooping
         * system voltages due to other actuators.
         */
        double conductance;
        try {
          conductance = currentTalonCurrent / currentBatteryVoltage;
    
          m_averageConductance.addNewValue(conductance);
    
        } catch (Exception e) {
          conductance = Double.NaN;
        }
    
        SmartDashboard.putNumber(m_subsystemName + "VacuumMotorCurrent", currentTalonCurrent);
        SmartDashboard.putNumber(m_subsystemName + "VacuumMotorConductance", conductance);
        SmartDashboard.putNumber(m_subsystemName + "VacuumMotorAverageConductance", m_averageConductance.getAverage());
        SmartDashboard.putNumber(m_subsystemName + "PercentOutput Vacuum", vacuumTalon.getMotorOutputPercent());

    
      }
      @Override
      public void initDefaultCommand() {
        /*
         * Subsystems don't NEED a default command, so don't bother specifying one here.
         */
      }
    
    protected class Debounce {
        private LinkedList<Boolean> m_values = new LinkedList<>();

        private boolean lastStableState = false;

        void addNewValue(boolean value) {
            m_values.addFirst(value);

            while (m_values.size() > Math.max(Config.GAME_PIECE_DETECTION_MINIMUM_COUNTS, 2)) {
                m_values.removeLast();
            }
        }

        boolean getDebounceState() {
            /*
             * Count the number values that are equal to true in the list.
             */
            int numberOfTrueValues = Collections.frequency(m_values, true);

            /*
             * Compare the number of true values. If every element is true, set the
             * lastStableState to true; it's stabilized. If nothing is true, then they are
             * all false and set the lastStableState to false to indicate that they're all
             * false; it's stabilized. If there's a mix of true and false values, well, just
             * keep returning the last stable value until every sample can agree whether
             * they're all true or all false.
             */
            if (numberOfTrueValues == m_values.size()) {
                lastStableState = true;
            } else if (numberOfTrueValues == 0) {
                lastStableState = false;
            }

            return lastStableState;
        }
    }

    protected class RunningAverage {

        private LinkedList<Double> m_values = new LinkedList<>();
        private double m_accumulation = 0.0;

        void addNewValue(double value) {
            m_values.addFirst(value);
            m_accumulation += value;

            while (m_values.size() > Math.max(Config.VACUUM_PUMP_SPIN_UP_TIME / 20, 2)) {
                m_accumulation -= m_values.removeLast();
            }
        }

        double getAverage() {
            return m_accumulation / m_values.size();
        }
    }

	public void poweredRelease() { //Not finished, don't use
        releaseGamePiece();
        vacuumTalon.set(ControlMode.PercentOutput, -.02);
	}
}
