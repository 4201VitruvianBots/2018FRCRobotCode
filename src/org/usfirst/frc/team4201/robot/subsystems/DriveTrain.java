/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.SetCheesyDrive;
import org.usfirst.frc.team4201.robot.commands.SetSplitArcadeDrive;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	PIDController leftMotorPIDController, rightMotorPIDController, driveGyroPIDController;
	double kP = 0.03;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    double kI = 0;           		// Start with I = P / 100
    double kD = 0;           		// Start with D = P * 10
    double period = 0.01;
    
	public WPI_TalonSRX[] driveMotors = {
		new WPI_TalonSRX(RobotMap.driveTrainLeftFront),
		new WPI_TalonSRX(RobotMap.driveTrainLeftRear),
		new WPI_TalonSRX(RobotMap.driveTrainRightFront),
		new WPI_TalonSRX(RobotMap.driveTrainRightRear)
	};
	
	DifferentialDrive robotDrive = new DifferentialDrive(driveMotors[0], driveMotors[2]);
	
	DoubleSolenoid driveTrainShifters = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.driveTrainShifterForward, RobotMap.driveTrainShifterReverse);
	
	public ADXRS450_Gyro spartanGyro;
	
	public DriveTrain(){
		super("Drive Train");
		
		// Set Motor Controller Control Mode
		driveMotors[1].set(ControlMode.Follower, driveMotors[0].getDeviceID());
		driveMotors[3].set(ControlMode.Follower, driveMotors[2].getDeviceID());

		// Set Motor Controller Feedback Device
		driveMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		driveMotors[2].setSensorPhase(true);
		driveMotors[2].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < 4; i++){
			driveMotors[i].configPeakOutputForward(1, 0);
			driveMotors[i].configPeakOutputReverse(-1, 0);
			driveMotors[i].setInverted(true);
			driveMotors[i].enableVoltageCompensation(true);
			driveMotors[i].configVoltageCompSaturation(12, 0);
			//driveMotors[i].setSafetyEnabled(true);
			//driveMotors[i].configContinuousCurrentLimit(40, 0);
			//driveMotors[i].configPeakCurrentLimit(80, 0);
			//driveMotors[i].configPeakCurrentDuration(100, 0);
		}
		
		try{
			spartanGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

			spartanGyro.setName("Gyro");
			spartanGyro.setSubsystem("Drive Train");
	        LiveWindow.add(spartanGyro);
	        if(spartanGyro == null)
	        	SmartDashboard.putBoolean("Gyro Detected", false);
	        else
	        	SmartDashboard.putBoolean("Gyro Detected", true);
		} catch (Exception e) {
			DriverStation.reportError("4201 Error: Spartan Gyro not detected!", false);
			SmartDashboard.putBoolean("Gyro Detected", false);
		}
		
        robotDrive.setName("Robot Drive");
		robotDrive.setSubsystem("Drive Train");
        LiveWindow.add(robotDrive);

        driveTrainShifters.setName("Shifters");
        driveTrainShifters.setSubsystem("Drive Train");
        LiveWindow.add(driveTrainShifters);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public int getLeftEncoderValue() {
		return driveMotors[0].getSelectedSensorPosition(0);
	}
	
	public int getRightEncoderValue() {
		return driveMotors[2].getSelectedSensorPosition(0);
	}

	public void resetSensors() {
		driveMotors[0].setSelectedSensorPosition(0, 0, 0);
		driveMotors[2].setSelectedSensorPosition(0, 0, 0);
		try {
			spartanGyro.reset();
		} catch(Exception e) {
			
		}
	}
	
	public void setMotorsToBrake() {
		for(int i = 0; i < driveMotors.length; i++)
			driveMotors[i].setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorsToCoast() {
		for(int i = 0; i < driveMotors.length; i++)
			driveMotors[i].setNeutralMode(NeutralMode.Coast);
	}
	
	public void setDriveOutput(double throttle, double angularPower) {
		double leftPWM = throttle + angularPower;
		double rightPWM = throttle - angularPower;
		
		if(rightPWM > 1.0) {
			leftPWM -= rightPWM - 1.0;
			rightPWM = 1.0;
        } else if(rightPWM < -1.0) {
        	leftPWM -= rightPWM + 1.0;
        	rightPWM = -1.0;
        } else if(leftPWM > 1.0) {
        	rightPWM -= leftPWM - 1.0;
        	leftPWM = 1.0;
        } else if(leftPWM < -1.0) {
        	rightPWM -= leftPWM + 1.0;
        	leftPWM = -1.0;
        }

		setDirectDriveOutput(leftPWM, rightPWM);
	}
	
	public void PIDDrive(double leftOutput, double rightOutput) {
		double leftPWM = leftOutput;
		double rightPWM = rightOutput;
		
		if(rightPWM > 1.0){
			leftPWM -= (rightPWM - 1.0);
			rightPWM = 1.0;
        } else if(rightPWM < -1.0){
        	leftPWM += (-rightPWM - 1.0);
        	rightPWM = -1.0;
        } else if(leftPWM > 1.0){
        	rightPWM -= (leftPWM - 1.0);
        	leftPWM = 1.0;
        } else if(leftPWM < -1.0){
        	rightPWM += (-leftPWM - 1.0);
        	leftPWM = -1.0;
        }
		
		robotDrive.tankDrive(leftPWM, rightPWM);
	}
	
	public void setDirectDriveOutput(double leftOutput, double rightOutput) {
		
		robotDrive.tankDrive(leftOutput, rightOutput);
	}
	
	
	
	public void setDirectDriveOutputInverted(double leftOutput, double rightOutput) {
		robotDrive.tankDrive(rightOutput, leftOutput);
	}
	
	public void setDirectDriveScaledOutput(double leftOutput, double rightOutput) {
		double max = Math.max(leftOutput, rightOutput);
		
		if(max > 1) {
			if(leftOutput == max) {
				rightOutput = rightOutput/leftOutput;
				leftOutput = 1;
			} else {
				leftOutput = leftOutput/rightOutput;
				rightOutput = 1;
			}
		}
		
		robotDrive.tankDrive(leftOutput, rightOutput);
	}
	
	public void cheesyDrive(double xSpeed, double zRotation, boolean QuickTurn) {
		robotDrive.curvatureDrive(xSpeed, zRotation, QuickTurn);
	}
	
	public void setTankDrive(double leftSpeed, double rightSpeed){
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public void setArcadeDrive(double speed, double turn) {
		robotDrive.arcadeDrive(speed, turn);
	}
	
	public void setDriveShiftHigh(){
		driveTrainShifters.set(Value.kForward);
	}
	
	public void setDriveShiftLow(){
		driveTrainShifters.set(Value.kReverse);
	}
	
	public boolean getDriveShiftStatus(){
		return driveTrainShifters.get() == Value.kForward ? true : false;
	}
	
	public void updateSmartDashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Drive Train", "Left Encoder Count", getLeftEncoderValue());
		Shuffleboard.putNumber("Drive Train", "Right Encoder Count", getRightEncoderValue());
		
		Shuffleboard.putBoolean("Drive Train", "Cheesy Quick Turn", Robot.oi.isQuickTurn);
		Shuffleboard.putBoolean("Drive Train", "Drive Train Shift", getDriveShiftStatus());

		Shuffleboard.putNumber("Drive Train", "Match Time Test", DriverStation.getInstance().getMatchTime());
		
		Shuffleboard.putNumber("Pathfinder", "Left Encoder Count", getLeftEncoderValue());
		Shuffleboard.putNumber("Pathfinder", "Right Encoder Count", getRightEncoderValue());
		
		// Use SmartDashboard to put only the important stuff for drivers
		SmartDashboard.putBoolean("Drive Train Shifters", getDriveShiftStatus());
		try {
			Shuffleboard.putNumber("Drive Train", "Gyro", spartanGyro.getAngle());
			Shuffleboard.putNumber("Pathfinder", "Gyro", spartanGyro.getAngle());
			//Shuffleboard.putData("Drive Train", "P Gyro", spartanGyro);
			//Shuffleboard.putData("Pathfinder", "DT Gyro", spartanGyro);
			SmartDashboard.putData(spartanGyro);	// Check if this needs to be in this loop?
		} catch(Exception e) {
			
		}
		
		try {
			// This is in a try/catch to avoid crash when teleOpDrive is null before teleOpInit is called
			if(Robot.teleOpDrive.getClass() == SetCheesyDrive.class)
				SmartDashboard.putBoolean("Cheesy Quick Turn", Robot.oi.isQuickTurn);
		} catch(Exception e) {
			
		}
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new SetSplitArcadeDrive());
	}
}
