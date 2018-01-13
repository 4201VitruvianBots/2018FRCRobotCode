package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightRobotToRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(8834, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(162.68 ,1));
		 //Drop off Block
	}
}
