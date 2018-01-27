package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	
	public BaseMotorController[] armAndWristMotors = {
			new WPI_TalonSRX(RobotMap.armOne),
			new WPI_TalonSRX(RobotMap.armTwo),
			new WPI_TalonSRX(RobotMap.wrist),
		};
	
	//DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmOne, RobotMap.leftArmTwo);
	//DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmOne, RobotMap.rightArmTwo);
	
	public Arm() {
		super("Arm");
		
		for(int i = 0; i < 2; i++){
			armAndWristMotors[i].configPeakOutputForward(1, 0);
			armAndWristMotors[i].configPeakOutputReverse(-1, 0);
			armAndWristMotors[i].setNeutralMode(NeutralMode.Brake);
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
	
	public void armStop() {
		armAndWristMotors[0].set(ControlMode.PercentOutput, 0);
		armAndWristMotors[1].set(ControlMode.PercentOutput, 0);
	}
	
	public void wristUp() {
		armAndWristMotors[2].set(ControlMode.PercentOutput, 0.5);
	}
	
	public void wristDown() {
		armAndWristMotors[2].set(ControlMode.PercentOutput, -0.5);
	}
	
	public void wristStop() {
		armAndWristMotors[2].set(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}
