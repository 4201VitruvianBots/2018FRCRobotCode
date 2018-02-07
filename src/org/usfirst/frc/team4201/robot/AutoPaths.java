package org.usfirst.frc.team4201.robot;

import jaci.pathfinder.*;

public class AutoPaths {
	// +/- X is forward/backwards, +/- Y is left/right, +/- angle is left/right (unlike gyro, which is +/- right/left).
	// Keep all units in terms of yards for consistency, unless otherwise stated.

	public static Waypoint[] corrector = new Waypoint[] {
		new Waypoint(RobotMap.waypointX, RobotMap.waypointY, Pathfinder.d2r(RobotMap.waypointAngle)),
	};
	

	public static Waypoint[] driveStraightOne = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 0, 0),
	};
	public static Waypoint[] driveStraightTwo = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 0, 0),
	};
	public static Waypoint[] driveStraightThree = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 0, 0),
	};
	public static Waypoint[] driveStraightFour = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 0, 0),
	};
	public static Waypoint[] driveStraightFive = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 0, 0),
	};
	

	public static Waypoint[] centerRobotToLeftSwitch = new Waypoint[] {		// Create a spline path
		new Waypoint(0, 0, 0),
		new Waypoint(1, 1, Pathfinder.d2r(45)),
		new Waypoint(3.1, 1.3, 0),
	};  //Drop off cube

	public static Waypoint[] centerRobotToRightSwitch = new Waypoint[] {		// Create a spline path
		new Waypoint(0, 0, 0),
		new Waypoint(1, -1, Pathfinder.d2r(-45)),
		new Waypoint(3.1, -1.35, 0),
	};  //Drop off cube

	public static Waypoint[] leftRobotToLeftSwitch = new Waypoint[] {		// Create a spline path
		new Waypoint(0, 0, 0),
		new Waypoint(3, 0, Pathfinder.d2r(0)),
		new Waypoint(3.5, -1/3, Pathfinder.d2r(-45)),
		new Waypoint(4, -2/3, Pathfinder.d2r(-90)),
	};  //Drop off cube. If going to left scale then use LeftSwitchToLeftCube.
	
	public static Waypoint[] leftSwitchToLeftCube = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, Pathfinder.d2r(90)),          
		new Waypoint(-1, -1, Pathfinder.d2r(45)),       
		new Waypoint(8, -1, Pathfinder.d2r(0)),  
		new Waypoint(9, 0, Pathfinder.d2r(90)),
		new Waypoint(8, -1, Pathfinder.d2r(180)),
	};	 //Go forward and pick up cube. Then use LeftCubeToLeftScale	
	
	public static Waypoint[] leftCubeToLeftScale = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 180),                 
		new Waypoint(-0.75, 2.5, Pathfinder.d2r(225)),    
		new Waypoint(-1.5, 5, Pathfinder.d2r(180)),
	}; 	//Drop off cube
	
	public static Waypoint[] leftCubeToRightScale = new Waypoint[] {
		new Waypoint(0, 0, 180),                 
		new Waypoint(-0.2, -8/36, Pathfinder.d2r(135)),    
		new Waypoint(-0.5, -17/36, Pathfinder.d2r(90)),
		new Waypoint(3, -17/36, Pathfinder.d2r(90)),
		new Waypoint(5, -1, Pathfinder.d2r(45)),
		new Waypoint(5, -3.5, Pathfinder.d2r(0)),
		new Waypoint(-130.69/36, -4, Pathfinder.d2r(-45)),
		//136.5-5.81=130.69
	};
	
	public static Waypoint[] leftScaleToLeftCube = new Waypoint[] {
		new Waypoint(0, 0, 90),                 
		new Waypoint(0, -2, Pathfinder.d2r(90)),          
		new Waypoint(0.5, -1.5, Pathfinder.d2r(135)),       
		new Waypoint(4, -1, Pathfinder.d2r(180)),
		new Waypoint(5.66388889, 0.433666667, Pathfinder.d2r(135)),
		new Waypoint(6.38, 0.43366666666667, Pathfinder.d2r(90)),
		new Waypoint(6.38, 0.43366666666667, Pathfinder.d2r(180)),
	};

	public static Waypoint[] leftRobotToLeftScale = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),
		new Waypoint(631/72, 0, Pathfinder.d2r(45)),          
		new Waypoint(631/72, 0.233, Pathfinder.d2r(90)),
	};  //Drop off cube

	public static Waypoint[] rightRobotToRightSwitch = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),
		new Waypoint(3.5, 0, Pathfinder.d2r(0)),
		new Waypoint(3.75, 0.25, Pathfinder.d2r(45)),
		new Waypoint(4, 0.5, Pathfinder.d2r(90)),
	};  //Drop off cube

	public static Waypoint[] rightRobotToRightScale = new Waypoint[] {		// Create a spline path 
		new Waypoint(0, 0, 0),
		new Waypoint(5, 0, 0),
		new Waypoint(7, 1, Pathfinder.d2r(30)),          
	};  //Drop off cube
	public static Waypoint[] test = new Waypoint[] {		// Create a spline path 
			new Waypoint(0, 0, 0),
			new Waypoint(1, 1, Pathfinder.d2r(45)),
			new Waypoint(2, 2, Pathfinder.d2r(90)),
		};  //Drop off cube
}
