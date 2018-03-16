package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleElevatorClimbMode extends InstantCommand{
	public ToggleElevatorClimbMode() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
		//requires(Robot.wings);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.elevator.getElevatorShiftersStatus() || Robot.elevator.getDiskBrakeStatus()) { // || Robot.wings.getWingsStatus()){
			Robot.elevator.setElevatorShiftersHigh();
			Robot.elevator.setDiskBrakeHigh();
			//Robot.wings.deployWings();
		} else {
			Robot.elevator.setElevatorShiftersLow();
			Robot.elevator.setDiskBrakeHigh();
			//Robot.wings.retractWings();
		}
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
