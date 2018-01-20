package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightRobotToRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightScale() {
		 addSequential(new DriveStraightFusion(36000));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(673.98));
		 //Drop off Block
	}
}
