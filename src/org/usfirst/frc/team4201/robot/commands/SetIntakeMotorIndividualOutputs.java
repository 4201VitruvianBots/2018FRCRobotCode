package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetIntakeMotorIndividualOutputs extends InstantCommand {
	double leftOutput, rightOutput;
	
	public SetIntakeMotorIndividualOutputs(double leftOutput, double rightOutput) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);

		this.leftOutput = leftOutput;
		this.rightOutput = rightOutput;
		
		setInterruptible(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
			Robot.intake.setIntakeMotorOutput(leftOutput, rightOutput);
		
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		if(leftOutput < 0 && rightOutput < 0)
			Intake.isCubePresent = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

