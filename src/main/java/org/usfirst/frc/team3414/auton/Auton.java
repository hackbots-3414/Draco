package org.usfirst.frc.team3414.auton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.usfirst.frc.team3414.actuators.DriveTrain;
import org.usfirst.frc.team3414.config.Config;

public class Auton{
	private static Auton instance;

    public static Auton getInstance()
    {
        if(instance == null)
        {
            instance = new Auton();
        }
        
        return instance;
        
}
boolean driving;
public boolean isDriveActive(){
	return driving;
}
	// BEGIN RECORDING CODE
	long startTime;
	FileWriter writer;

	public void recordInit() throws IOException {
		startTime = System.currentTimeMillis();
		// record the time we started recording

		// put the filesystem location you are supposed to write to as a string
		// as the argument in this method, as of 2015 it is
		// /home/lvuser/recordedAuto.csv

		writer = new FileWriter(Config.getAutoFile());
	}

	public void record(double l, double r) throws IOException {
		if (writer != null) {
			// start each "frame" with the elapsed time since we started recording
			writer.append("" + (System.currentTimeMillis() - startTime));
			
			
			// drive motors
			//writer.append("," + DriveTrain.getInstance().getLeft());
			//writer.append("," + DriveTrain.getInstance().getRight() + "\n");
			writer.append("," + l);
			writer.append("," + r + "\n");
			//Original Code calls for a delimiter as part of the last entry
			
			
			
			
			// barrel motors
			/*
			 * writer.append("," + storage.robot.getBarrelMotorLeft().get());
			 * writer.append("," + storage.robot.getBarrelMotorRight().get());
			 * 
			 * // fork motors writer.append("," + storage.robot.getLeftForkLeft().get());
			 * writer.append("," + storage.robot.getLeftForkRight().get());
			 * writer.append("," + storage.robot.getRightForkLeft().get());
			 * writer.append("," + storage.robot.getRightForkRight().get()); /*
			 */
			/*
			 * THE LAST ENTRY OF THINGS YOU RECORD NEEDS TO HAVE A DELIMITER CONCATENATED TO
			 * THE STRING AT THE END. OTHERWISE GIVES NOSUCHELEMENTEXCEPTION
			 */

			// this records a true/false value from a piston
			// writer.append("," + storage.robot.getToteClamp().isExtended() + "\n");

			/*
			 * CAREFUL. KEEP THE LAST THING YOU RECORD BETWEEN THESE TWO COMMENTS AS A
			 * REMINDER TO APPEND THE DELIMITER
			 */
		}
	}
	

	// this method closes the writer and makes sure that all the data you recorded
	// makes it into the file
	public void endRecording() throws IOException {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	// BEGIN REPLAY CODE
	Scanner scanner;
	long startTimeReplay;

	boolean onTime = true;
	double nextDouble;

	public void replayInit() throws FileNotFoundException {
		// create a scanner to read the file created during BTMacroRecord
		// scanner is able to read out the doubles recorded into recordedAuto.csv (as of
		// 2015)
		 scanner = new Scanner(new File(Config.getAutoFile()));

		// let scanner know that the numbers are separated by a comma or a newline, as
		// it is a .csv file
		scanner.useDelimiter(",|\\n");

		// lets set start time to the current time you begin autonomous
		startTimeReplay = System.currentTimeMillis();
		DriveTrain.getInstance().setBlock(true);
	}
	private int loopcount = 0;
	public void replay() throws FileNotFoundException{
		//if(scanner.hasNextDouble()){
		if ((scanner != null) && (scanner.hasNextDouble())) {
			double t_delta;
			System.out.println("The scanner is not null and has a next double");
			// if we have waited the recorded amount of time assigned to each respective
			// motor value,
			// then move on to the next double value
			// prevents the macro playback from getting ahead of itself and writing
			// different
			// motor values too quickly
			if (onTime) {
				// take next value
				nextDouble = scanner.nextDouble();
			}

			// time recorded for values minus how far into replaying it we are--> if not
			// zero, hold up
			t_delta = nextDouble - (System.currentTimeMillis() - startTimeReplay);
			// if we are on time, then set motor values
			if (t_delta <= 0) {
				// for 2015 robot. these are all the motors available to manipulate during
				// autonomous
				// it is extremely important to set the motors in the SAME ORDER as was recorded
				// in BTMacroRecord
				// otherwise, motor values will be sent to the wrong motors and the robot will
				// be unpredicatable
				// ORIGINAL writer.append("," + tankControl.getLeftJoy());
				// ORIGINAL writer.append("," + tankControl.getRightJoy())
				//System.out.println(scanner.nextDouble());
				System.out.println("sending commands to DriveTrain");
				DriveTrain.getInstance().set(scanner.nextDouble(), scanner.nextDouble());
				loopcount++;
				System.out.println(loopcount);
				/*
				 * storage.robot.getFrontLeftMotor().setX(scanner.nextDouble());
				 * storage.robot.getFrontRightMotor().setX(scanner.nextDouble());
				 * storage.robot.getBackRightMotor().setX(scanner.nextDouble());
				 * storage.robot.getBackLeftMotor().setX(scanner.nextDouble());
				 * 
				 * storage.robot.getBarrelMotorLeft().setX(scanner.nextDouble());
				 * storage.robot.getBarrelMotorRight().setX(scanner.nextDouble());
				 * 
				 * storage.robot.getLeftForkLeft().setX(scanner.nextDouble());
				 * storage.robot.getLeftForkRight().setX(scanner.nextDouble());
				 * storage.robot.getRightForkLeft().setX(scanner.nextDouble());
				 * storage.robot.getRightForkRight().setX(scanner.nextDouble());
				 * 
				 * storage.robot.getToteClamp().set(storage.robot.getToteClamp().isExtended());
				 */
				// go to next double
				onTime = true;
			}
			// else don't change the values of the motors until we are "onTime"
			else {
				onTime = false;
				System.out.println("Error, catch up!");
			}
		}
		// end play, there are no more values to find
		else {
			endReplay();
			
			
		}

	}
	
	// stop motors and end playing the recorded file
	public void endReplay() {
		DriveTrain.getInstance().stop();
		//tankControl.stop();
		//gamepadControl.stop();
		/*
		 * storage.robot.getFrontLeftMotor().setX(0);
		 * storage.robot.getBackLeftMotor().setX(0);
		 * storage.robot.getFrontRightMotor().setX(0);
		 * storage.robot.getBackRightMotor().setX(0);
		 * 
		 * storage.robot.getBarrelMotorLeft().setX(0);
		 * storage.robot.getBarrelMotorRight().setX(0);
		 * 
		 * storage.robot.getLeftForkLeft().setX(0);
		 * storage.robot.getLeftForkRight().setX(0);
		 * storage.robot.getRightForkLeft().setX(0);
		 * storage.robot.getRightForkRight().setX(0);
		 */
		// all this mess of a method does is keep the piston in the same state it ended
		// in
		// if you want it to return to a specific point at the end of auto, change that
		// here
		// storage.robot.getToteClamp().set(storage.robot.getToteClamp().isExtended());

		if (scanner != null) {
			scanner.close();
		}

	}

}



