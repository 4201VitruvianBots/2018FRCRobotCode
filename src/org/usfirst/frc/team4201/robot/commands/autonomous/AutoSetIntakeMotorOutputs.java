package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoSetIntakeMotorOutputs extends Command {
	double output, delay = 0;
	Timer stopwatch;
	
	public AutoSetIntakeMotorOutputs(double output, double timeout) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);

		this.output = output;
		
		setTimeout(timeout);
	}
	
	public AutoSetIntakeMotorOutputs(double output, double timeout, double delay) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);

		this.output = output;
		this.delay = delay;
		
		setTimeout(timeout);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(delay > 0) {
    		stopwatch = new Timer();
    		stopwatch.start();
    		
    		while(stopwatch.get() < delay) {
    			
    		}
    		
    		stopwatch.stop();
    	}
		
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
		Robot.intake.setIntakeMotorOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

