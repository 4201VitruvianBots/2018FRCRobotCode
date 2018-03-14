package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Elevator;
import org.usfirst.frc.team4201.robot.subsystems.Intake;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**	This command kills all of the PID Controllers on our elevator/arm/wrist mechanisms.
 * 	This is an operator command in the event that the PIDController dies (e.g. sensor dies, readings go off for some reason, etc.)
 * 	This allows us to protect our mechanisms (So that the robot doesn't suddenly decide, for example, to drive the wrist at full speed because it sees a large error)
 * 	This also allows us to operate the mechanisms manually (not ideal), but its better than doing nothing for the rest of a match.
 */
public class KillAll extends InstantCommand{
	
	public KillAll() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
		requires(Robot.arm);
		requires(Robot.wrist);
		requires(Robot.intake);
		setInterruptible(false);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Interrupt all commands currently in queue
		Scheduler.getInstance().removeAll();
		
		// Disable all PIDControllers
		Robot.elevator.disable();
		Robot.arm.disable();
		Robot.wrist.disable();
		
		// Set all subsystem states to 1 (Fault mode)
		RobotMap.ElevatorState  = 1;
		RobotMap.ArmState = 1;
		RobotMap.WristState = 1;
		Robot.oi.setWristManualMode();
		//Intake.isCubePresent = false;
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
