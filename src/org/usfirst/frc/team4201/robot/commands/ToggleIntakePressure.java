package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleIntakePressure extends InstantCommand {
	
	public ToggleIntakePressure() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
		setInterruptible(false);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (Robot.intake.getIntakePressureStatus())
			Robot.intake.retractIntakePressure();
		else
			Robot.intake.extendIntakePressure();
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
