package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(5838, 1));
		 addSequential(new DriveTurnWithGyro2(0,-90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(3480.68, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(1792, 1));
		 //Drop of Block
	}
}
