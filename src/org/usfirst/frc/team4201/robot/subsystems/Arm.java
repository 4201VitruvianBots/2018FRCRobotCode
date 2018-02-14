package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	
	DoubleSolenoid fourBar = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.fourBarForward, RobotMap.fourBarReverse);
	
	public Arm() {
		super("Arm");
	}
	
	public boolean getArmStatus() {
		return fourBar.get() == Value.kForward ? true : false;
	}
	
	public void deployArm() {
		fourBar.set(Value.kForward);
	} 
	
	public void retractArm() {
		fourBar.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
