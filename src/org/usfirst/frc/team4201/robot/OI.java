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
	public boolean complexControls = false;
	
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
		
		
        leftButtons[0].whenPressed(new ToggleDriveShifters());							// Left Joystick Trigger: Toggle Driver Shifters
        leftButtons[1].whenPressed(new ToggleLEDs(7));
        leftButtons[2].whenPressed(new ToggleLEDs(8));
        leftButtons[3].whenPressed(new ToggleLEDs(9));

        rightButtons[1].whenPressed(new ToggleElevatorShifters());						// This is mostly a test command atm. In reality, this will be assigned ot a different button and used as ToggleElevatorClimbMode()
        rightButtons[7].whenPressed(new SetPIDTunerValues());
        //rightButtons[1].whenPressed(new RetractIntakePistons());
        //rightButtons[3].whileHeld(new ArmDown());
        //rightButtons[3].whileHeld(new IntakeMotorsRightReverse());
        //rightButtons[5].whileHeld(new ArmUp());		
		//rightButtons[5].whenPressed(new ToggleCheesyDrive());
        
        xBoxButtons[0].toggleWhenPressed(new SetIntakeMotorOutputs(0.75));				// A Button: Set Intake to Intake
        xBoxButtons[1].toggleWhenPressed(new SetIntakeMotorOutputs(-0.75));				// B Button: Set Intake to Outtake
        //xBoxButtons[2].whileHeld(new SetWristArmElevatorSetpoints(0, -40, 0));		// X Button: Set Wrist/Arm/Elevator to Intake Position
        xBoxButtons[3].whenPressed(new ToggleIntakePistons());							// Y Button: Toggle Intake Motors
        //xBoxButtons[8].whenPressed(new KillAll());									// Select: Kill all PIDControllers (Check button assignment)
        
        xBoxButtons[5].whileHeld(new SetWristDeltaSetpoint(1));							// Right Button: Adjust wrist up
        xBoxButtons[5].whenReleased(new SetWristDeltaSetpoint(0));						// Right Button: Disable (This is needed due to how whileHeld() functions)
        xBoxRightTrigger.whileHeld(new SetWristDeltaSetpoint(-1));						// Right Trigger: Adjust wrist down
        xBoxRightTrigger.whenReleased(new SetWristDeltaSetpoint(0));					// Right Trigger: Disable (This is needed due to how whileHeld() functions)
        //xBoxButtons[4].whileHeld(new SetWristArmElevatorSetpoints(0, -40, 0));		// Left Trigger: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
        //xBoxLeftTrigger.whileHeld(new SetWristArmElevatorSetpoints(0, -40, 0));		// Left Trigger: Set Wrist/Arm/Elevator to reverse Scale Parallel
        
        // xBoxLeftJoystickY: Adjust Arm angle up/down
        // xBoxRightJoystickY: Adjust Elevator height up/down 
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
	
	public void checkDriverInputs(){
		
		// Read the xBox Controller D-Pad and use that to set the Wrist/Arm/Elevator positions
		if(xBoxController.getPOV(0) == 0) // 0 degrees, Up Button: Set Wrist/Arm/Elevator to Forward Scale High Position
			new SetWristArmElevatorSetpoints(-90, 0, 0);	// -90,
		else if (xBoxController.getPOV(0) == 90) // 90 degrees, Right Button: Set Wrist/Arm/Elevator to Forward Scale Neutral Position
			new SetWristArmElevatorSetpoints(-90, 0, 0);	// -90,
		else if (xBoxController.getPOV(0) == 180) // 180 degrees, Down Button: Set Wrist/Arm/Elevator to Forward Scale Low Position
			new SetWristArmElevatorSetpoints(-90, 0, 0);	// -90, 
		else if (xBoxController.getPOV(0) == 270) // 270 degrees, Left Button: Set Wrist/Arm/Elevator to Switch Position
			new SetWristArmElevatorSetpoints(0, 0, 0);	// 0, -40, 0
		
		// Check if two of the driver joysticks are pressed to enable climb mode. This is done to avoid accidental deployment mid-match.
		if(leftButtons[6].get() && rightButtons[6].get())
			new ToggleElevatorClimbMode();
	}
}

