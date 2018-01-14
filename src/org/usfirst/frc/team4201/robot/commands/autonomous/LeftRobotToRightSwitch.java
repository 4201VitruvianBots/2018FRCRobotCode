package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class LeftRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToRightSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(12093, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(7209.98, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(3712, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(1380.98, 1));
		 //Drop off Block
	}
}
