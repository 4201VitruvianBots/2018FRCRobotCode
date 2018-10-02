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
	// USB Devices
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
	
	public static boolean manualShiftOverride = true;
	public static boolean cheesyDriveBrakeMode = true;
	
	public static final int driveTrainMotorLeftFront = 20;
	public static final int driveTrainMotorLeftRear = 21;
	public static final int driveTrainMotorRightFront = 22;
	public static final int driveTrainMotorRightRear = 23;
	
	public static final int gearIntakeMotor = 24;
	public static final int ballIntakeMotor = 26;	
	
	public static final int conveyorMotor = 28;
	public static final int shooterUptake = 30;	// Master to CANTalon 4?
	
	public static final int flywheelMaster = 32; // This is controlling the uptake
	public static final int flywheelSlave = 33; // Following CANTalon 1
	
	
	public static final int PCMOne = 11;
		public static final int driveTrainShifterForward = 1;
		public static final int driveTrainShifterReverse = 0;	
		public static final int gearClampPistonForward = 4;
		public static final int gearClampPistonReverse = 5;
		public static final int gearIntakePistonForward = 3;
		public static final int gearIntakePistonReverse = 2;
		public static final int hopperWallForward = 7;
		public static final int hopperWallReverse = 6;
}
