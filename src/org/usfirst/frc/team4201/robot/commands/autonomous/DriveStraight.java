package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraight extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public DriveStraight() {
		 addSequential(new Delay());
		 addSequential(new DriveStraightFusion(6971));
	}
}

