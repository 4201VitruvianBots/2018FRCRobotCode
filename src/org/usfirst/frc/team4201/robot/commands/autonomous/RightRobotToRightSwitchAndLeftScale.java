package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightRobotToRightSwitchAndLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightSwitchAndLeftScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(8381, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(1380.98, 1));
		 //Drop off Block
		 addSequential(new DriveStraightWithGyroAndEncoders(-1380.98, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(2262, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(2888.98, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 //Start Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(986, 1));
		 //Stop Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(-986, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(7917, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(9396, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(336.98, 1));
		 //Drop off Block
	}
}
