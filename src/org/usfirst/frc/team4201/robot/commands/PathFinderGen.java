package org.usfirst.frc.team4201.robot.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;

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
public class PathFinderGen extends Command{
	double max_vel;
	Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left, right;
	boolean end = false;
	Timer stopwatch;
	Waypoint[] points;
	
	
	
	boolean lock = false;
	
    public PathFinderGen(Waypoint[] path) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.points = path; 
        
        // +/- X is forward/backwards, +/- Y is left/right, +/- angle is left/right (unlike gyro, which is +/- right/left).
     	// Keep all units in terms of yards for consistency, unless otherwise stated.
        /*this.points = new Waypoint[] {		// Temp
			new Waypoint(0, 0, 0),                 
			//new Waypoint(2, -2, Pathfinder.d2r(-45)),          
			new Waypoint(3, 0, 0),
			
		};*/
    }
    
    
    // Called just before this Command runs the first time
    protected void initialize() {
		SmartDashboard.putString("PathFinder Status" , "Initializing...");
		
		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC	// Keep it Cubic
		// Sample Count:        SAMPLES_HIGH (100 000)
		//    		            SAMPLES_LOW  (10 000)
		//    		            SAMPLES_FAST (1 000)				// Use Fast only if calculating from roboRIO
		// Time Step:           0.05 Seconds
		// Max Velocity:        1.7 m/s
		// Max Acceleration:    2.0 m/s/s
		// Max Jerk:            60.0 m/s/s/s
		this.max_vel = 2 * 1.09361;
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.005, max_vel, 200, (800 * 1.09361));

		// Generate the trajectory
		SmartDashboard.putString("PathFinder Status" , "Generating Trajectory..."); 
		trajectory = Pathfinder.generate(points, config);
		
		try {
			File myfile = new File("/media/sda1/Pathfinder/PathFinderGen.csv");
		 	Pathfinder.writeToCSV(myfile, trajectory);		
		 
		} catch (Exception e) {
	    // Handle it how you want
			DriverStation.reportError("Error: Couldn't create csv", false);
		} 
		
		
		SmartDashboard.putString("PathFinder Status" , "Trajectory Generated!");
		
		// Modify the trajectory from a single line from the center of the bot to two lines for both sides of the drive train.
		// Wheelbase = Distance between left/right side of wheels
		modifier = new TankModifier(trajectory).modify(0.9111);
		
		// Configure encoder classes to follow the trajectories
    	left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
    	
		SmartDashboard.putString("PathFinder Status" , "Enabling...");
		
		left.configureEncoder(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0), 1440, 0.1050);	// 360 enc ticks per rev * 4x quad enc ?  0.1016
		right.configureEncoder(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0), 1440, 0.1050);	// 0.1016 4 inches in meters - undershoot
																											// 0.1111 4 inches in years  - 5 in overshoot
																											// 0.125 undershoot - overshoot
		left.configurePIDVA(2.0, 0.02, 0.05, 1 / max_vel, 0);
		right.configurePIDVA(2.0, 0.02, 0.05, 1 / max_vel, 0);    

		stopwatch = new Timer();
		lock = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	SmartDashboard.putString("PathFinder Status" , "Running...");
    	if(!lock) {
    		stopwatch.start();
    		lock = true;
    	}
    	
    	// Calculate the current motor outputs based on the trajectory values + encoder positions
		double l = left.calculate(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0));
		double r = right.calculate(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0));
		SmartDashboard.putNumber("PathFinder L" , l);
		SmartDashboard.putNumber("PathFinder R" , r);
		SmartDashboard.putNumber("PathFinder H" , Pathfinder.r2d(left.getHeading()));
		
		// Adjust a turn value based on the gyro's heading + the trajectory's heading. Note that we only sue the left's ehading, but left/right would be teh same since they're following the same path, but sepearetd by wheelbase distance.
		double turn = 0.8 * (-1.0/80.0) * Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) + Robot.driveTrain.spartanGyro.getAngle());
		//double turn = 0;
		SmartDashboard.putNumber("PathFinder T" , turn);
		SmartDashboard.putNumber("PathFinder L output" , l + turn);
		SmartDashboard.putNumber("PathFinder R output" , r - turn);
		
		SmartDashboard.putNumber("Timer", stopwatch.get());
		SmartDashboard.putNumber("Speed", Robot.driveTrain.getTestEncoderSpeed());
		
		
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
		SmartDashboard.putString("PathFinder Status" , "Command Exited");
    	Robot.driveTrain.setDriveOutput(0, 0);
    	stopwatch.stop();
    	SmartDashboard.putNumber("Path Time", stopwatch.get());
    	RobotMap.waypointX = points[points.length - 1].x;
    	RobotMap.waypointY = points[points.length - 1].y;
    	RobotMap.waypointAngle = points[points.length - 1].angle;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
