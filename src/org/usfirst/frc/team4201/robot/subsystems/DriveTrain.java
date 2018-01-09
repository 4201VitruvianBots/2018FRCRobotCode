/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.SplitArcadeDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	
	WPI_TalonSRX[] driveMotors = {
		new WPI_TalonSRX(RobotMap.driveTrainLeftFront),
		new WPI_TalonSRX(RobotMap.driveTrainLeftRear),	// VictorSPX(RobotMap.driveTrainLeftRear),
		new WPI_TalonSRX(RobotMap.driveTrainRightFront),
		new WPI_TalonSRX(RobotMap.driveTrainRightRear)	// VictorSPX(RobotMap.driveTrainRightRear)
	};
	
	//RobotDrive robotDrive = new RobotDrive(driveMotors[0], driveMotors[1], driveMotors[2], driveMotors[3]);
	DifferentialDrive robotDrive = new DifferentialDrive(driveMotors[0], driveMotors[2]);
	DoubleSolenoid driveTrainShifters = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.driveTrainShifterForward, RobotMap.driveTrainShifterReverse);
	
	public DriveTrain(){
		super("Drive Train");
		
		// Set Motor Controller Control Mode
		driveMotors[1].set(ControlMode.Follower, driveMotors[0].getDeviceID());
		driveMotors[3].set(ControlMode.Follower, driveMotors[2].getDeviceID());

		// Set Motor Controller Feedback Device
		driveMotors[0].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		driveMotors[2].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < driveMotors.length; i++){
			driveMotors[i].configPeakOutputForward(1, 0);
			driveMotors[i].configPeakOutputReverse(-1, 0);
			driveMotors[i].setNeutralMode(NeutralMode.Coast);
		}
		
		// Invert Left Motors
		//driveMotors[0].setInverted(true);
		//driveMotors[1].setInverted(true);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
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
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new SplitArcadeDrive());
	}
}
