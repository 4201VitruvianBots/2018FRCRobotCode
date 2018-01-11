/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
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
	public Button[] xBoxButtons = new Button[9];
	public boolean isQuickTurn = true;

	public OI(){
		leftJoystick = new Joystick(0);
		rightJoystick = new Joystick(1);
		xBoxController = new Joystick(2);
		
		for(int i = 0; i < leftButtons.length; i++)
			leftButtons[i] = new JoystickButton(leftJoystick, (i + 1));
		for(int i = 0; i < rightButtons.length; i++)
			rightButtons[i] = new JoystickButton(rightJoystick, (i + 1));
		for(int i = 0; i < xBoxButtons.length; i++)
			xBoxButtons[i] = new JoystickButton(xBoxController, (i + 1));

		leftButtons[0].whenPressed(new ToggleConveyorUptake()); // Flywheel + Conveyor
		leftButtons[3].whenPressed(new SetDriveHighGear());		
		leftButtons[5].whenPressed(new SetDriveLowGear());		 
		leftButtons[4].whenPressed(new ToggleDriveShift());		// [5]
		
		rightButtons[0].whenPressed(new ToggleAutoDriveShift());
		rightButtons[1].whenPressed(new ToggleHopperWall());		
		rightButtons[2].whenPressed(new IntakeUp());
		rightButtons[3].whenPressed(new ToggleCheesyDrive());
		rightButtons[4].whenPressed(new IntakeDown());	

		xBoxButtons[0].whenPressed(new IntakeDown());	
		xBoxButtons[1].whenPressed(new IntakeUp());		
		xBoxButtons[2].whenPressed(new ToggleFlywheel(8320)); // Uptake + Flywheel
		//xBoxButtons[3].whenPressed(new MotorTest(5)); //1: Flywheel, 10: Conveyor, 4: Uptake, 5 : Flywheel + Uptake
		xBoxButtons[5].whenPressed(new ToggleBallIntake());
	}
	
	public double getLeftY(){
		return leftJoystick.getY();
	}
	
	public double getLeftX(){
		return leftJoystick.getX();
	}
	
	public double getRightY(){
		return rightJoystick.getY();
	}
	
	public double getRightX(){
		return rightJoystick.getX();
	}
}
