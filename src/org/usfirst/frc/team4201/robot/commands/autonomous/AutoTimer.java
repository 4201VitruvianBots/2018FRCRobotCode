package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoTimer extends InstantCommand {
	
    public AutoTimer(boolean run) {
    	requires(Robot.controls);
    	
    	if(run) {
    		Robot.controls.autoTimer.reset();
    		Robot.controls.autoTimer.start();
    	} else
    		Robot.controls.autoTimer.stop();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
