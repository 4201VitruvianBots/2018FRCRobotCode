package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetArmDeltaSetpoint extends InstantCommand {
	double inc;
	
    public SetArmDeltaSetpoint(double increment) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.inc = increment;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.checkLimits(Robot.arm.getSetpoint() + inc))
			Robot.arm.setSetpoint(Robot.arm.getSetpoint() + inc);
		else
			Robot.oi.enableXBoxLeftRumbleTimed();
	
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
    	Robot.oi.disableXBoxLeftRumble();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
