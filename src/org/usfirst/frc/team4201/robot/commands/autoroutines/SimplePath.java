package org.usfirst.frc.team4201.robot.commands.autoroutines;

import org.usfirst.frc.team4201.robot.commands.PathFinderRead;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SimplePath extends CommandGroup{
	public SimplePath(){
		addSequential(new PathFinderRead("DriveStraight"));
	}
}
