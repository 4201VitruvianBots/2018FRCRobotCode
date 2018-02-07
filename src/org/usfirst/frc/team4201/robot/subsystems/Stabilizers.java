package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Stabilizers extends Subsystem{
	
	DoubleSolenoid stabilizerHorizontalDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.stabalizerEngageHorizontal, RobotMap.stabalizerReleaseHorizontal);
	DoubleSolenoid stabilizerVerticalDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.stabalizerEngageVertical, RobotMap.stabalizerReleaseVertical);
	
	public Stabilizers(){		
		super("Stabilizers");
	}

	public boolean getHorizontalStatus() {
		return stabilizerHorizontalDeploy.get() == Value.kForward ? true : false;
	}
	
	public void deployHorizontalStabilizers() {
		stabilizerHorizontalDeploy.set(Value.kForward);
	}
	
	public void retractHorizontalStabilizers() {
		stabilizerHorizontalDeploy.set(Value.kReverse);
	}
	public boolean getVerticalStatus() {
		return stabilizerVerticalDeploy.get() == Value.kForward ? true : false;
	}
	
	public void deployVerticalStabilizers() {
		stabilizerVerticalDeploy.set(Value.kForward);
	}
	
	public void retractVerticalStabilizers() {
		stabilizerVerticalDeploy.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
