package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public CenterRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(1392, 1));
		 addSequential(new DriveTurnWithGyro2(0, -45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(9768, 1));
		 addSequential(new DriveTurnWithGyro2(0, 45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(8120, 1));
		 //Drop off Block
	}
}
