package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public CenterRobotToRightSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(1392, 1));
		 addSequential(new TurnWithGyro(45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(16414, 1));
		 addSequential(new TurnWithGyro(-45));  
		 addSequential(new DriveStraightWithGyroAndEncoders(8209, 1));
		 //Drop off Block
	}
}
