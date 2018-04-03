package org.usfirst.frc.team4201.robot.commands.autonomous;

import java.io.*;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.followers.EncoderFollower;

/**
 *
 */
public class PathFinderReadInverted extends Command {
	double max_vel = 2, kP = 1, kD = 0.2;
	
	Trajectory leftTrajectory, rightTrajectory;
	EncoderFollower left, right;
	boolean end = false;
	Timer stopwatch;
	Waypoint[] points;
	
	String filename;
	boolean first = false;
	Notifier periodicRunnable;
	
	public PathFinderReadInverted(String filename, boolean first, double maxVel, double kP, double kD) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
        this.filename = filename;
        this.first = first;
        this.max_vel = maxVel;
        this.kP = kP;
        this.kD = kD;
    }
	
	public PathFinderReadInverted(String filename, boolean first, double maxVel, double kP) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);

        this.filename = filename;
        this.first = first;
        this.max_vel = maxVel;
        this.kP = kP;
    }
	
	public PathFinderReadInverted(String filename, boolean first, double maxVel) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.filename = filename;
        this.first = first;
        this.max_vel = maxVel;
    }
	
    public PathFinderReadInverted(String filename, boolean first) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.filename = filename;
        this.first = first;
    }

    public PathFinderReadInverted(String filename) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.filename = filename;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	// Used to print status to dashboard for debugging
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Initializing...");
		
		try {
			// Try to read the .csv files
			File leftFile = new File("/media/sda1/Pathfinder/" + filename + "_Left.csv");
			Trajectory lT = Pathfinder.readFromCSV(leftFile);
			leftTrajectory = lT;
			File rightFile = new File("/media/sda1/Pathfinder/" + filename + "_Right.csv");
			Trajectory rT = Pathfinder.readFromCSV(rightFile);
			rightTrajectory =  rT;
			Shuffleboard.putString("Pathfinder", "PathFinder Read" , "Trajectory Read Success!");

			this.setTimeout(lT.segments.length * 0.05 * 1.2);
		} catch (Exception e) {
			// Handle it how you want
			/*
			if(first){
				File leftFile = new File("/media/sda1/Pathfinder/straightCalibration_Left.csv");
				Trajectory lT = Pathfinder.readFromCSV(leftFile);
				leftTrajectory = lT;
				File rightFile = new File("/media/sda1/Pathfinder/straightCalibration_Right.csv");
				Trajectory rT = Pathfinder.readFromCSV(rightFile);
				rightTrajectory =  rT;
				
				Shuffleboard.putString("Pathfinder", "PathFinder Read" , "Trajectory Read Failed!");
			}
			*/
		} 
		
		// This resets the trajectory counts so you can run autos successively without redeploying the robot code.
		// This is in a try/catch statement because the first path loaded will always be null
		try {
			left.reset();
			right.reset();
		} catch (Exception e){
			
		}
		// Configure encoder classes to follow the trajectories
    	left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);
		
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Enabling...");
		
		left.configureEncoder(-Robot.driveTrain.getRightEncoderValue(), 1440, 0.1667);	//0.1823	// 360 enc ticks per rev * 4x quad enc ?  0.1016
		right.configureEncoder(-Robot.driveTrain.getLeftEncoderValue(), 1440, 0.1667);	//0.1823	// 0.1016 4 inches in meters - undershoot

		// The A value here != max_accel. A here is an acceleration gain (adjusting acceleration to go faster/slower), while max_accel is the max acceleration of the robot.
		// Leave A here alone until robot is reaching its target, then adjust to get it to go faster/slower (typically a small value like ~0.03 is used).
		// Usually, you wont have to adjust this though.
		left.configurePIDVA(kP, 0, kD, 1 / max_vel, 0);
		right.configurePIDVA(kP, 0, kD, 1 / max_vel, 0);   
		
		// Initialize the timer & Notifier
		stopwatch = new Timer(); 
		periodicRunnable = new Notifier(new PeriodicRunnable());
		
		// Start the stopwatch and the notifier (notifier will be called every 0.05 second to ensure trajectory is read properly)
		stopwatch.start();
		periodicRunnable.startPeriodic(0.05);
    }
    
    // Called repeatedly when this Command is scheduled to run
	// Not used. run() from the Runnable is used instead
    protected void execute() {
    }
    
    class PeriodicRunnable implements Runnable {
    	int segment = 0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Running...");
	    	
	    	// Calculate the current motor outputs based on the trajectory values + encoder positions
	    	double l = left.calculate(-Robot.driveTrain.getRightEncoderValue());
			double r = right.calculate(-Robot.driveTrain.getLeftEncoderValue());
			
			Shuffleboard.putNumber("Pathfinder", "PathFinder L" , l);
			Shuffleboard.putNumber("Pathfinder", "PathFinder R" , r);
			Shuffleboard.putNumber("Pathfinder", "PathFinder H" , Pathfinder.r2d(left.getHeading()));
			
			// Adjust a turn value based on the gyro's heading + the trajectory's heading. Note that we only use the left's heading, but left/right would be the same since they're following the same path, but separated by wheelbase distance.
			double angleDifference = 0;
			double turn = 0;
			try {
				angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) + Robot.driveTrain.spartanGyro.getAngle());
				turn = 2 * (-1.0/80.0) * angleDifference;
			} catch(Exception e) {
				angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()));
				turn = 2 * (-1.0/80.0) * angleDifference;
			}
			Shuffleboard.putNumber("Pathfinder", "PathFinder T" , turn);
			Shuffleboard.putNumber("Pathfinder", "PathFinder L output" , l + turn);
			Shuffleboard.putNumber("Pathfinder", "PathFinder R output" , r - turn);
			
			Shuffleboard.putNumber("Pathfinder", "Timer", stopwatch.get());
			
			// Set the output to the motors
			Robot.driveTrain.setDirectDriveOutput(-(r - turn), -(l + turn));
			
			// Continue sending output values until the path has been completely followed.
			if(left.isFinished() && right.isFinished() && Math.abs(angleDifference) < 3)
				end = true;
		}
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return end || isTimedOut(); 
    }

    // Called once after isFinished returns true
    protected void end() {
    	periodicRunnable.stop();
    	stopwatch.stop();
    	Robot.driveTrain.setDriveOutput(0, 0);
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Command Exited");
    	Shuffleboard.putNumber("Pathfinder", "Path Time", stopwatch.get());
    	
    	if(first) {
    		try {
	    		FileWriter writer = new FileWriter("/media/sda1/Pathfinder/calibrationFile.txt", true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            
	            bufferedWriter.write("Left Enc. Count: " + Robot.driveTrain.getLeftEncoderValue());
	            bufferedWriter.newLine();
	            bufferedWriter.write("Right Enc. Count: " + Robot.driveTrain.getRightEncoderValue());
	 
	            bufferedWriter.close();
    		} catch(Exception e) {
    			DriverStation.reportError("Error: Could not write to calibration file", false);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
