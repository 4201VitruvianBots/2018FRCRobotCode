package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderCommandGroup extends CommandGroup{
	public PathFinderCommandGroup() {
		 addSequential(new PathFinder(AutoPaths.centerRobotToRightSwitch));
	}
}
