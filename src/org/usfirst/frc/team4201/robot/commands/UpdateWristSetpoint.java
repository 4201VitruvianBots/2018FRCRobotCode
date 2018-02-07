package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateWristSetpoint extends Command {
	
	
    public UpdateWristSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.
    }
    
    @Override
	protected void execute() {
		Robot.wrist.updateWristAngle();
	}
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
