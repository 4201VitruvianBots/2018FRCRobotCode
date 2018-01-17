package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToRightSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightSwitch() {
		 addSequential(new DriveStraightFusion(24186));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(14419.96));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(7424));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(2761.96));
		 //Drop off Block
	}
}
