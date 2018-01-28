package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class LeftRobotToRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public LeftRobotToRightScale() {
		 addSequential(new Delay());
		 addSequential(new DriveStraightFusion(24186));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(21610));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(12412));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(673.96));
		 //Drop off Block
	}
}
