package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetSplitArcadeDrive extends Command{
	boolean limit = false;
	
	public SetSplitArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double throttle = Robot.oi.getLeftY();
		
		if(Robot.arm.getAngle() > 30 && throttle < 0)
			throttle = Math.max(throttle, -0.5);
		
		Robot.driveTrain.setDriveOutput(throttle, Robot.oi.getRightX());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.setTankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
