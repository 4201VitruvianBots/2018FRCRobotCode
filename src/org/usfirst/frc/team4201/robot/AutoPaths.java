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
	

	public static Waypoint[] centerRobotToLeftSwitch = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[1],
		RobotMap.secondaryWaypoints[1],
		RobotMap.primaryWaypoints[4],
	};  //Drop off cube

	public static Waypoint[] centerRobotToRightSwitch = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[1],
		RobotMap.secondaryWaypoints[0],
		RobotMap.primaryWaypoints[3],
	};  //Drop off cube

	public static Waypoint[] leftRobotToLeftSwitch = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[2],
		RobotMap.secondaryWaypoints[3],
		RobotMap.primaryWaypoints[6],
	};  //Drop off cube. If going to left scale then use LeftSwitchToLeftCube.
	
	public static Waypoint[] leftSwitchToLeftCube = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[6],
		RobotMap.secondaryWaypoints[3],
		RobotMap.secondaryWaypoints[5],
		RobotMap.secondaryWaypoints[9],
		RobotMap.primaryWaypoints[8],
	};	 //Go forward and pick up cube. Then use LeftCubeToLeftScale	
	
	public static Waypoint[] leftCubeToLeftScale = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[8],
		RobotMap.primaryWaypoints[10],
		RobotMap.primaryWaypoints[12],
	}; 	//Drop off cube
	
	public static Waypoint[] leftCubeToRightScale = new Waypoint[] {	// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[8],           
		RobotMap.secondaryWaypoints[9],
		RobotMap.secondaryWaypoints[7],
		RobotMap.primaryWaypoints[9],
		//136.5-5.81=130.69
	};
	
	public static Waypoint[] leftCubeToRightSwitch = new Waypoint[] {
		RobotMap.primaryWaypoints[8],
		RobotMap.secondaryWaypoints[9],
		RobotMap.secondaryWaypoints[6],
		RobotMap.secondaryWaypoints[4],
		RobotMap.primaryWaypoints[5],
	};
	
	public static Waypoint[] leftScaleToLeftCube = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[10],
		RobotMap.secondaryWaypoints[9],       
		RobotMap.secondaryWaypoints[5],		//backing up to give clearance
		RobotMap.secondaryWaypoints[9],
		RobotMap.primaryWaypoints[8],
	};

	public static Waypoint[] leftRobotToLeftScale = new Waypoint[] {		// Create a spline path & don't forget to declare angle 
		RobotMap.primaryWaypoints[2],
		RobotMap.secondaryWaypoints[3],
		RobotMap.secondaryWaypoints[5],
		RobotMap.primaryWaypoints[10],
	};  //Drop off cube

	public static Waypoint[] rightRobotToRightSwitch = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[0],
		RobotMap.secondaryWaypoints[2],
		RobotMap.primaryWaypoints[5],
	};  //Drop off cube
	
	public static Waypoint[] rightSwitchToRightCube = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[5],
		RobotMap.secondaryWaypoints[2],
		RobotMap.secondaryWaypoints[4],
		RobotMap.secondaryWaypoints[6],
		RobotMap.primaryWaypoints[7],
	};	 //Go forward and pick up cube. Then use LeftCubeToLeftScale	
	
	public static Waypoint[] rightCubeToRightScale = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[7],
		RobotMap.primaryWaypoints[9],
		RobotMap.primaryWaypoints[11],
	}; 	//Drop off cube
	
	public static Waypoint[] rightCubeToLeftScale = new Waypoint[] {	// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[7],           
		RobotMap.secondaryWaypoints[6],
		RobotMap.secondaryWaypoints[8],
		RobotMap.primaryWaypoints[10],
		//136.5-5.81=130.69
	};

	public static Waypoint[] rightCubeToLeftSwitch = new Waypoint[] {
		RobotMap.primaryWaypoints[7],
		RobotMap.secondaryWaypoints[6],
		RobotMap.secondaryWaypoints[9],
		RobotMap.secondaryWaypoints[5],
		RobotMap.primaryWaypoints[6],
	};
	
	public static Waypoint[] rightScaleToRightCube = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[9],
		RobotMap.secondaryWaypoints[6],       
		RobotMap.secondaryWaypoints[4],		//backing up to give clearance
		RobotMap.secondaryWaypoints[6],
		RobotMap.primaryWaypoints[7],
		};
		
	public static Waypoint[] rightRobotToRightScale = new Waypoint[] {		// Create a spline path & don't forget to declare angle
		RobotMap.primaryWaypoints[0],
		RobotMap.secondaryWaypoints[2],
		RobotMap.secondaryWaypoints[4],
		RobotMap.primaryWaypoints[9],	        
	};  //Drop off cube
	
	public static Waypoint[] test = new Waypoint[] {		// Create a spline path & don't forget to declare angle
			new Waypoint(0, 0, 0),
			new Waypoint(1, 1, Pathfinder.d2r(45)),
			new Waypoint(2, 2, Pathfinder.d2r(90)),
		};  //Drop off cube
}
