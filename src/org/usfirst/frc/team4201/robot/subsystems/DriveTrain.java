/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.SetSplitArcadeDrive;
import org.usfirst.frc.team4201.robot.interfaces.CTREPIDSource;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
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
    CTREPIDSource leftDriveEncoder, rightDriveEncoder;
    PIDOutputInterface leftMotorPIDOutput, rightMotorPIDOutput, driveTurnPIDOutput;
    
    double throttleLeft, throttleRight, setpoint;
	
	
	public BaseMotorController[] driveMotors = {
		new WPI_TalonSRX(RobotMap.driveTrainLeftFront),
		new WPI_TalonSRX(RobotMap.driveTrainLeftRear),	// VictorSPX(RobotMap.driveTrainLeftRear),
		new WPI_TalonSRX(RobotMap.driveTrainRightFront),
		new WPI_TalonSRX(RobotMap.driveTrainRightRear)	// VictorSPX(RobotMap.driveTrainRightRear)
	};
	
	//RobotDrive robotDrive = new RobotDrive(driveMotors[0], driveMotors[1], driveMotors[2], driveMotors[3]);
	DifferentialDrive robotDrive = new DifferentialDrive((WPI_TalonSRX)driveMotors[0], (WPI_TalonSRX)driveMotors[2]);
	
	DoubleSolenoid driveTrainShifters = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.driveTrainShifterLeft, RobotMap.driveTrainShifterRight);
	
	public ADXRS450_Gyro spartanGyro;
	
	public Encoder leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB, false, EncodingType.k2X);
	public Encoder rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB, false, EncodingType.k2X);
	
	public DriveTrain(){
		super("Drive Train");
		
		// Set Motor Controller Control Mode
		driveMotors[1].set(ControlMode.Follower, driveMotors[0].getDeviceID());
		driveMotors[3].set(ControlMode.Follower, driveMotors[2].getDeviceID());

		// Set Motor Controller Feedback Device
		driveMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		driveMotors[0].setSensorPhase(true);
		driveMotors[2].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < 4; i++){
			driveMotors[i].configPeakOutputForward(1, 0);
			driveMotors[i].configPeakOutputReverse(-1, 0);
			driveMotors[i].setNeutralMode(NeutralMode.Coast);
		}
		
		// Invert Left Motors
		//driveMotors[0].setInverted(true);
		//driveMotors[1].setInverted(true);
		
		spartanGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		spartanGyro.setName("Gyro");
		spartanGyro.setSubsystem("Drive Train");
		// Initialize PID Controllers
		
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public double getLeftEncoderValue() {
		return leftEncoder.get();
	}
	
	public double getRightEncoderValue() {
		return rightEncoder.get();
	}
	
	public double getAverageEncoderValue() {
		double leftEncoder = getLeftEncoderValue();
		double rightEncoder = getRightEncoderValue();
		double averageEncoderCounts = leftEncoder + rightEncoder;
		averageEncoderCounts = averageEncoderCounts/2;
		return averageEncoderCounts;
	}

	
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
		
		//driveMotors[0].setSelectedSensorPosition(driveMotors[0].getSelectedSensorPosition(0), 0, 0);
		//driveMotors[2].setSelectedSensorPosition(driveMotors[2].getSelectedSensorPosition(0), 0, 0);
		driveMotors[0].setSelectedSensorPosition(0, 0, 0);
		driveMotors[2].setSelectedSensorPosition(0, 0, 0);
	
	}
	
	public void setMotorsToCoast(){
		for(int i = 0; i < driveMotors.length; i++)
			driveMotors[i].setNeutralMode(NeutralMode.Coast);
	}
	
	public void setMotorsToBrake(){
		for(int i = 0; i < driveMotors.length; i++)
			driveMotors[i].setNeutralMode(NeutralMode.Brake);
	}
	
	public void setDriveOutput(double throttle, double angularPower){
		double leftPWM = throttle + angularPower;
		double rightPWM = throttle - angularPower;
		
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
	
	public void PIDDrive(double leftOutput, double rightOutput){
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
		
		//robotDrive.tankDrive(leftPWM, rightPWM);
		robotDrive.tankDrive(leftPWM, rightPWM);
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
		SmartDashboard.putNumber("Front Left Current", driveMotors[0].getOutputCurrent());
		SmartDashboard.putNumber("Rear Left Current", driveMotors[1].getOutputCurrent());
		SmartDashboard.putNumber("Front Right Current", driveMotors[2].getOutputCurrent());
		SmartDashboard.putNumber("Rear Right Current", driveMotors[3].getOutputCurrent());

		SmartDashboard.putNumber("Left Encoder", driveMotors[0].getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder", driveMotors[2].getSelectedSensorPosition(0));
		
		SmartDashboard.putNumber("Left Encoder Count", getLeftEncoderValue());
		SmartDashboard.putNumber("Right Encoder Count", getRightEncoderValue());
		SmartDashboard.putNumber("Average Encoder Count", getAverageEncoderValue());
		SmartDashboard.putNumber("Spartan Gyro", spartanGyro.getAngle());
		SmartDashboard.putBoolean("Cheesy Quick Turn", Robot.oi.isQuickTurn);
		SmartDashboard.putBoolean("Drive Train Shift", getDriveShiftStatus());
	}
	
	public void initializeLiveWindow() {
        leftDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[0]);
        rightDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[2]);
		leftMotorPIDOutput = new PIDOutputInterface();
        rightMotorPIDOutput = new PIDOutputInterface();
        driveTurnPIDOutput = new PIDOutputInterface();
        
        leftMotorPIDController = new PIDController(kP, kI, kD, leftDriveEncoder, leftMotorPIDOutput, period);
        leftMotorPIDController.setName("Left Motor PID");
        leftMotorPIDController.setSubsystem("Drive Train");
        leftMotorPIDController.setAbsoluteTolerance(100);
        leftMotorPIDController.setOutputRange(-0.8, 0.8);
        
        rightMotorPIDController = new PIDController(kP, kI, kD, rightDriveEncoder, rightMotorPIDOutput, period);
        rightMotorPIDController.setName("Right Motor PID");
        rightMotorPIDController.setSubsystem("Drive Train");
        rightMotorPIDController.setAbsoluteTolerance(100);
        rightMotorPIDController.setOutputRange(-0.8, 0.8);
    	
        driveGyroPIDController = new PIDController(kP, kI, kD, spartanGyro, driveTurnPIDOutput, period);
        driveGyroPIDController.setName("Drive Gyro PID");
    	driveGyroPIDController.setSubsystem("Drive Train");
        driveGyroPIDController.setAbsoluteTolerance(2);
        driveGyroPIDController.setOutputRange(-0.2, 0.2);
        
        LiveWindow.add(leftMotorPIDController);
        LiveWindow.add(rightMotorPIDController);
        LiveWindow.add(driveGyroPIDController);
        //LiveWindow.addChild("Drive Train", (WPI_TalonSRX)driveMotors[0]);
        //LiveWindow.addChild("Drive Train", (WPI_TalonSRX)driveMotors[2]);
        //LiveWindow.addChild("Drive Train", spartanGyro);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new SetSplitArcadeDrive());
	}
}
