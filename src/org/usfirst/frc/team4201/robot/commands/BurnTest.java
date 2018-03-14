package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BurnTest extends Command {
	Timer stopwatch;
	Boolean toggle = true;
    public BurnTest() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopwatch.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.setDirectOutput(1);
    	
    	if(stopwatch.get() % 15 == 0 && toggle){
    		Robot.elevator.setElevatorShiftersHigh();
    		toggle = false;
    	} else if(stopwatch.get() % 15 == 0) {
    		Robot.elevator.setElevatorShiftersLow();
    		toggle = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stopwatch.get() > 600;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setDirectOutput(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
