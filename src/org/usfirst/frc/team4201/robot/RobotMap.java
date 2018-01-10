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
	
	public static final int driveTrainMotorLeftFront = 8;
	public static final int driveTrainMotorLeftRear = 9;
	public static final int driveTrainMotorRightFront = 6;
	public static final int driveTrainMotorRightRear = 7;
	
	public static final int gearIntakeMotor = 0;
	public static final int ballIntakeMotor = 11;	
	
	public static final int conveyorMotor = 10;
	public static final int shooterUptake = 4;	// Master to CANTalon 4?
	
	public static final int flywheelMaster = 5; // This is controlling the uptake
	public static final int flywheelSlave = 1; // Following CANTalon 1
	
	
	public static final int PCMOne = 21;
	
	// Double Solenoids (?)
	public static final int driveTrainShifterForward = 1;
	public static final int driveTrainShifterReverse = 0;
	
	public static final int gearClampPistionForward = 4;
	public static final int gearClampPistionReverse = 5;
	
	public static final int gearIntakePistonForward = 3;
	public static final int gearIntakePistonReverse = 2;
	
	public static final int hopperWallForward = 7;
	public static final int hopperWallReverse = 6;
}
