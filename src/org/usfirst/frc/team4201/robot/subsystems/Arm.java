package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	
	DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmForward, RobotMap.leftArmReverse);
	DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmForward, RobotMap.rightArmReverse);
	
	public Arm() {
		super("Arm");
	}
	
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
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
