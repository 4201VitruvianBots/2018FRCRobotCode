package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem{
	
	DoubleSolenoid climberPistons = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.climberForward, RobotMap.climberReverse);
	
	public Climber(){		
		super("Climber");
	}
	public boolean getClimberStatus() {
		return climberPistons.get() == Value.kForward ? true : false;
	}
	
	public void deployClimbers() {
		climberPistons.set(Value.kForward);
	}
	
	public void retractClimbers() {
		climberPistons.set(Value.kReverse);
	}
	
	public void updateSmartDashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putBoolean("Climber", "Climber Deployed", getClimberStatus());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		//SmartDashboard.putBoolean("Climber", getClimberStatus());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
