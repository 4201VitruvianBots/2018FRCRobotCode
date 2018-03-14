package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleElevatorShifters extends InstantCommand{
	public ToggleElevatorShifters() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.elevator.getElevatorShiftersStatus())
			Robot.elevator.setElevatorShiftersLow();
		else
			Robot.elevator.setElevatorShiftersHigh();
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
