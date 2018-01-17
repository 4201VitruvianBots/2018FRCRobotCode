package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public CenterRobotToRightSwitch() {
		 addSequential(new DriveStraightFusion(5392));
		 addSequential(new DriveTurnWithGyro(45)); 
		 addSequential(new DriveStraightFusion(16414));
		 addSequential(new DriveTurnWithGyro(-45));  
		 addSequential(new DriveStraightFusion(4209));
		 //Drop off Block
	}
}
