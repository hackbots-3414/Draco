package org.usfirst.frc.team3414.teleop;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.actuators.HatchPanelManipulator;
import org.usfirst.frc.team3414.actuators.Intake;
import org.usfirst.frc.team3414.actuators.Tunnel;
import org.usfirst.frc.team3414.auton.Auton;
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
	public void record() throws IOException{
		if(recordcounter == 0){
			recordcounter++;
			auton.recordInit();
		}
		if(stopcounter == 0){
			auton.record();
		}
		if(pad.getRSButton() && pad.getLSButton()){
			stopcounter++;
			auton.endRecording();
		}
		}
		public void replay() throws IOException{
			if(replaycounter == 0){
				replaycounter++;
				auton.replayInit();
			}
			if(stopcounter == 0){
				auton.replay();
			}
			if(pad.getRSButton() && pad.getLSButton()){
				stopcounter++;
				auton.endReplay();
			}
		}
		/*
		SmartDashboard.putNumber("RB Presses", rbpresses);
		if(pad.getRBButton() && !auton.isDriveActive()){
			System.out.print("RB Pressed");
			rbpresses++;
		}
		if(rbpresses >=50 && recordcounter ==0){
			recordcounter++;
			auton.recordInit();
		}

		if(rbpresses >= 50 && rbpresses <= 500){
			auton.record();
				}
		else if(rbpresses > 500){
			auton.endRecording();
		}
		}
		*/
	
		/*
	int rtpresses = 0;

	public void replay() throws FileNotFoundException{
			SmartDashboard.putNumber("RT Presses", rtpresses);
		if(pad.getRT()){
			rtpresses++;
			
		}
		if(rtpresses >= 50 && replaycounter==0){
			replaycounter++;
			auton.replayInit();
		}
		if(rtpresses >=70){
			auton.replay();
		}
				
	}*/
	public void drive() {
		if(auton.isDriveActive()){
		//	auton.replayDrive();
		}
		else{
		DriveTrain.getInstance().teleop(left.getY(), right.getY());
		}
	}
	public void ball(){
		SmartDashboard.putNumber("POV", pad.getPov());
		if(pad.getAButton()){ //Turn on Intake, run tunnel
		Intake.getInstance().on();
		Tunnel.getInstance().on();
		}
		else if(pad.getBButton()){
			Tunnel.getInstance().on();
		}
		else{
			Intake.getInstance().off();
			Tunnel.getInstance().off();
		}
		if(pad.getYButton()){
			Intake.getInstance().goUp();
		}
		else if(pad.getXButton()){
			Intake.getInstance().goDown();
		}
		}
		public void manipulator(){
			if(pad.getPov() == 0){
				HatchPanelManipulator.getInstance().setOut();
			}
			else if(pad.getPov() == 180){
				HatchPanelManipulator.getInstance().setIn();
			}
			else if(pad.getLBButton()){
				HatchPanelManipulator.getInstance().setUp();
			}
			else if(pad.getLT()){
				HatchPanelManipulator.getInstance().setDown();
			}
		}
}