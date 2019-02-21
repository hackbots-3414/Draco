package org.usfirst.frc.team3414.teleop;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.Climber;
import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.auton.Align;
import org.usfirst.frc.team3414.auton.Auton;
import org.usfirst.frc.team3414.auton.MoveStraight;
import org.usfirst.frc.team3414.config.Config;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	private static Teleop instance;

	public static Teleop getInstance() {
		if (instance == null) {
			instance = new Teleop();
		}

		return instance;

	}

	Auton auton = new Auton();
	Joystick left = new Joystick(Config.LEFT_STICK);
	Joystick right = new Joystick(Config.RIGHT_STICK);
	Controller pad = new Controller(Config.CONTROLLER_CHANNEL);
	int rbpresses = 0;
	int recordcounter = 0;
	int replaycounter = 0;
	int stopcounter = 0;

	public void record() throws IOException {

		if (pad.getRSButton() && pad.getLSButton()) {
			stopcounter++;
			auton.endRecording();
		}
	}

	public void replay() throws IOException {
		if (pad.getXButton()) {
			auton.replayInit();
		}
		if (pad.getRT()) {
			auton.replay();
		}
		if (pad.getRBButton()) {
			stopcounter++;
			auton.endReplay();
		}
	}
	
	public void drive() {
			DriveTrain.getInstance().teleop(left.getY(), right.getY());
	}
	public void driveStraight(){
		if(left.getRawButton(6)){
			MoveStraight.lockout();
		}
		if(left.getRawButton(7)){
			MoveStraight.go();
		}
		if(left.getRawButton(8)){
			MoveStraight.release();
		}
	}
	public void ball() {
		SmartDashboard.putNumber("POV", pad.getPov());
		boolean isBallMiddle = false;
		if (pad.getAButton() && (Tunnel.getInstance().getBallPos() == 0)) { // Turn on Intake, run tunnel
			Intake.getInstance().on();
			Intake.getInstance().goDown();
			Tunnel.getInstance().on();
		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 1) {
			Intake.getInstance().goUp();
			Tunnel.getInstance().on();
			isBallMiddle = true;
		} else if ((pad.getAButton() && (Tunnel.getInstance().getBallPos() != 2)) && isBallMiddle) {
			Intake.getInstance().goUp();
			Intake.getInstance().off();
			Tunnel.getInstance().on();
		} else if (pad.getAButton() && Tunnel.getInstance().getBallPos() == 2) {
			isBallMiddle = false;
			Intake.getInstance().off();
			Intake.getInstance().goUp();
			Tunnel.getInstance().off();
		} else if (pad.getBButton()) {
			Intake.getInstance().off();
			Tunnel.getInstance().on();
		} else {
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		if (pad.getYButton()) {
			Intake.getInstance().goUp();
		}
	}

	public void manipulator() {
		if (pad.getPov() == 0) {
			HatchPanelManipulator.getInstance().setOut();
		} else if (pad.getPov() == 180) {
			HatchPanelManipulator.getInstance().setIn();
		} else if (pad.getLBButton()) {
			HatchPanelManipulator.getInstance().setUp();
		} else if (pad.getLT()) {
			HatchPanelManipulator.getInstance().setDown();
		}
	}
	public void climber(){
		if(right.getRawButton(6)){
			Climber.getInstance().resetEncoders();
			Climber.getInstance().lockDriveTrain(); //Step 1
			Climber.getInstance().extendAll(); //Eventually integrate the moveforward into this, 
		}
		else if(right.getRawButton(7)){
			Climber.getInstance().extendAll();
			Climber.getInstance().moveBottomForward();
		}
		else if(right.getRawButton(8)){
			Climber.getInstance().retractFront();
		}
		else if(right.getRawButton(9)){
			Climber.getInstance().retractRear();
		}
		else if(right.getRawButton(10)){
			Climber.getInstance().retractAll();
		}
	}
	public void legacyClimber() { //Old Climber Code
		if (right.getRawButton(2)) {
			Climber.getInstance().resetEncoders();
		}
		if (right.getRawButton(6) && right.getRawButton(7)) { // Step 1
			Climber.getInstance().lockDriveTrain();
			Climber.getInstance().extendAll();
			Climber.getInstance().moveBottomForward();
		}
		else if(right.getRawButton(6)){
			Climber.getInstance().extendAll();
		}
			else if (right.getRawButton(7)) { // Step 2
			Climber.getInstance().moveBottomForward();
		} 
		else if (right.getRawButton(6) ) { // Step 1
			Climber.getInstance().lockDriveTrain();
			Climber.getInstance().extendAll(); 
		}
			else if (right.getRawButton(7)) { // Step 2
			Climber.getInstance().moveBottomForward();
		} 
		else if (right.getRawButton(8)) { // Step 3
			Climber.getInstance().retractFront();
		} else if (right.getRawButton(9)) { // step 4
			Climber.getInstance().moveForward();
		} else if (right.getRawButton(10)) { // Step 5
			Climber.getInstance().retractRear();
		} else if (right.getRawButton(11)) { // Step 6
			Climber.getInstance().unlockDriveTrain();
		} else {
			Climber.getInstance().stop();
		}
	}

	public double getLeftJoy() {
		return left.getY();
	}

	public double getRightJoy() {
		return right.getY();
	}

	public void align() {
		if (left.getRawButton(11)) {
			Align.align();
		} else {
			DriveTrain.getInstance().setBlock(false);
		}

	}
	public void replaySystem() {
		if(Config.REPLAY_MODE == "record"){
			try {
			record();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		else if(Config.REPLAY_MODE == "replay"){
			try{
				replay();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
