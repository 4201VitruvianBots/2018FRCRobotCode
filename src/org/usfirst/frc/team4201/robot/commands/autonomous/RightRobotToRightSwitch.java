package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(24186, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(14419.96, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(7424, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(2761.96, 1));
		 //Drop off Block
	}
}
