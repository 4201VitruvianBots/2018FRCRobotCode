package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.AnalogJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controls extends Subsystem{
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	DigitalOutput LEDS[] = {
		new DigitalOutput(RobotMap.redSignal),
		new DigitalOutput(RobotMap.greenSignal),
		new DigitalOutput(RobotMap.blueSignal),
		new DigitalOutput(RobotMap.flashSignal),
	};
	
	public int powerState;
	
	public Controls(){
		super("Controls");
	}
	
	public void updateCurrentState(){
		if(pdp.getTotalCurrent() > 200)
			powerState = 1;
		else powerState = 0;
		
		switch(powerState){
			case 1:
				break;
			case 0:
			default:
				
				break;
		}
	}
	
	public void checkSensorHealth(){
	}
	
	public void setRGBF(int channel) {
		LEDS[channel].set(!LEDS[channel].get());
	}
	
	public void updateSmartDashboard(){
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Total Current Draw", pdp.getTotalCurrent());
		SmartDashboard.putBoolean("Brownout", RobotController.isBrownedOut());
		
	}
	
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
