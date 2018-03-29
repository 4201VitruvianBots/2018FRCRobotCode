package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristAbsoluteSetpoint extends Command {
	double setpoint;
	boolean awaitArm = false;
	
    public AutoSetWristAbsoluteSetpoint(double setpoint) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    }
    
    public AutoSetWristAbsoluteSetpoint(double setpoint, boolean awaitArm) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    	setTimeout(0.25);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    @Override
   	protected void execute() {
    	if(Robot.arm.onTarget() && awaitArm){
        	UpdateWristSetpoint.autoCommand = true;
        	UpdateWristSetpoint.autoSetpoint = setpoint;
        	Robot.wrist.setSetpoint(setpoint);
    	} else {
        	UpdateWristSetpoint.autoCommand = true;
        	UpdateWristSetpoint.autoSetpoint = setpoint;
        	Robot.wrist.setSetpoint(setpoint);
    	}
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
