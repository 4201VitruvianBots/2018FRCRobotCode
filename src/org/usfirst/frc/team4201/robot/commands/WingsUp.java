package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class WingsUp extends InstantCommand{
	public WingsUp() {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.wings);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		/*
		if(Robot.wings.getWingStatus());
			Robot.wings.retractWings();
			*/
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
