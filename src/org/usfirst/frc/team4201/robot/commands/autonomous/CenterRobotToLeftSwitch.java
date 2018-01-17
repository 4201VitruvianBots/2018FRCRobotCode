package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public CenterRobotToLeftSwitch() {
		 addSequential(new DriveStraightFusion(5392));
		 addSequential(new DriveTurnWithGyro(-45)); 
		 addSequential(new DriveStraightFusion(9768));
		 addSequential(new DriveTurnWithGyro(45)); 
		 addSequential(new DriveStraightFusion(4120));
		 //Drop off Block
	}
}
