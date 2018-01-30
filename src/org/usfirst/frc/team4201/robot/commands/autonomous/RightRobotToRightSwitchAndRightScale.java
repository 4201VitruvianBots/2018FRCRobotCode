package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightRobotToRightSwitchAndRightScale extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public RightRobotToRightSwitchAndRightScale() {
		 addSequential(new DriveStraightFusion(167662));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(2761.96));
		 //Drop off Block
		 addSequential(new DriveStraightFusion(-2761.96));
		 addSequential(new DriveTurnWithGyro(90));  
		 addSequential(new DriveStraightFusion(4524));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(5777.96));
		 addSequential(new DriveTurnWithGyro(-90));  
		 //Start Intake
		 addSequential(new DriveStraightFusion(1972));
		 //Stop Intake
		 addSequential(new DriveStraightFusion(-1972));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(5777.96));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(18792));
		 addSequential(new DriveTurnWithGyro(-90));  
		 addSequential(new DriveStraightFusion(673.96));
		 //Drop off Block
	}
}