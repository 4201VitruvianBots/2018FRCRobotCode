package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristRelativeSetpoint extends Command {
	double setpoint;
	
    public AutoSetWristRelativeSetpoint(double setpoint) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UpdateWristSetpoint.autoCommand = true;
    	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint));
    }
    
    @Override
   	protected void execute() {
    	//UpdateWristSetpoint.autoSetpoint = setpoint;
    }       
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.wrist.onTarget();	// What if this returns true? Does this eliminate all other issues?
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
