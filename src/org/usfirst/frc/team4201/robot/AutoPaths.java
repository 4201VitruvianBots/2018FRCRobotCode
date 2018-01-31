package org.usfirst.frc.team4201.robot;

import jaci.pathfinder.*;

public class AutoPaths {
	// +/- X is forward/backwards, +/- Y is left/right, +/- angle is left/right (unlike gyro, which is +/- right/left).
	// Keep all units in terms of yards for consistency, unless otherwise stated.

	public static Waypoint[] driveStraight = new Waypoint[] {
			new Waypoint(0, 0, 0),
			new Waypoint(8, 0, 0),
	};
	
	public static Waypoint[] centerRobotToLeftSwitch = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(1, 1, Pathfinder.d2r(45)),          
		new Waypoint(3, 2, 0),
	};  //Drop off cube
	
	public static Waypoint[] centerRobotToRightSwitch = new Waypoint[] {		// Create a spline path 
			new Waypoint(0, 0, 0),                 
			new Waypoint(1, -1, Pathfinder.d2r(-45)),          
			new Waypoint(3.1, -1.35, 0),
	};  //Drop off cube

	public static Waypoint[] leftRobotToLeftSwitch = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(4, 1/3, Pathfinder.d2r(45)),          
		new Waypoint(4, 2/3, Pathfinder.d2r(90)),
	};  //Drop off cube. If going to left scale then use LeftSwitchToLeftCube.
	
	public static Waypoint[] leftSwitchToLeftCube = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(-1.5, 0, Pathfinder.d2r(0)),          
		new Waypoint(-1, -0.5, Pathfinder.d2r(-45)),       
		new Waypoint(-0.5, -13/12, Pathfinder.d2r(0)),  
		new Waypoint(0.722, -13/12, Pathfinder.d2r(0)),
		new Waypoint(0.722, -13/12, Pathfinder.d2r(90)),
		new Waypoint(0.722, -13/12, Pathfinder.d2r(90)),
	};	 //Go forward and pick up cube. Then use LeftCubeToLeftScale	
	
	public static Waypoint[] leftCubeToLeftScale = new Waypoint[] {		// Create a spline path 
			new Waypoint(0, 0, 0),                 
			new Waypoint(-8/36, -0.2, Pathfinder.d2r(-45)),    
			new Waypoint(-17/36, -0.5, Pathfinder.d2r(-90)),        
			new Waypoint(-17/36, 1.384, Pathfinder.d2r(-90)),       
			new Waypoint(-1, 2, Pathfinder.d2r(-135)),  
			new Waypoint(-2, 2, Pathfinder.d2r(-180)),
			new Waypoint(-4.5, 1.5, Pathfinder.d2r(-225)),
			new Waypoint(-4.765, 1.2226, Pathfinder.d2r(-270)),
		}; 	//Drop off cube

	public static Waypoint[] leftRobotToLeftScale = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(631/72, 0, Pathfinder.d2r(45)),          
		new Waypoint(631/72, 0.233, Pathfinder.d2r(90)),
	};  //Drop off cube

	public static Waypoint[] rightRobotToRightSwitch = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(4, -1/3, Pathfinder.d2r(-45)),          
		new Waypoint(4, -2/3, Pathfinder.d2r(-90)),
	};  //Drop off cube

	public static Waypoint[] rightRobotToRightScale = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),                 
		new Waypoint(631/72, 0, Pathfinder.d2r(-45)),          
		new Waypoint(631/72, -0.233, Pathfinder.d2r(-90)),
	};  //Drop off cube
}
