package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(11676, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(224186, 1));
		 addSequential(new TurnWithGyro(-90));  
		 addSequential(new DriveStraightWithGyroAndEncoders(7424, 1));
		 //Drop of Block
	}
}
