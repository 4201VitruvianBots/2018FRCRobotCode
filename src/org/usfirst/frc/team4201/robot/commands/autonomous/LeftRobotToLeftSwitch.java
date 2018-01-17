package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToLeftSwitch() {
		 addSequential(new DriveStraightFusion(16762));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(2761.96));
		 //Drop off Block
	}
}
