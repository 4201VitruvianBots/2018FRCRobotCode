package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetIntakeMotorOutputs extends InstantCommand {
	double output;
	
	public SetIntakeMotorOutputs(double output) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);

		this.output = output;
		
		setInterruptible(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.setIntakeMotorOutput(output);
		
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		if(output < 0)
			Intake.isCubePresent = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

