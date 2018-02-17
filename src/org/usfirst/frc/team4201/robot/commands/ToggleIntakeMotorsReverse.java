package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleIntakeMotorsReverse extends InstantCommand {
	public ToggleIntakeMotorsReverse() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.intake.intakeMotors[0].get() != -0.75)
			Robot.intake.setIntakeMotorOutput(-0.75);
		else
			Robot.intake.setIntakeMotorOutput(0);
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
