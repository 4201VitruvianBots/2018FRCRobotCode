package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleDriveShifters extends InstantCommand{
	public ToggleDriveShifters() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.driveTrain.getDriveShiftStatus())
			Robot.driveTrain.setDriveShiftLow();
		else
			Robot.driveTrain.setDriveShiftHigh();
	}
    
	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
