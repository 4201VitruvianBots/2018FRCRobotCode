package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightWithGyroAndEncoders extends Command {
	
	double speed, goalDistance;
	double kP = 0.03;
	
    public DriveStraightWithGyroAndEncoders(double distance, double robotSpeed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
        this.speed = robotSpeed;
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.spartanGyro.reset();
    	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.driveTrain.spartanGyro.getAngle();
    	Robot.driveTrain.setDriveOutput(speed, -angle*kP);	//check sign to make sure it continues to drive straight
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (goalDistance == Robot.driveTrain.getLeftEncoderDistance()/2 + Robot.driveTrain.getRightEncoderDistance()/2) {
        	return true;
        }
        else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setDriveOutput(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}

