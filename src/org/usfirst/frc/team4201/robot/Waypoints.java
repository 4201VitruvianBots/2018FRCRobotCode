package org.usfirst.frc.team4201.robot;

import jaci.pathfinder.Waypoint;

public class Waypoints {
	// Divide the auto routines into segments, with each segment being separated by a mechanism action (e.g. eject cube from intake))
	
	
	public static final Waypoint[] driveStraight = {
		new Waypoint(0, 0, 0),
		new Waypoint(2, 0, 0)
	};
}
