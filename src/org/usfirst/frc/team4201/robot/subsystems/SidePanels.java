package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SidePanels extends Subsystem{
	
	Solenoid rightPanel = new Solenoid(RobotMap.PCMOne, RobotMap.rightSidePanel);
	Solenoid leftPanel = new Solenoid(RobotMap.PCMOne, RobotMap.rightSidePanel);
	
	public SidePanels(){
		
	}
	
	public void deploySidePanels(){
		rightPanel.set(true);
		leftPanel.set(true);
	}
	
	public void retractSidePanels(){
		rightPanel.set(false);
		leftPanel.set(false);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
