package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftSwitchAndLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftSwitchAndLeftScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(3206, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(666.68, 1));
		 //Drop off Block
		 addSequential(new DriveStraightWithGyroAndEncoders(-666.68, 1));
	}
}
