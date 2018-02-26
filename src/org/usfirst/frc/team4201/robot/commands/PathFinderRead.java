package org.usfirst.frc.team4201.robot.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class PathFinderRead extends Command{
	double max_vel = 180; // 180
	
	Trajectory leftTrajectory, rightTrajectory;
	TankModifier modifier;
	EncoderFollower left, right;
	boolean end = false;
	Timer stopwatch;
	Waypoint[] points;
	
	String filename;
	boolean first = false;
	
	boolean lock = false;
	
    public PathFinderRead(String filename, boolean first) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.filename = filename;
        this.first = first;
    }

    public PathFinderRead(String filename) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.filename = filename;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Initializing...");
		
		
		try {
			//Path extractionPath = Files.createTempFile("trajectory", ".csv");
			//Files.copy(Robot.class.getResourceAsStream("/com/team4201/myfile.csv"), extractionPath);
			//trajectory = Pathfinder.readFromCSV(extractionPath.toFile());
			
			File leftFile = new File("/media/sda1/Pathfinder/" + filename + "_Left.csv");
			Trajectory lT = Pathfinder.readFromCSV(leftFile);
			leftTrajectory = lT;
			File rightFile = new File("/media/sda1/Pathfinder/" + filename + "_Right.csv");
			Trajectory rT = Pathfinder.readFromCSV(rightFile);
			rightTrajectory =  rT;
			Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Trajectory Read!");
		} catch (Exception e) {
			// Handle it how you want
			Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Trajectory Read Failed!");
		} 
		
		// This resets the trajectory counts so you can run autos successively without redeploying the entire code.
		left.reset();
		right.reset();
		
		// Configure encoder classes to follow the trajectories
    	left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);
    	
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Enabling...");
		
		left.configureEncoder(Robot.driveTrain.getLeftEncoderValue(), 1440, 0.1667);	//0.1823	// 360 enc ticks per rev * 4x quad enc ?  0.1016
		right.configureEncoder(Robot.driveTrain.getRightEncoderValue(), 1440, 0.1667);	//0.1823	// 0.1016 4 inches in meters - undershoot

		// The A value here != max_accel. A here is an acceleration gain (adjusting acceleration to go faster/slower), while max_accel is the max acceleration of the robot.
		// Leave A here alone until robot is reaching its target, then adjust to get it to go faster/slower (typically a small value like ~0.03 is used).
		// Usually, you wont have to adjust this though.
		left.configurePIDVA(1, 0, 0.04, 1 / max_vel, 0);
		right.configurePIDVA(1, 0, 0.04, 1 / max_vel, 0);   

		stopwatch = new Timer(); 
		lock = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Running...");
    	if(!lock) {
    		stopwatch.start();
    		lock = true;
    	}
    	
    	// Calculate the current motor outputs based on the trajectory values + encoder positions
    	double l = left.calculate(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0));
		double r = right.calculate(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0));
		Shuffleboard.putNumber("Pathfinder", "PathFinder L" , l);
		Shuffleboard.putNumber("Pathfinder", "PathFinder R" , r);
		Shuffleboard.putNumber("Pathfinder", "PathFinder H" , Pathfinder.r2d(left.getHeading()));
		
		// Adjust a turn value based on the gyro's heading + the trajectory's heading. Note that we only use the left's heading, but left/right would be the same since they're following the same path, but separated by wheelbase distance.
		double turn = 0.8 * (-1.0/80.0) * Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) + Robot.driveTrain.spartanGyro.getAngle());
		//double turn = 0;
		Shuffleboard.putNumber("Pathfinder", "PathFinder T" , turn);
		Shuffleboard.putNumber("Pathfinder", "PathFinder L output" , l + turn);
		Shuffleboard.putNumber("Pathfinder", "PathFinder R output" , r - turn);
		
		Shuffleboard.putNumber("Pathfinder", "Timer", stopwatch.get());
		
		// Set the output to the motors
		Robot.driveTrain.setDirectDriveOutput(l + turn, r - turn);
		
		// Continue sending output values until the path has been completely followed.
		if(left.isFinished() && right.isFinished())
			end = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return end; 
    }

    // Called once after isFinished returns true
    protected void end() {
		Shuffleboard.putString("Pathfinder", "PathFinder Status" , "Command Exited");
    	Robot.driveTrain.setDriveOutput(0, 0);
    	stopwatch.stop();
    	Shuffleboard.putNumber("Pathfinder", "Path Time", stopwatch.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
