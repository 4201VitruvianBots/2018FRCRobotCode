package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SidePanels extends Subsystem {
	
	DoubleSolenoid sidePanels = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftSidePanel, RobotMap.rightSidePanel);
	
	public SidePanels(){
		super("SidePanels");
	}
	
	public boolean getIntakePistonStatus() {
		return sidePanels.get() == Value.kForward ? true : false;
	}
	
	public void deploySidePanels(){
		sidePanels.set(Value.kForward);
	}
	
	public void retractSidePanels(){
		sidePanels.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stubs
		
	}
}
