package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Stabilizers extends Subsystem{
	
	DoubleSolenoid stabilizerDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.stabilizerDeployForward, RobotMap.stabilizerDeployReverse);
	DoubleSolenoid stabilizerEngage = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.stabilizerEngageForward, RobotMap.stabilizerEngageReverse);
	
	public Stabilizers(){		
		super("Stabilizers");
	}

	public boolean getDeployedStatus() {
		return stabilizerDeploy.get() == Value.kForward ? true : false;
	}
	
	public void deployStabliziers() {
		stabilizerDeploy.set(Value.kForward);
	}
	
	public void retractStabilizers() {
		stabilizerDeploy.set(Value.kReverse);
	}
	public boolean getEngagedStatus() {
		return stabilizerEngage.get() == Value.kForward ? true : false;
	}
	
	public void engageStabilizers() {
		stabilizerEngage.set(Value.kForward);
	}
	
	public void disengageStabilizers() {
		stabilizerEngage.set(Value.kReverse);
	}
	
	public void updateSmartdashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putBoolean("Climber", "Stabilizers Deployed", getDeployedStatus());
		Shuffleboard.putBoolean("Climber", "Stabilizers Engaged", getEngagedStatus());
		

		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putBoolean("Stabilizers Deployed", getDeployedStatus());
		SmartDashboard.putBoolean("Stabilizers Engaged", getEngagedStatus());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
