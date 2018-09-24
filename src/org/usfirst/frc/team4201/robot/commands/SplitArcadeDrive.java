/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.DriveTrain;

/**
 * An example command.  You can replace me with your own command.
 */
public class SplitArcadeDrive extends Command {
	public SplitArcadeDrive() {
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
		double leftYValue = Math.pow(Robot.oi.leftJoystick.getRawAxis(1), DriveTrain.drivePowerExponent);
		double rightXValue = Math.pow(Robot.oi.getRightX(), DriveTrain.drivePowerExponent);
		
		Robot.driveTrain.setTeleOpDriveOutput(Robot.oi.leftJoystick.getRawAxis(1), rightXValue);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.setTeleOpDriveOutput(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
