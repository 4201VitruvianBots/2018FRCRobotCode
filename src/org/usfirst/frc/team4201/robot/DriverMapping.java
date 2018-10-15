package org.usfirst.frc.team4201.robot;

import org.usfirst.frc.team4201.robot.commands.*;

public class DriverMapping { 
	public static String[] driverOptions = {
		"Default",
		"Joy",
		"Miles"
	};
	
	public static String[] operatorOptions = {
		"Melita",
		"Default"
	};
	
	public static void InitDriverMapping() {
		Robot.driverControls.addDefault(driverOptions[0], driverOptions[0]);
		for(int i = 1; i < driverOptions.length; i++)
			Robot.driverControls.addObject(driverOptions[i], driverOptions[i]);

		Robot.operatorControls.addDefault(operatorOptions[0], operatorOptions[0]);
		for(int i = 1; i < operatorOptions.length; i++)
			Robot.operatorControls.addObject(operatorOptions[i], operatorOptions[i]);
	}
	
	public static void DEFAULT_DRIVER() {
		Robot.oi.leftButtons[0].whileHeld(new SetIntakeMotorOutputs(-0.6));					// Left Joystick Trigger: Eject cube
		Robot.oi.leftButtons[0].whenReleased(new SetIntakeMotorOutputs(0));					// Left Joystick Trigger: Eject cube
        //Robot.oi.leftButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A
        //Robot.oi.leftButtons[2].whenPressed(new ToggleIntakePistons());					// Left Left Thumb Button Up: N/A
        //Robot.oi.leftButtons[4].whenPressed(new ToggleIntakePressure());					// Left Left Thumb Button Down: N/A
        //Robot.oi.leftButtons[3].whenPressed();											// Left Right Thumb Button Up: Retract Intake
		Robot.oi.leftButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());			// Left Right Thumb Button Down: Deploy Intake
        //Robot.oi.leftButtons[6].whenPressed(new SetPIDTunerValues());						// Forward Button: N/A
        
		Robot.oi.rightButtons[0].toggleWhenPressed(new ToggleIntakePistons());				// Right Joystick Trigger: Toggle Intake Pistons
        //Robot.oi.rightButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A                     
        Robot.oi.rightButtons[2].whenPressed(new SetDriveShiftersLow());					// Left Left Thumb Button Up:                        
        Robot.oi.rightButtons[4].whenPressed(new SetDriveShiftersHigh());					// Left Left Thumb Button Down:                      
        Robot.oi.rightButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());			// Left Right Thumb Button Down: Deploy Intake
        
        //Robot.oi.rightButtons[3].whenPressed(new EnableClimbMode());						// Left Right Thumb Button Up: N/A   
        //Robot.oi.rightButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());		// Left Right Thumb Button Down: N/A       
        //Robot.oi.rightButtons[6].whenPressed(new SetPIDTunerValues());				    // Forward Button: Broken                                    
	}
	
	public static void JOY_DRIVER() {
		Robot.oi.leftButtons[0].whileHeld(new SetIntakeMotorOutputs(-0.6));					// Left Joystick Trigger: Eject cube
		Robot.oi.leftButtons[0].whenReleased(new SetIntakeMotorOutputs(0));					// Left Joystick Trigger: Eject cube
        //Robot.oi.leftButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A
        //Robot.oi.leftButtons[2].whenPressed(new ToggleIntakePistons());					// Left Left Thumb Button Up: N/A
        //Robot.oi.leftButtons[4].whenPressed(new ToggleIntakePressure());					// Left Left Thumb Button Down: N/A
        //Robot.oi.leftButtons[3].whenPressed();											// Left Right Thumb Button Up: Retract Intake
		Robot.oi.leftButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());			// Left Right Thumb Button Down: Deploy Intake
        //Robot.oi.leftButtons[6].whenPressed(new SetPIDTunerValues());						// Forward Button: N/A
        
		Robot.oi.rightButtons[0].toggleWhenPressed(new ToggleIntakePistons());				// Right Joystick Trigger: Toggle Intake Pistons
        //Robot.oi.rightButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A                     
        Robot.oi.rightButtons[2].whenPressed(new SetDriveShiftersLow());					// Left Left Thumb Button Up:                        
        Robot.oi.rightButtons[4].whenPressed(new SetDriveShiftersHigh());					// Left Left Thumb Button Down:                      
        //Robot.oi.rightButtons[3].whenPressed(new EnableClimbMode());						// Left Right Thumb Button Up: N/A   
        //Robot.oi.rightButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());		// Left Right Thumb Button Down: N/A       
        //Robot.oi.rightButtons[6].whenPressed(new SetPIDTunerValues());				    // Forward Button: Broken                                    
	}
	
	public static void MILES_DRIVER() {
		Robot.oi.leftButtons[0].whileHeld(new SetIntakeMotorOutputs(-0.6));					// Left Joystick Trigger: Eject cube
		Robot.oi.leftButtons[0].whenReleased(new SetIntakeMotorOutputs(0));					// Left Joystick Trigger: Eject cube
        //Robot.oi.leftButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A
        //Robot.oi.leftButtons[2].whenPressed(new ToggleIntakePistons());					// Left Left Thumb Button Up: N/A
        //Robot.oi.leftButtons[4].whenPressed(new ToggleIntakePressure());					// Left Left Thumb Button Down: N/A
        //Robot.oi.leftButtons[3].whenPressed();											// Left Right Thumb Button Up: Retract Intake
		Robot.oi.leftButtons[3].whenPressed(new ToggleCubeIntakeWithRetraction());			// Left Right Thumb Button Down: Deploy Intake
        //Robot.oi.leftButtons[6].whenPressed(new SetPIDTunerValues());						// Forward Button: N/A
        
		Robot.oi.rightButtons[0].toggleWhenPressed(new ToggleIntakePistons());				// Right Joystick Trigger: Toggle Intake Pistons
        //Robot.oi.rightButtons[1].whenPressed(new ToggleElevatorShifters());				// Left Center Thumb Button: N/A                     
        Robot.oi.rightButtons[2].whenPressed(new SetDriveShiftersLow());					// Left Left Thumb Button Up:                        
        Robot.oi.rightButtons[4].whenPressed(new SetDriveShiftersHigh());					// Left Left Thumb Button Down:                      
        //Robot.oi.rightButtons[3].whenPressed(new EnableClimbMode());						// Left Right Thumb Button Up: N/A   
        //Robot.oi.rightButtons[5].whenPressed(new ToggleCubeIntakeWithRetraction());		// Left Right Thumb Button Down: N/A       
        //Robot.oi.rightButtons[6].whenPressed(new SetPIDTunerValues());				    // Forward Button: Broken                                    
	}
	
	public static void DEFAULT_OPERATOR() {
		//Robot.oi.xBoxButtons[2].whenPressed(new SetElevatorShiftersLow());		
		//Robot.oi.xBoxButtons[3].whenPressed(new SetElevatorShiftersHigh());			
        
		Robot.oi.xBoxButtons[6].whenPressed(new KillElevator());							// Select: Kill elevator PIDController (Check button assignment)
		Robot.oi.xBoxButtons[7].whenPressed(new KillAll());									// Start: Kill all PIDControllers (Check button assignment)
        
        
		//Robot.oi.xBoxButtons[4].whenPressed(new SetArmElevatorHome());						// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		//Robot.oi.xBoxButtons[4].whenPressed(new SetIntakePistonsClose());					// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		Robot.oi.xBoxButtons[4].whileHeld(new SetIntakeMotorOutputs(0.75));					// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		Robot.oi.xBoxButtons[4].whenReleased(new SetIntakeMotorOutputs(0));					// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		

		if(RobotMap.WristState != 0)
			Robot.oi.setWristManualMode();
		else {
			// Must be xBox controller mapping
			UpdateWristSetpoint.WRIST_FORWARD = 0;
			UpdateWristSetpoint.WRIST_BACKWARDS = 1;
		}
		
		
        //Robot.oi.xBoxButtons[4].whenReleased(new SetIntakeMotorOutputs(0));
        //Robot.oi.xBoxLeftTrigger.whileHeld(new SetIntakeMotorOutputs(-0.5));						// Left Trigger: Set Wrist/Arm/Elevator to reverse Scale Parallel
        //Robot.oi.xBoxLeftTrigger.whenReleased(new SetIntakeMotorOutputs(0));
        
        //Robot.oi.xBoxButtons[10].whenPressed(new KillAll());										// L3: Toggle Left Stabilizers
        //Robot.oi.xBoxButtons[11].whenPressed(new KillAll());										// R3: Toggle Right Stabilizers
	}
	
	public static void MELITA_OPERATOR() {
		//Robot.oi.xBoxButtons[2].whenPressed(new SetElevatorShiftersLow());		
		//Robot.oi.xBoxButtons[3].whenPressed(new SetElevatorShiftersHigh());			
		Robot.oi.xBoxButtons[6].whenPressed(new KillElevator());							// Select: Kill elevator PIDController (Check button assignment)
		Robot.oi.xBoxButtons[7].whenPressed(new KillAll());									// Start: Kill all PIDControllers (Check button assignment)
        
		//Robot.oi.xBoxButtons[4].whenPressed(new SetArmElevatorHome());						// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		Robot.oi.xBoxButtons[4].whileHeld(new SetIntakeMotorOutputs(0.75));					// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position
		Robot.oi.xBoxButtons[4].whenReleased(new SetIntakeMotorOutputs(0));					// Left Button: Set Wrist/Arm/Elevator to reverse Scale Shoot Position

		if(RobotMap.WristState != 0)
			Robot.oi.setWristManualMode();
		else {
			// Must be xBox controller mapping
			UpdateWristSetpoint.WRIST_FORWARD = 1;
			UpdateWristSetpoint.WRIST_BACKWARDS = 0;
		}
        //Robot.oi.xBoxButtons[4].whenReleased(new SetIntakeMotorOutputs(0));
        //Robot.oi.xBoxLeftTrigger.whileHeld(new SetIntakeMotorOutputs(-0.5));						// Left Trigger: Set Wrist/Arm/Elevator to reverse Scale Parallel
        //Robot.oi.xBoxLeftTrigger.whenReleased(new SetIntakeMotorOutputs(0));
        
        //Robot.oi.xBoxButtons[10].whenPressed(new KillAll());										// L3: Toggle Left Stabilizers
        //Robot.oi.xBoxButtons[11].whenPressed(new KillAll());										// R3: Toggle Right Stabilizers
	}
}
