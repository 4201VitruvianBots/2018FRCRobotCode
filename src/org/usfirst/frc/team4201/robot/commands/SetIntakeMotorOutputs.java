package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetIntakeMotorOutputs extends Command {
	double output;
	
	public SetIntakeMotorOutputs(double output) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
		
		setInterruptible(true);
		this.output = output;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.setIntakeMotorOutput(output);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intake.setIntakeMotorOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

