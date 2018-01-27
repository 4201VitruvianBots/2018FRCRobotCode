package org.usfirst.frc.team4201.robot.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class PathFinder extends Command{
	double max_vel;
	Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left, right;
	boolean end = false;
	Timer stopwatch;
	
    public PathFinder() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }
    
    
    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.spartanGyro.reset();
	
		SmartDashboard.putString("PathFinder Status" , "Initializing...");
		// +/- X is forward/backwards, +/- Y is right/left. Keep all units in terms of yards.
		Waypoint[] points = new Waypoint[] {        
			new Waypoint(0, 0, 0),                  // Waypoint @ x=0, y=0,   exit angle=0 radians
			//new Waypoint(2, -2, Pathfinder.d2r(-45)),                  
			new Waypoint(3, 0, 0),
		};

		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count:        SAMPLES_HIGH (100 000)
		//    		            SAMPLES_LOW  (10 000)
		//    		            SAMPLES_FAST (1 000)
		// Time Step:           0.05 Seconds
		// Max Velocity:        1.7 m/s
		// Max Acceleration:    2.0 m/s/s
		// Max Jerk:            60.0 m/s/s/s
		this.max_vel = 360;
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.005, max_vel, 280, (800 * 1.09361));

		// Generate the trajectory
		SmartDashboard.putString("PathFinder Status" , "Generating Trajectory...");
		trajectory = Pathfinder.generate(points, config);
		
		try {
			 /*
		    Path extractionPath = Files.createTempFile("trajectory", ".csv");
		    Files.copy(Robot.class.getResourceAsStream("/com/team4201/myfile.csv"), extractionPath);
		    trajectory = Pathfinder.readFromCSV(extractionPath.toFile());
		  	*/
			 //File myfile = new File("/home/lvuser/myfile.csv");
			 //trajectory = Pathfinder.readFromCSV(myfile);
			 
		  } catch (Exception e) {
		    // Handle it how you want
			  DriverStation.reportError("Error: Couldn't read csv", false);
		  } 
		
		
		SmartDashboard.putString("PathFinder Status" , "Trajectory Generated!");
		
		// The distance between the left and right sides of the wheelbase is 0.6m
		// Create the Modifier Object
		modifier = new TankModifier(trajectory).modify(0.9111);
		
    	left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
    	
		SmartDashboard.putString("PathFinder Status" , "Enabling...");
		left.configureEncoder(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0), 1440, 0.1016);	// 360 enc ticks per rev * 4x quad enc ?
		right.configureEncoder(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0), 1440, 0.1016);	// 0.1016 4 inches in meters - undershoot
																											// 0.1111 4 inches in years  - 5 in overshoot
																											// 0.125 undershoot - overshoot
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / max_vel, 0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / max_vel, 0);    
		stopwatch = new Timer();
		stopwatch.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putString("PathFinder Status" , "Running...");
		double l = left.calculate(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0));
		double r = right.calculate(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0));
		SmartDashboard.putNumber("PathFinder L" , l);
		SmartDashboard.putNumber("PathFinder R" , r);
		SmartDashboard.putNumber("PathFinder H" , Pathfinder.r2d(left.getHeading()));
		
		double turn = 0.8 * (-1.0/80.0) * Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) + Robot.driveTrain.spartanGyro.getAngle());
		//double turn = 0;
		SmartDashboard.putNumber("PathFinder T" , turn);
		SmartDashboard.putNumber("PathFinder L output" , l + turn);
		SmartDashboard.putNumber("PathFinder R output" , r - turn);
		
		Robot.driveTrain.setDirectDriveOutput(l + turn, r - turn);
		
		if(left.isFinished() && right.isFinished())
			end = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return end; 
    }

    // Called once after isFinished returns true
    protected void end() {
		SmartDashboard.putString("PathFinder Status" , "Command Exited");
    	Robot.driveTrain.setDriveOutput(0, 0);
    	stopwatch.stop();
    	SmartDashboard.putNumber("Path Time", stopwatch.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
