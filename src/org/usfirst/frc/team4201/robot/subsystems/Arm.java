package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem{
	
	public BaseMotorController[] armAndWristMotors = {
		new WPI_TalonSRX(RobotMap.armLeftMotor),
		new WPI_TalonSRX(RobotMap.armRightMotor),
		new WPI_TalonSRX(RobotMap.wristMotor),
	};
	
	public AnalogPotentiometer armPot = new AnalogPotentiometer(RobotMap.armPot);
	public AnalogPotentiometer wristPot = new AnalogPotentiometer(RobotMap.wristPot);
	
	double armSetpoint, wristSetpoint;
	
	//DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmOne, RobotMap.leftArmTwo);
	//DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmOne, RobotMap.rightArmTwo);
	
	public Arm() {
		super("Arm");
		
		for(int i = 0; i < 2; i++){
			armAndWristMotors[i].configPeakOutputForward(1, 0);
			armAndWristMotors[i].configPeakOutputReverse(-1, 0);
			armAndWristMotors[i].setNeutralMode(NeutralMode.Coast);
		}
	}
	/*
	public boolean getArmStatus() {
		return leftArm.get() == Value.kForward ? true : false;
	}
	
	public void deployArm() {
		leftArm.set(Value.kForward);
		rightArm.set(Value.kForward);
	} 
	
	public void retractArm() {
		leftArm.set(Value.kReverse);
		rightArm.set(Value.kReverse);
	}
	*/
		
	public void armUp() {
		armAndWristMotors[0].set(ControlMode.PercentOutput, 0.5);
		armAndWristMotors[1].set(ControlMode.PercentOutput, 0.5);
	}
	
	public void armDown() {
		armAndWristMotors[0].set(ControlMode.PercentOutput, -0.5);
		armAndWristMotors[1].set(ControlMode.PercentOutput, -0.5);
	}
	
	public void wristUp() {
		armAndWristMotors[2].set(ControlMode.PercentOutput, 0.5);
	}
	
	public void wristDown() {
		armAndWristMotors[2].set(ControlMode.PercentOutput, -0.5);
	}
	
	public void updateArmState(){
		
		if(armPot.get() > 33 && armPot.get() < 38 || armPot.get() > 123 && armPot.get() < 128)
			RobotMap.armState = 1;
		else if(armPot.get() > 38 && armPot.get() < 123){
			RobotMap.armState = 2;
		} else
			RobotMap.armState = 0;
			
		switch(state){
			case 0:
				// Do nothing
				break;
			case 1:
				if(wrist)
				break;
		}
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Arm Pot", armPot.get());
		SmartDashboard.putNumber("Wrist Pot", wristPot.get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}
