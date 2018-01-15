package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public CenterRobotToRightSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(348, 1));
		 addSequential(new DriveTurnWithGyro2(0, 45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(4103.5, 1));
		 addSequential(new DriveTurnWithGyro2(0, -45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(2051.75, 1));
		 //Drop off Block
	}
}
