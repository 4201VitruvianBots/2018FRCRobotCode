package org.usfirst.frc.team4201.robot.commands;

import java.io.File;

import org.usfirst.frc.team4201.robot.Robot;

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
	double max_vel = 3; // 180
	
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
		SmartDashboard.putString("PathFinder Status" , "Initializing...");
		
		
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
		} catch (Exception e) {
			// Handle it how you want
			DriverStation.reportError("4201 Error: Couldn't read csv", false);
			if(first) {
				//File myfile = new File("/media/sda1/Pathfinder/driveSpline.csv");
				//trajectory = Pathfinder.readFromCSV(myfile);
			}
			else{
				SmartDashboard.putString("PathFinder Status" , "Error: Could Not Read string - " + filename + " - Ending Autonomous to be safe");
				Scheduler.getInstance().removeAll();
			}
		} 
		
		SmartDashboard.putString("PathFinder Status" , "Trajectory Generated!");
		
		// Modify the trajectory from a single line from the center of the bot to two lines for both sides of the drive train.
		// Wheelbase = Distance between left/right side of wheels
		
		// Configure encoder classes to follow the trajectories
    	left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);
    	
		SmartDashboard.putString("PathFinder Status" , "Enabling...");
		
		left.configureEncoder(Robot.driveTrain.getLeftEncoderValue(), 1440, 0.1111);	//0.1823	// 360 enc ticks per rev * 4x quad enc ?  0.1016
		right.configureEncoder(Robot.driveTrain.getRightEncoderValue(), 1440, 0.1111);	//0.1823	// 0.1016 4 inches in meters - undershoot

		// The A value here != max_accel. A here is an acceleration gain (adjusting acceleration to go faster/slower), while max_accel is the max acceleration of the robot.
		// Leave A here alone until robot is reaching its target, then adjust to get it to go faster/slower (typically a small value like ~0.03 is used).
		// Usually, you wont have to adjust this though.
		left.configurePIDVA(1, 0, 0, 1 / max_vel, 0);
		right.configurePIDVA(1, 0, 0, 1 / max_vel, 0);   

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
		//double l = left.calculate(Robot.driveTrain.leftEncoder.get());
		//double r = right.calculate(Robot.driveTrain.rightEncoder.get());
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
		//Shuffleboard.putNumber("Speed", Robot.driveTrain.getTestEncoderSpeed());
		
		// Set the output to the motors
		Robot.driveTrain.setDirectDriveOutput(-(l + turn), -(r - turn));
		
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
    	//RobotMap.waypointX = points[points.length - 1].x;
    	//RobotMap.waypointY = points[points.length - 1].y;
    	//RobotMap.waypointAngle = points[points.length - 1].angle;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
