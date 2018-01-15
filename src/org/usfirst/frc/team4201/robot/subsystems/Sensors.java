package org.usfirst.frc.team4201.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem {
	AnalogInput rangeFinder;
	
	public Sensors() {
		super("Sensors");
		rangeFinder = new AnalogInput(0);
		
	}
	
	public double getRangeMM(){
		return rangeFinder.getAverageValue() * 5;
	}
	
	public double getRangeIn(){
		return (rangeFinder.getAverageValue() * 5)/ 2.54;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Ultrasonic Rangefinder (mm)", getRangeMM());
		SmartDashboard.putNumber("Ultrasonic Rangefinder (in)", getRangeIn());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
