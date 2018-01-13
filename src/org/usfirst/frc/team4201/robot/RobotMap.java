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
		
	
	// Motor Controllers
	public static final int driveTrainLeftFront = 20;
	public static final int driveTrainLeftRear = 21;
	public static final int driveTrainRightFront = 22;
	public static final int driveTrainRightRear = 23;
	public static final int intakeLeft = 24;
	public static final int intakeRight = 25;
	public static final int elevatorA = 26;
	public static final int elevatorB = 27;
	public static final int fourBar = 29;
	
	// Constant Integers
	public static final int kDistance = 2;
	
}
