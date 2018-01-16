package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightRobotToLeftSwitchAndRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToLeftSwitchAndRightScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(36598, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(2761.96, 1));
		 //Drop off Block
		 addSequential(new DriveStraightWithGyroAndEncoders(2761.96, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(13572, 1));
		 addSequential(new TurnWithGyro(90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(5777.96, 1));
		 addSequential(new TurnWithGyro(-90));  
		 //Start Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(1972, 1));
		 //Stop Intake
		 addSequential(new DriveStraightWithGyroAndEncoders(-1972, 1));
		 addSequential(new TurnWithGyro(90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(15834, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(7424, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(673.98, 1));
		 //Drop off Block
	}
}
