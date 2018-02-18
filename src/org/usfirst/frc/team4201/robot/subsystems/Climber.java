package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem{
	
	DoubleSolenoid climberPistons = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.climberForward, RobotMap.climberReverse);
	

	public Climber(){		
		super("Climber");
	}
	public boolean getWingStatus() {
		return climberPistons.get() == Value.kForward ? true : false;
	}
	
	public void deployWings() {
		climberPistons.set(Value.kForward);
	}
	
	public void retractWings() {
		climberPistons.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
