package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(4190.5, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(690.49, 1));
		 //Drop off Block
	}
}
