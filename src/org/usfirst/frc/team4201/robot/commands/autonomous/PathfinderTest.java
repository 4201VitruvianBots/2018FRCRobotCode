package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addSequential(new PathFinderRead("straightCalibration"));
	}
}
 