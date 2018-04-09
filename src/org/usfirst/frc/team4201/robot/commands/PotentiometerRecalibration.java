package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.followers.EncoderFollower;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import java.io.*;


/**
 *
 */
public class PotentiometerRecalibration extends Command {
    AnalogPotentiometer pot;
	double initialValue, lowerValue, upperValue, midValue, maxDelta, zeroDelta;
	boolean end;
	
    Notifier periodicRunnable;
	Timer stopwatch;
	
    public PotentiometerRecalibration(AnalogPotentiometer analogPot) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.controls);
        
        pot = analogPot;
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialValue = lowerValue = upperValue = pot.get();
    	
        periodicRunnable.startPeriodic(0.05);
        stopwatch.start();
    }

    // Called repeatedly when this Command is scheduled to run
    // Not used. run() from the Runnable is used instead
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stopwatch.get() >= 10;
    }

    // Called once after isFinished returns true
    protected void end() {
        periodicRunnable.stop();
        zeroDelta = 0 - midValue;
    	Shuffleboard.putNumber("Controls", "Suggested Sensor Offset Change", zeroDelta);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }

    class PeriodicRunnable implements Runnable {
        @Override
        public void run() {
        	double value = pot.get();
        	
			if(value < lowerValue)
				lowerValue = value;
        	if(value > upperValue)
        		upperValue = value;
        	
        	maxDelta = upperValue - lowerValue;
        	midValue = (upperValue - lowerValue) / 2;
        	
        	Shuffleboard.putNumber("Controls", "Pot Lowest Value", lowerValue);
        	Shuffleboard.putNumber("Controls", "Pot Highest Value", upperValue);
        	Shuffleboard.putNumber("Controls", "Pot Mid Value", midValue);
        	Shuffleboard.putNumber("Controls", "Pot Delta", maxDelta);
        }
    }
}
