package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.PathfinderGen;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderLeftSwithLeftScale extends CommandGroup{
	
	public static Waypoint[][] paths = new Waypoint[2][];
	
	public static void setPaths() {
		// Set your paths here. Will be called in robotInit to pull them to be generated;
		
		paths[0] = new Waypoint[] {
			new Waypoint(0, 0, 0),	// Editing a waypoint like this may be the source of the weird paths.
			new Waypoint(4.5, 0, 0)	// Editing a waypoint like this may be the source of the weird paths.	
		};
		paths[1] = new Waypoint[] {
			new Waypoint(0, 0, Pathfinder.d2r(90)),	// Editing a waypoint like this may be the source of the weird paths.
			new Waypoint(0, 1, Pathfinder.d2r(90))	// Editing a waypoint like this may be the source of the weird paths.	
		};
	}
	
	
	public PathFinderLeftSwithLeftScale() {
		RobotMap.primaryWaypoints[6].angle = -90;
		addSequential(new PathFinder(AutoPaths.leftRobotToLeftSwitch));

	}
}
