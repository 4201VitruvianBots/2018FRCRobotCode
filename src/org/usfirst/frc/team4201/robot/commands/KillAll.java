package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class KillAll extends InstantCommand {

    public KillAll() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
        requires(Robot.arm);
        requires(Robot.wrist);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wrist.disable();
    	Robot.arm.disable();
    	Robot.elevator.disable();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
