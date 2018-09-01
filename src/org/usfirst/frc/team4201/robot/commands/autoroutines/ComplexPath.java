package org.usfirst.frc.team4201.robot.commands.autoroutines;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ComplexPath extends CommandGroup{
	public ComplexPath(){
		addSequential(new PathFinderRead("DriveStraight"));
		addSequential(new Wait(1.5));
		addSequential(new PathFinderRead("DriveSpline"));
	}
}
