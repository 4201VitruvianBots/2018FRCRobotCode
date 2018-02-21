package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderTestCommandGroup extends CommandGroup{
	
	public PathFinderTestCommandGroup() {
		addSequential(new PathFinderRead("centerToLeftSwitch", true));
		//addSequential(new DriveStraightWithGyro(3, 0.5));
	}
}
