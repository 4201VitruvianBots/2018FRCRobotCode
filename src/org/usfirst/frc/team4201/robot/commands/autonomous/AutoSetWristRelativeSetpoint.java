package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristRelativeSetpoint extends Command {
	double setpoint;
	
    public AutoSetWristRelativeSetpoint(double setpoint) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    	setTimeout(0.25);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    @Override
   	protected void execute() {
    	UpdateWristSetpoint.autoCommand = true;
    	UpdateWristSetpoint.autoSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint);
    	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint));

    }       
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.wrist.onTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
