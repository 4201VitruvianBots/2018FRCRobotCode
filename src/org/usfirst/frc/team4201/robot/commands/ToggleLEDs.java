package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleLEDs extends InstantCommand{
	int channel;
	
	public ToggleLEDs(int channel) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.controls);
		this.channel = channel;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(channel >= 0 && channel <= 2)
			Robot.controls.setRGBF(channel, !Robot.controls.getRGBFStatus(channel));
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
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
