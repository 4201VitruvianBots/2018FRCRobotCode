package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetIntakeMotorOutputs extends Command {
	double leftOutput, rightOutput;
	int state = 0;
	
	public SetIntakeMotorOutputs(double output) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
		
		setInterruptible(true);
		this.leftOutput = output;
	}
	
	public SetIntakeMotorOutputs(double leftOutput, double rightOutput) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
		
		state = 1;
		
		setInterruptible(true);
		this.leftOutput = leftOutput;
		this.rightOutput = rightOutput;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(state == 0)
			Robot.intake.setIntakeMotorOutput(leftOutput);
		else
			Robot.intake.setIntakeMotorOutput(leftOutput, rightOutput);
		
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
		state = 0;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

