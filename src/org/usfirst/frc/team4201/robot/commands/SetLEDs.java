package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetLEDs extends InstantCommand{
	int bits;
	
	public SetLEDs(int channels) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.controls);
		this.bits = channels;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		boolean c1 = bits % 2 == 1 ? true : false;
		boolean c2 = bits == 2 || bits == 3 || bits > 5 ? true : false;
		boolean c3 = bits > 3 ? true : false;
		
		Robot.controls.setRGBF(0, c3);
		Robot.controls.setRGBF(1, c2);
		Robot.controls.setRGBF(3, c1);
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
