/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// USB ADDRESSES
	public static final int leftJoystick = 0;
	public static final int rightJoystick = 1;
	public static final int xBoxController = 2;
	
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
	
	public static boolean testBoolean = false;
	// Electrical Modules
	public static final int PDP = 0;
	public static final int VRMOne = 1;
	
	// Pneumatic Modules & their devices
	public static final int PCMOne = 11;
		public static final int testShifters1a = 0;
		public static final int testShifters1b = 1;
		
		public static final int testShifters2a = 2;
		public static final int testShifters2b = 3;
		
		public static final int testShifters3a = 4;
		public static final int testShifters3b = 5;
	
	// Motor Controllers
	public static final int driveTrainLeftFront = 20;
	public static final int driveTrainLeftRear = 21;
	public static final int driveTrainRightFront = 23;
	public static final int driveTrainRightRear = 22;
	public static final int intakeLeft = 24;
	public static final int intakeRight = 25;
	
}
