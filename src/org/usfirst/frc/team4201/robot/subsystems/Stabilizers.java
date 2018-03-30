package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Stabilizers extends Subsystem{
	
	DoubleSolenoid leftStabilizers;// = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.leftStabilizerForward, RobotMap.leftStabilizerReverse);
	DoubleSolenoid rightStabilizers;// = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightStabilizerForward, RobotMap.rightStabilizerReverse);
	
	public Stabilizers(){		
		super("Stabilizers");
	}

	public boolean getLeftStabilizerStatus() {
		return leftStabilizers.get() == Value.kForward ? true : false;
	}
	
	public void deployLeftStabilizers() {
		leftStabilizers.set(Value.kForward);
	}
	
	public void retractLeftStabilizers() {
		leftStabilizers.set(Value.kReverse);
	}
	public boolean getRightStabilizerStatus() {
		return rightStabilizers.get() == Value.kForward ? true : false;
	}
	
	public void deployRightStabilizers() {
		rightStabilizers.set(Value.kForward);
	}
	
	public void retractRightStabilizers() {
		rightStabilizers.set(Value.kReverse);
	}
	
	public void updateSmartdashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putBoolean("Climber", "Left Stabilizers", getLeftStabilizerStatus());
		Shuffleboard.putBoolean("Climber", "Right Stabilizers", getRightStabilizerStatus());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putBoolean("Left Stabilizers", getLeftStabilizerStatus());        
		SmartDashboard.putBoolean("Right Stabilizers", getRightStabilizerStatus());      
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
