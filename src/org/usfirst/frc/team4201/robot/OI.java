/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.interfaces.XBoxTrigger;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	
	public Joystick leftJoystick, rightJoystick, xBoxController, testController;
	public Button[] leftButtons = new Button[7];
	public Button[] rightButtons = new Button[7];
	public Button[] xBoxButtons = new Button[10];
	public Button[] testButtons = new Button[10];
	public Button xBoxLeftTrigger, xBoxRightTrigger, testLeftTrigger, testRightTrigger;
	public boolean isQuickTurn = true;
	
	public static int driverControlSet = 0;
	public static int operatorControlSet = 0;
	
	public OI(){
		leftJoystick = new Joystick(RobotMap.leftJoystick);
		rightJoystick = new Joystick(RobotMap.rightJoystick);
		xBoxController = new Joystick(RobotMap.xBoxController);
		
		initializeButtons();									
		//DriverMapping.DEFAULT_DRIVER();
		//DriverMapping.DEFAULT_OPERATOR();

        //xBoxButtons[4].whenReleased(new SetIntakeMotorOutputs(0));
        //xBoxLeftTrigger.whileHeld(new SetIntakeMotorOutputs(-0.5));				// Left Trigger: Set Wrist/Arm/Elevator to reverse Scale Parallel
        //xBoxLeftTrigger.whenReleased(new SetIntakeMotorOutputs(0));
        
        //xBoxButtons[10].whenPressed(new KillAll());								// L3: Toggle Left Stabilizers
        //xBoxButtons[11].whenPressed(new KillAll());								// R3: Toggle Right Stabilizers  
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
	
	public double getXBoxLeftTriggerValue(){
		return xBoxController.getRawAxis(RobotMap.leftTrigger);
	}
	
	public double getXBoxRightTriggerValue(){
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
	
	public void enableXBoxLeftRumbleTimed() {
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
	
	public void checkDriverInputs(){
		if(!UpdateWristSetpoint.intaking) {
			// Read the xBox Controller D-Pad and use that to set the Wrist/Arm/Elevator positions
			if(xBoxController.getPOV() == 0) 			// 0 degrees, Up Button: Set Wrist/Arm/Elevator to Forward Scale Super High Position
				Scheduler.getInstance().add(new SetArmElevatorSetpoints(55, 24.5));	// (55, 25)
			else if (xBoxController.getPOV() == 90) 	// 90 degrees, Right Button: Set Wrist/Arm/Elevator to Forward Scale High Position
				Scheduler.getInstance().add(new SetArmElevatorSetpoints(55, 12));	// (55, 12)
			else if (xBoxController.getPOV() == 180) 	// 180 degrees, Down Button: Set  Wrist/Arm/Elevator to Forward Scale Low Position
				Scheduler.getInstance().add(new SetArmElevatorSetpoints(55, 5));	// (55, 5)
			else if (xBoxController.getPOV() == 270) 	// 270 degrees, Left Button: Set Wrist/Arm/Elevator to Switch Position
				System.out.println("Disabled");
				//Scheduler.getInstance().add(new SetArmElevatorSetpoints(-56, 24.5));	// (-56, 25)
		} else{
			if(xBoxController.getPOV() == 0){
				if(ToggleCubeIntakeWithRetraction.intakeState < 2)
					ToggleCubeIntakeWithRetraction.intakeState++;
			} else if(xBoxController.getPOV() == 180) {
				if(ToggleCubeIntakeWithRetraction.intakeState > 0)
					ToggleCubeIntakeWithRetraction.intakeState--;
			}
		}
		// Check if two of the driver joysticks are pressed to enable climb mode. This is done to avoid accidental deployment mid-match.
		//if(leftButtons[6].get() && rightButtons[6].get())
		//	new ToggleElevatorClimbMode();
	}
	
	public void setWristManualMode(){
        xBoxButtons[5].whileHeld(new SetWristDeltaSetpoint(5));					// Right Button: Adjust wrist up
        xBoxButtons[5].whenReleased(new SetWristDeltaSetpoint(0));				// Right Button: Disable (This is needed due to how whileHeld() functions)
        xBoxRightTrigger.whileHeld(new SetWristDeltaSetpoint(-5));				// Right Trigger: Adjust wrist down
        xBoxRightTrigger.whenReleased(new SetWristDeltaSetpoint(0));			// Right Trigger: Disable (This is needed due to how whileHeld() functions)
	}
	
	public void initializeDriverOperatorControls() {
		String driverOption = Robot.driverControls.getSelected();
		String operatorOption = Robot.operatorControls.getSelected();
		
		if(driverOption == null)
			driverOption = "Default";
		if(operatorOption == null)
			operatorOption = "Default";

		System.out.println("Driver: " + driverOption);
		System.out.println("Operator: " + operatorOption);
		
		switch(driverOption) {
			case "Joy":
				DriverMapping.JOY_DRIVER();
				break;
			case "MILES":
				DriverMapping.MILES_DRIVER();
				break;
			default:
			case "Default":
				DriverMapping.DEFAULT_DRIVER();
				break;
		}
		
		switch(operatorOption) {
			case "Melita":
				DriverMapping.MELITA_OPERATOR();
				break;
			default:
			case "Default":
				DriverMapping.DEFAULT_OPERATOR();
				break;
		}	
	};
	
	public void initializeButtons() {
		leftButtons = null;
		rightButtons = null;
		xBoxButtons = null;
		leftButtons = new Button[7];
		rightButtons = new Button[7];
		xBoxButtons = new Button[10];
		
		for(int i = 0; i < leftButtons.length; i++) {
			leftButtons[i] = null;
			leftButtons[i] = new JoystickButton(leftJoystick, (i + 1));
		}
		for(int i = 0; i < rightButtons.length; i++) {
			rightButtons[i] = null;
			rightButtons[i] = new JoystickButton(rightJoystick, (i + 1));
		}
		for(int i = 0; i < xBoxButtons.length; i++) {
			xBoxButtons[i] = null;
			xBoxButtons[i] = new JoystickButton(xBoxController, (i + 1));
		}
		xBoxLeftTrigger = new XBoxTrigger(xBoxController, RobotMap.leftTrigger);
		xBoxRightTrigger = new XBoxTrigger(xBoxController, RobotMap.rightTrigger);
	}
}

