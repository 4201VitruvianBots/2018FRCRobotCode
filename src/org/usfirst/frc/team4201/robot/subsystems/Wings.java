package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wings extends Subsystem{
	
	DoubleSolenoid platformDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.wingsForward, RobotMap.wingsReverse);
	

	public Wings(){		
		super("Wings");
	}
	public boolean getWingStatus() {
		return platformDeploy.get() == Value.kForward ? true : false;
	}
	
	public void deployWings() {
		platformDeploy.set(Value.kForward);
	}
	
	public void retractWings() {
		platformDeploy.set(Value.kReverse);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
