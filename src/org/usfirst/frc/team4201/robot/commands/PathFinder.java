package org.usfirst.frc.team4201.robot.commands;

import java.io.File;

import org.usfirst.frc.team4201.robot.Robot;

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
	Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left, right;
	boolean end = false;
	Waypoint[] points;
	
    public PathFinder(Waypoint path) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        // this.points = path; Real
        
        this.points = new Waypoint[] {		// Temp
			new Waypoint(0, 0, 0),                 
			new Waypoint(2, -2, Pathfinder.d2r(-45)),          
			new Waypoint(4, -4, 0),
		};
    }
    
    
    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.spartanGyro.reset();
	
		SmartDashboard.putString("PathFinder Status" , "Initializing...");
		// +/- X is forward/backwards, +/- Y is left/right, +/- angle is left/right (unlike gyro, which is +/- right/left).
		// Keep all units in terms of yards for consistency, unless otherwise stated.
		

		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC	// Keep it Cubic
		// Sample Count:        SAMPLES_HIGH (100 000)
		//    		            SAMPLES_LOW  (10 000)
		//    		            SAMPLES_FAST (1 000)				// Use Fast only if calculating from roboRIO
		// Time Step:           0.05 Seconds
		// Max Velocity:        1.7 m/s								// Probably increase this to 3-4x
		// Max Acceleration:    2.0 m/s/s							// Probably increase this to 2-3x
		// Max Jerk:            60.0 m/s/s/s						// Leave this alone, IDK how to calculate an actual value 
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.005, (3.4 * 1.09361), (3 * 1.09361), (60 * 1.09361));

		// Generate the trajectory
		SmartDashboard.putString("PathFinder Status" , "Generating Trajectory...");
		trajectory = Pathfinder.generate(points, config);
		
		SmartDashboard.putString("PathFinder Status" , "Trajectory Generated!");
		
		// Modify the trajectory from a single line from the center of the bot to two lines for both sides of the drive train.
		// Wheelbase = Distance between left/right side of wheels
		modifier = new TankModifier(trajectory).modify(0.9111);
		
		// Configure encoder classes to follow the trajectories
    	left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
    	
		SmartDashboard.putString("PathFinder Status" , "Enabling...");
		
		// Use current encoder values and wheel diameter to start calculating stuff. Wheel diameters is in METERS.
		left.configureEncoder(Robot.driveTrain.driveMotors[0].getSelectedSensorPosition(0), 1440, 0.1016);	// 360 enc ticks per rev * 4x quad enc
		right.configureEncoder(Robot.driveTrain.driveMotors[2].getSelectedSensorPosition(0), 1440, 0.1016);
		
		// Set the PID + V + A values for control. V = 1/ max velocity, which should be the same as what you cinfigured for above. Acceleration is a gain value, tune it like PID values.
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / (1.7 * 1.09361), 0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / (1.7 * 1.09361), 0);    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putString("PathFinder Status" , "Running...");
    	
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
