package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetWristScaleScoring extends Command {
	double setpoint;
	boolean check = false, relative = false;
	
    public AutoSetWristScaleScoring(double setpoint, boolean relative) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    	this.relative = relative;
    	setTimeout(0.2);
    }
    
    public AutoSetWristScaleScoring(double setpoint) {
    	requires(Robot.wrist);
    	
    	this.setpoint = setpoint;
    	setTimeout(0.2);
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
		if(Robot.arm.onTarget()){
			double tempSet = relative ? Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint) : setpoint;
	    	UpdateWristSetpoint.autoSetpoint = tempSet;
	    	Robot.wrist.setSetpoint(tempSet);
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
