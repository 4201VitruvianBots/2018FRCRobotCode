package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristToAbsoluteZero extends Command {
	double setpoint = -10;
	boolean check = false;
	
    public AutoSetWristToAbsoluteZero() {
    	requires(Robot.wrist);
    	
    	setTimeout(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UpdateWristSetpoint.autoCommand = true;
    	//UpdateWristSetpoint.autoSetpoint = RobotMap.wristRetractedAngle;
    	//Robot.wrist.setSetpoint(RobotMap.wristRetractedAngle);
    	check = false;
    }
    
    @Override
   	protected void execute() {
    	if(Robot.arm.onTarget() && Robot.arm.getAngle() < -46) {
	    	UpdateWristSetpoint.autoSetpoint = setpoint;
	    	Robot.wrist.setSetpoint(setpoint);
	    	check = true;
    	}
    }       
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return check && Robot.wrist.onTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
