package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArmSetpoint extends Command {
	double inc;
    public SetArmSetpoint(double increment) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.inc = increment;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Keep a running count of how many times this command has been declared
    	Robot.arm.armCommandCount++;
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.armPIDController.getSetpoint() + inc < Robot.arm.armForwardAbsoluteLimit && 
		   Robot.arm.armPIDController.getSetpoint() + inc > Robot.arm.armReverseAbsoluteLimit)
			Robot.arm.setArmSetpoint(inc);
		else
			Robot.oi.enableXboxLeftRumbleTimed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
