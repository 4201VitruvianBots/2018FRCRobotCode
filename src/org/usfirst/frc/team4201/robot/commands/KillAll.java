package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Elevator;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class KillAll extends InstantCommand{
	/**	This command kills all of the PID Controllers on our elevator/arm/wrist mechanisms.
	 * 	This is an operator command in the event that the PIDController dies (e.g. sensor dies, readings go off for some reason, etc.)
	 * 	This allows us to protect our mechanisms (So that the robot doesn't suddenly decide, for example, to drive the wrist at full speed because it sees a large error)
	 * 	This also allows us to operate the mechanisms manually (not ideal), but its better than doing nothing for the rest of a match.
	 */
	public KillAll(int channel) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
		requires(Robot.arm);
		requires(Robot.wrist);
		setInterruptible(false);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.elevator.disable();
		Robot.arm.disable();
		Robot.wrist.disable();
		Elevator.state  = 1;
		Arm.state = 1;
		Wrist.state = 1;
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
