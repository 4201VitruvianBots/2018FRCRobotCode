package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.PathfinderGen;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderCommandGroup extends CommandGroup{
	
	static Waypoint[][] paths;
	
	public static void setPaths() {
		// Set your paths here. Will be called in robotInit to pull them to be generated;
		
		paths[0] = AutoPaths.driveStraightOne;
		paths[0][1] = new Waypoint(4.5, 0, 0);	// Editing a waypoint like this may be the source of the weird paths.
		paths[1] = AutoPaths.driveStraightTwo;
		paths[1][0] = new Waypoint(0, 0, Pathfinder.d2r(90));
		paths[1][1] = new Waypoint(0, 1, Pathfinder.d2r(90));
		
	}
	
	
	public PathFinderCommandGroup() {
		/*
		 AutoPaths.driveStraightOne[1] = new Waypoint(4.5, 0, 0);
		 //addSequential(new PathFinder(AutoPaths.test));
		 addSequential(new PathFinder(AutoPaths.driveStraightOne));
		 addSequential(new DriveTurnWithGyro(-90));
		 //addSequential(new Delay(3));
		 AutoPaths.driveStraightTwo[0] = new Waypoint(0, 0, Pathfinder.d2r(90));
		 AutoPaths.driveStraightTwo[1] = new Waypoint(0, 1, Pathfinder.d2r(90));
		 addSequential(new PathFinder(AutoPaths.driveStraightTwo));
		 */
		
		 // Use PathfinderTrajectories instead
		 addSequential(new PathFinderTrajectories(PathfinderGen.trajectories[0]));
		 addSequential(new DriveTurnWithGyro(-90));
		 addSequential(new PathFinderTrajectories(PathfinderGen.trajectories[1]));
	}
}
