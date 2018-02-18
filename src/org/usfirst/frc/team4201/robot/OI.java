/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.interfaces.XBoxTrigger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	
	public Joystick leftJoystick, rightJoystick, xBoxController;
	public Button[] leftButtons = new Button[7];
	public Button[] rightButtons = new Button[7];
	public Button[] xBoxButtons = new Button[10];
	public Button xBoxLeftTrigger;
	public Button xBoxRightTrigger;
	public boolean isQuickTurn = true;

	public OI(){
		leftJoystick = new Joystick(RobotMap.leftJoystick);
		rightJoystick = new Joystick(RobotMap.rightJoystick);
		xBoxController = new Joystick(RobotMap.xBoxController);
		
		for(int i = 0; i < leftButtons.length; i++)
			leftButtons[i] = new JoystickButton(leftJoystick, (i + 1));
		for(int i = 0; i < rightButtons.length; i++)
			rightButtons[i] = new JoystickButton(rightJoystick, (i + 1));
		for(int i = 0; i < xBoxButtons.length; i++)
			xBoxButtons[i] = new JoystickButton(xBoxController, (i + 1));
		
		xBoxLeftTrigger = new XBoxTrigger(xBoxController, RobotMap.leftTrigger);
		xBoxRightTrigger = new XBoxTrigger(xBoxController, RobotMap.rightTrigger);
		
		
        leftButtons[0].whenPressed(new ToggleDriveShifters());
        leftButtons[1].whenPressed(new ToggleLEDs(0));
        leftButtons[2].whenPressed(new ToggleLEDs(1));
        leftButtons[3].whenPressed(new ToggleLEDs(2));
        leftButtons[4].whenPressed(new ToggleLEDs(3));

        rightButtons[0].whenPressed(new SetPIDTunerSetpoint());
        //rightButtons[1].whenPressed(new RetractIntakePistons());
        //rightButtons[3].whileHeld(new ArmDown());
        //rightButtons[3].whileHeld(new IntakeMotorsRightReverse());
        //rightButtons[5].whileHeld(new ArmUp());		
		//rightButtons[5].whenPressed(new ToggleCheesyDrive());
        
        xBoxButtons[0].toggleWhenPressed(new SetIntakeMotorOutputs(0.75));			// A Button: Set Position Feed
        xBoxButtons[1].toggleWhenPressed(new SetIntakeMotorOutputs(-0.75));			// B Button: Set Position Angled
        xBoxButtons[3].whenPressed(new ToggleIntakePistons());					// B Button: Set Position Angled
        //xBoxButtons[3].whenPressed(command);							// Y Button: Set Position Perpendicular
        //xBoxButtons[4].whileHeld(new SetArmDeltaSetpoint(1));			// Left Button: Adjust arm up
        //xBoxLeftTrigger.whileHeld(new SetArmDeltaSetpoint(-1));		// Left Trigger: Adjust arm down
        xBoxButtons[5].whileActive(new SetWristDeltaSetpoint(1));		// Right Button: Adjust wrist up
        xBoxRightTrigger.whileActive(new SetWristDeltaSetpoint(-1));	// Right Trigger: Adjust wrist down
        // xBoxLeftJoystickY: Adjust Arm angle up/down
        // xBoxRightJoystickY: Adjust Elevator height up/down 
        
        // Test Mode Button Commands. WARNING: EXTREMELY DANGEROUS
        if(DriverStation.getInstance().isTest()) {
        }
	}
	
	public double getLeftY(){
		return -leftJoystick.getY();
	}
	
	public double getLeftX(){
		return leftJoystick.getX();
	}
	
	public double getRightY(){
		return -rightJoystick.getY();
	}
	
	public double getRightX(){
		return rightJoystick.getX();
	}
	
	public double getXBoxLeftTrigger(){
		return xBoxController.getRawAxis(RobotMap.leftTrigger);
	}
	
	public double getXBoxRightTrigger(){
		return xBoxController.getRawAxis(RobotMap.rightTrigger);
	}
	
	public void enableXBoxLeftRumble() {
		xBoxController.setRumble(RumbleType.kLeftRumble, 0.8);
	}
	
	public void disableXBoxLeftRumble() {
		xBoxController.setRumble(RumbleType.kLeftRumble, 0);
	}
	
	public void enableXBoxRightRumble() {
		xBoxController.setRumble(RumbleType.kRightRumble, 0.8);
	}
	
	public void disableXBoxRightRumble() {
		xBoxController.setRumble(RumbleType.kRightRumble, 0);
	}
	
	public void enableXBoxLeftRumbleTimed(){
		Thread t = new Thread(() -> {
			Timer stopwatch = new Timer();
			enableXBoxLeftRumble();
			stopwatch.start();
			while(stopwatch.get() < 0.05){
				
			}
			disableXBoxLeftRumble();
		});
		t.start();
	}
	
	public void enableXBoxRightRumbleTimed(){
		Thread t = new Thread(() -> {
			Timer stopwatch = new Timer();
			enableXBoxRightRumble();
			stopwatch.start();
			while(stopwatch.get() < 0.05){
				
			}
			disableXBoxRightRumble();
		});
		t.start();
	}
}

