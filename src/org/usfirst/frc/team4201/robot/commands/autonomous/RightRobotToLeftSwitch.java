package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToLeftSwitch extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToLeftSwitch() {
		 addSequential(new DriveStraightFusion(11676));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(224186));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(7424));
		 //Drop of Block
	}
}
