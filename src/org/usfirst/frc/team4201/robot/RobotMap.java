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

//5ft = 6971.  116 encoder counts in an inch. (S4T-360-250)


public class RobotMap {
	// Global Variables
	public static int WristState = 0;
	public static int ArmState = 0;
	public static int ElevatorState = 0;
	public static boolean testMode = true;
	
	public static int wristRetractedAngle = 120;
	
	
	// USB ADDRESSES
	public static final int leftJoystick = 0;
	public static final int rightJoystick = 1;
	public static final int xBoxController = 2;
	public static final int testController = 3;
	
	// Joystick Constants
	public static final int leftTrigger = 2;
	public static final int rightTrigger = 3;
	
	// DIO Ports
	public static final int elevatorLowerLimitSwitch = 0;
	public static final int elevatorUpperLimitSwitch = 1;
	public static final int intakeBumpSwitch = 3;
	
	public static final int redSignal = 7;
	public static final int greenSignal = 8;
	public static final int blueSignal = 9;
	
	// Analog Sensors
	public static final int wristPot = 0;
	public static final int armPot = 1;
	// Port 2 on comp bot seems to be broken (affects other sensors if values are passed through it)
	public static final int elevatorLinearPot = 3;
	
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
		public static final int elevatorShifterForward = 2;
		public static final int elevatorShifterReverse = 3;
		public static final int intakePressureForward = 4;
		public static final int intakePressureReverse = 5;
		public static final int climberForward = 6;
		public static final int climberReverse = 7;
				
	public static final int PCMTwo = 12;
		public static final int intakeForward = 0;
		public static final int intakeReverse = 1;
		//public static final int leftStabilizerForward = 0;
		//public static final int leftStabilizerReverse = 1;
		//public static final int rightStabilizerForward = 2;
		//public static final int rightStabilizerReverse = 3;
		public static final int diskBrakeForward = 4;
		public static final int diskBrakeReverse = 5;
		public static final int wingsForward = 6;
		public static final int wingsReverse = 7;
		
	// Motor Controllers
	public static final int driveTrainLeftFront = 20;
	public static final int driveTrainLeftRear = 21;
	public static final int driveTrainRightFront = 22;
	public static final int driveTrainRightRear = 23;
	public static final int elevatorA = 24;
	public static final int elevatorB = 25;
	public static final int intakeLeft = 26;
	
	public static final int intakeRight = 27;
	public static final int armMotor = 28;
	public static final int armMotorB = 29;
	public static final int wristMotor = 30;
}
