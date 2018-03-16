package org.usfirst.frc.team4201.robot.interfaces;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class XBoxTrigger extends Button {
	Joystick joystick;
	int axis;
	
	public XBoxTrigger(Joystick joystick, int axis){
		this.joystick = joystick;
		this.axis = axis;
	}
	
	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return joystick.getRawAxis(axis) > 0.15;
	}

}
