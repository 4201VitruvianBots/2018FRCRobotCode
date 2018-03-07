package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristRelativeSetpoint extends Command {
	double setpoint;
	Timer stopwatch;
	boolean lock;
	
    public AutoSetWristRelativeSetpoint(double setpoint) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    	stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UpdateWristSetpoint.autoCommand = true;
    }
    
    @Override
   	protected void execute() {
    	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint));
    }       
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.wrist.onTarget() && !lock){
    		stopwatch.start();
    		lock = true;
    	} else {
    		stopwatch.stop();
    		stopwatch.reset();
    		lock = false;
    	}
    	
    	return stopwatch.get() > 0.25;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
