package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetArmSetpoint extends InstantCommand {
	double setpoint;
	
    public SetArmSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.checkLimits(setpoint))
    		Robot.arm.setSetpoint(setpoint);
		else
			Robot.oi.enableXBoxLeftRumbleTimed();

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
