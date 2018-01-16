package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Turn extends CommandGroup{
	//all measurements are in encoder counts.  See RobotMap for conversion rate.
	public Turn() {
		 addSequential(new DriveTurnWithGyro(90));
	}
}
