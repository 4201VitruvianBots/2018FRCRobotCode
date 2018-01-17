package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToLeftScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToLeftScale() {
		 addSequential(new DriveStraightFusion(24186));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(21611.98));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(12412));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(673.96));
		 //Drop off Block
	}
}
