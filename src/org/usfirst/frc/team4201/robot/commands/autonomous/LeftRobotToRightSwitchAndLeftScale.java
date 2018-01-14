package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToRightSwitchAndLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToRightSwitchAndLeftScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(18299, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(336.98, 1));
		 //Drop off Block
		 addSequential(new DriveStraightWithGyroAndEncoders(-336.98, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(6786, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(2888.98, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 //Start Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(986, 1));
		 //Stop Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(-986, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(7917, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(3712, 1));
		 addSequential(new DriveTurnWithGyro2(0, 90));
		 addSequential(new DriveStraightWithGyroAndEncoders(1380.98, 1));
		 //Drop off Block
	}
}
