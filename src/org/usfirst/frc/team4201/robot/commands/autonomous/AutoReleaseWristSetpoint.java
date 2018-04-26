package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoReleaseWristSetpoint extends InstantCommand {

    public AutoReleaseWristSetpoint() {
    	requires(Robot.wrist);
    	
    	setInterruptible(false);
    	setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UpdateWristSetpoint.autoCommand = false;
    	
    	if(Robot.arm.getAngle() > 16) {
	    	UpdateWristSetpoint.autoSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(90);
	    	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(90));
    	} else {
        	UpdateWristSetpoint.autoSetpoint = RobotMap.wristRetractedAngle;
        	Robot.wrist.setSetpoint(RobotMap.wristRetractedAngle);
    	}
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
