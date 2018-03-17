package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class EnableClimbMode extends InstantCommand{
	
	public EnableClimbMode() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
		//requires(Robot.climber);
		//requires(Robot.wings);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.elevator.setElevatorShiftersHigh();
		Robot.elevator.setDiskBrakeHigh();
		//Robot.climber.deployClimbers();
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
