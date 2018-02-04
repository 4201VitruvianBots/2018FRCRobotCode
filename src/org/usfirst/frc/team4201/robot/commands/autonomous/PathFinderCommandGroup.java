package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderCommandGroup extends CommandGroup{
	public PathFinderCommandGroup() {
		 AutoPaths.driveStraightOne[1] = new Waypoint(4.5, 0, 0);
		 //addSequential(new PathFinder(AutoPaths.test));
		 addSequential(new PathFinder(AutoPaths.driveStraightOne));
		 addSequential(new DriveTurnWithGyro(-90));
		 //addSequential(new Delay(3));
		 AutoPaths.driveStraightTwo[0] = new Waypoint(0, 0, Pathfinder.d2r(90));
		 AutoPaths.driveStraightTwo[1] = new Waypoint(0, 1, Pathfinder.d2r(90));
		 addSequential(new PathFinder(AutoPaths.driveStraightTwo));
	}
}
