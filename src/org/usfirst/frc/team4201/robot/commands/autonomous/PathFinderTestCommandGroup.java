package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.PathfinderGen;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderTestCommandGroup extends CommandGroup{
	
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
	
	
	public PathFinderTestCommandGroup() {

		//addSequential(new PathFinder(paths[0]));
		//addSequential(new DriveTurnWithGyro(-90));
		//addSequential(new PathFinder(paths[1]));
		
		AutoPaths.driveStraightOne[1] = new Waypoint(4.25, 0, 0);
		addSequential(new Delay(1));
		//addSequential(new PathFinder(AutoPaths.test));
		addSequential(new PathFinder(AutoPaths.driveStraightOne));
		addSequential(new DriveTurnWithGyro(-90));
		//addSequential(new Delay(3));
		AutoPaths.driveStraightTwo[0] = new Waypoint(0, 0, Pathfinder.d2r(90));
		AutoPaths.driveStraightTwo[1] = new Waypoint(0, 0.666666666666666667, Pathfinder.d2r(90));
		addSequential(new PathFinder(AutoPaths.driveStraightTwo));
		addSequential(new Delay(1));
		AutoPaths.driveStraightThree[0] = new Waypoint(0, 0, Pathfinder.d2r(90));
		AutoPaths.driveStraightThree[1] = new Waypoint(0, -1.4, Pathfinder.d2r(90));
		addSequential(new PathFinder(AutoPaths.driveStraightThree));
		
		
		// Use PathfinderTrajectories instead
		//addSequential(new PathFinderTrajectories(PathfinderGen.trajectories[0]));
		//addSequential(new DriveTurnWithGyro(-90));
		//addSequential(new PathFinderTrajectories(PathfinderGen.trajectories[1]));
	}
}
