package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSetArmSetpoint extends Command {
	double setpoint, initialPosition;
	
	public AutoSetArmSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialPosition = Robot.arm.getAngle();
    	
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.checkLimits(setpoint))
    		Robot.arm.setSetpoint(setpoint);
    }
    
    protected void execute() {
    	UpdateWristSetpoint.autoCommand = true;
    	/*
    	if(Robot.arm.getAngle() > 16 && initialPosition <= 16) {
			UpdateWristSetpoint.autoSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(90);
			Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(90));
		} else if(Robot.arm.getAngle() > 16 && initialPosition > 16) {
			UpdateWristSetpoint.autoSetpoint = RobotMap.wristRetractedAngle;
			Robot.wrist.setSetpoint(RobotMap.wristRetractedAngle);
		}
		//*/
    }
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.arm.onTarget();
	}
    
    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
