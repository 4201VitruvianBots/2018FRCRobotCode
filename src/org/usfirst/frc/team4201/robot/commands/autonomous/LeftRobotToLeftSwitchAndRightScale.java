package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftSwitchAndRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftSwitchAndRightScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(16762, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(2761.96, 1));
		 //Drop off Block
		 addSequential(new DriveStraightWithGyroAndEncoders(-2761.96, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(4524, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(5777.96, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 //Start Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(1972, 1));
		 //Stop Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(-1972, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(15834, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(18792, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(673.96, 1));
		 //Drop off Block
	}
}
