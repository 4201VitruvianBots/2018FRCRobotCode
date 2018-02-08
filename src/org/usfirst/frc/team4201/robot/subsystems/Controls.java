package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.AnalogJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controls extends Subsystem{
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	/*
	DigitalOutput LEDS[] = {
		new DigitalOutput(0),
		new DigitalOutput(1),
		new DigitalOutput(2),
		new DigitalOutput(3),
	};
	*/
	int powerState;
	public Controls(){
		super("Controls");
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Total Current Draw", pdp.getTotalCurrent());
		SmartDashboard.putBoolean("Brownout", RobotController.isBrownedOut());
		int test2 = AnalogJNI.initializeAnalogInputPort(RobotMap.wristPot);
		int test = AnalogJNI.initializeAnalogInputPort(RobotMap.wristPot);
		SmartDashboard.putNumber("Wrist Pot Status", test);
		SmartDashboard.putNumber("Wrist Pot Status2", test2);
		
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
		/*
		if(!LEDS[channel].get())
			LEDS[channel].enablePWM(.5);
		else
			LEDS[channel].disablePWM();
		*/
		//LEDS[channel].set(!LEDS[channel].get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
