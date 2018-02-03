package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetWristSetpoint2 extends InstantCommand {
	
	double inc;
    public SetWristSetpoint2(double increment) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.inc = increment;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Keep a running count of how many times this command has been declared
    	/*
    	Robot.arm.wristCommandCount++;
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.wristPIDController.getSetpoint() + inc < Robot.arm.wristForwardAbsoluteLimit  + Robot.arm.wristForwardSoftLimit && 
		   Robot.arm.wristPIDController.getSetpoint() + inc > Robot.arm.wristReverseAbsoluteLimit)
			Robot.arm.setWristSetpoint(inc);
		else
	        Robot.oi.enableXBoxRightRumbleTimed();
	        */
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.disableXBoxRightRumble();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
