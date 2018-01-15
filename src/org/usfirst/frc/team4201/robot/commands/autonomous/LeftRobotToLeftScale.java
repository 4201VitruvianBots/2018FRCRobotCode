package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(36598, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(673.96, 1));
		 //Drop off Block
	}
}
