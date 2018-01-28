package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftScale() {
		 addSequential(new Delay());
		 addSequential(new DriveStraightFusion(36598));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(673.96));
		 //Drop off Block
	}
}
