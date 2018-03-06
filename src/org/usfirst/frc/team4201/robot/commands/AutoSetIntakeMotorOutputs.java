package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSetIntakeMotorOutputs extends Command {
	double output;
	
	public AutoSetIntakeMotorOutputs(double output, double timeout) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);

		this.output = output;
		
		setTimeout(timeout);
		setInterruptible(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.setIntakeMotorOutput(output);
	}
	
	@Override
   	protected void execute() {
		Robot.intake.setIntakeMotorOutput(output);
    }       
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intake.setIntakeMotorOutput(0.1);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

