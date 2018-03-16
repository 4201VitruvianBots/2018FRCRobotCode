/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

//5ft = 6971.  116 encoder counts in an inch. (S4T-360-250)


public class RobotMap {
	
	
	
	public static Trajectory path; 
	
	// USB ADDRESSES
	public static final int leftJoystick = 0;
	public static final int rightJoystick = 1;
	public static final int xBoxController = 2;
	
	// Sensors
	public static final int leftEncoderA = 10;
	public static final int leftEncoderB = 11;
	public static final int rightEncoderA = 12;
	public static final int rightEncoderB = 13;
	
	/*	ADDRESS VALUES
	 *  
	 *  CAN ADDRESSES
	 *  0-19: Major Modules
	 *  +0: PDP
	 *  +1-9: VRMs
	 *  +11-19: PCMs
	 *  
	 *  20-59: Motor Controllers
	 *  +20-24(26): DriveTrain Motors
	 */
	
	
	// Electrical Modules
	public static final int PDP = 0;
	public static final int VRMOne = 1;
	
	// Pneumatic Modules & their devices
	public static final int PCMOne = 11;
		public static final int driveTrainShifterForward = 0;
		public static final int driveTrainShifterReverse = 1;
		public static final int elevatorForward = 2;
		public static final int elevatorReverse = 3;
		public static final int intakeForward = 4;
		public static final int intakeReverse = 5;
		
	public static final int PCMTwo = 12;
		public static final int stabalizerReleaseForward = 0;
		public static final int stabalizerReleaseReverse = 1;
		public static final int stabalizerGrabForward = 2;
		public static final int stabalizerGrabReverse = 3;
		public static final int stabalizerEngageForward = 4;
		public static final int stabalizerEngageReverse = 5;
		public static final int rightArmForward = 6;
		public static final int rightArmReverse = 7;
		
	
	// Motor Controllers
	public static final int driveTrainLeftFront = 20;
	public static final int driveTrainLeftRear = 21;
	public static final int driveTrainRightFront = 22;
	public static final int driveTrainRightRear = 23;
	public static final int intakeLeft = 24;
	public static final int intakeRight = 25;
	public static final int elevatorA = 26;
	public static final int elevatorB = 27;
	public static final int fourBar = 28;
	
	
	public static final int red = 0;
	public static final int blue = 1;
	
    public static double delay = 10;
    public static double waypointX = 0;
    public static double waypointY = 0;
    public static double waypointAngle = 0;
    
    
    public static Waypoint[] primaryWaypoints = new Waypoint[] {
    	new Waypoint(0, 0, 0),
    	new Waypoint(0, 2.833333333333333, 0),
    	new Waypoint(0, 6.277777777777778, 0),
    	new Waypoint(3.1, 1.5, 0),
    	new Waypoint(3.1, 4.5, 0),
    	new Waypoint(4.3333333333333, 0.5, Pathfinder.d2r(90)),
    	new Waypoint(4.3333333333333, 5.77777777777778, Pathfinder.d2r(-90)),
    	new Waypoint(5.6666666666667, 1.38888888888889, Pathfinder.d2r(180)),
    	new Waypoint(5.6666666666667, 4.88888888888889, Pathfinder.d2r(180)),
    	new Waypoint(7.3333333333333, 0.88888888888889, 0),
    	new Waypoint(7.3333333333333, 5.38888888888889, 0),
    	new Waypoint(8.6666666666667, 0.11111111111111, Pathfinder.d2r(90)),
    	new Waypoint(8.6666666666667, 6.16666666666667, Pathfinder.d2r(-90)),
    }; 
    	
    	
        
    public static Waypoint[] secondaryWaypoints = new Waypoint[] {
		new Waypoint(1, 1.8333333333, Pathfinder.d2r(-45)),
		new Waypoint(1, 3.8333333333, Pathfinder.d2r(45)),
		new Waypoint(3.33333333333, -0.5, 0),
		new Waypoint(3.33333333333, 6.77777777777, 0),
		new Waypoint(5.33333333333, -0.5, 0),
		new Waypoint(5.33333333333, 6.77777777777, 0),
		new Waypoint(6.33333333333, 0.5, 90),
		new Waypoint(6.33333333333, 1.5, -90),		
		new Waypoint(6.33333333333, 3.77777777778, Pathfinder.d2r(90)),
		new Waypoint(6.33333333333, 4.77777777778, Pathfinder.d2r(-90)),
    }; 
    
    public static Waypoint[] extraWaypoints = new Waypoint[] {
    	new Waypoint(4.333333333333, 6.27777777777777777778, 0),
    	new Waypoint(5.6666666666667, 2, Pathfinder.d2r(180)),
    	new Waypoint(5.6666666666667, 2.7, Pathfinder.d2r(180)),
    	new Waypoint(5.6666666666667, 3.4, Pathfinder.d2r(180)),
    	new Waypoint(5.6666666666667, 4.1, Pathfinder.d2r(180)),
		new Waypoint(6.33333333333, 1.2, 90),
		new Waypoint(6.33333333333, 1.9, 90),
		new Waypoint(6.33333333333, 3.6, 90),
		new Waypoint(6.33333333333, 4.3, 90),
    };
    
}
