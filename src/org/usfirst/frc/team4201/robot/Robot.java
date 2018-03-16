/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;
import org.usfirst.frc.team4201.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static DriveTrain driveTrain = new DriveTrain();
	public static Elevator elevator = new Elevator();
	public static Arm arm = new Arm();
	public static Wrist wrist = new Wrist();
	public static Intake intake = new Intake();
	public static Climber climber = new Climber();
	//public static Wings wings = new Wings();
	//public static Stabilizers stabilizers = new Stabilizers();
	public static Controls controls = new Controls();
	//public static PIDTuner pidTuner = new PIDTuner();
	public static OI oi;

	Command m_autonomousCommand;
	public static Command teleOpDrive;
	
	SendableChooser<Command> driveMode = new SendableChooser<>();
	SendableChooser<String> autoModeChooser = new SendableChooser<>();

	UsbCamera fisheyeCamera;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//AutoCalibration.initializeAutoCalibration();
		oi = new OI();
		
		// If in controlled mode, then 
		if(RobotMap.WristState == 0 && RobotMap.ArmState == 0 && RobotMap.ElevatorState == 0) {
			autoModeChooser.addDefault("Center Auto", "Center Auto");
			autoModeChooser.addObject("Drive Straight", "Drive Straight");
			autoModeChooser.addObject("Left Auto Switch", "Left Auto Switch");
			autoModeChooser.addObject("Right Auto Switch", "Right Auto Switch");
			autoModeChooser.addObject("Left Auto Scale", "Left Auto Scale");
			autoModeChooser.addObject("Right Auto Scale", "Right Auto Scale");
		} else {
			if(Robot.driveTrain.spartanGyro != null) {	// May need to be put in a try/catch
				autoModeChooser.addDefault("Center Auto Semi-Automatic", "Center Auto Semi-Automatic");
				autoModeChooser.addObject("Drive Straight", "Drive Straight");
			} else {
				autoModeChooser.addDefault("Center Auto Manual", "Center Auto Manual");
				autoModeChooser.addObject("Drive Straight Manual", "Drive Straight Manual");
			}
		}
		SmartDashboard.putData("Auto Selector", autoModeChooser);

		driveMode.addDefault("Split Arcade", new SetSplitArcadeDrive());
		driveMode.addObject("Cheesy Drive", new SetCheesyDrive());
		driveMode.addObject("Tank Drive", new SetTankDrive());
		
		try {
			//fisheyeCamera = CameraServer.getInstance().startAutomaticCapture();	// Commented out for now to remove rioLog prints
		} catch(Exception e) {
			
		}
		
		SmartDashboard.putData("Drive Type", driveMode);

		elevator.setElevatorShiftersHigh();
		//pidTuner.initializeSmartDashboard();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveTrain.setMotorsToCoast();
		//elevator.setMotorsToCoast();	// Comment this out during competitions/ change to brake
		//elevator.setElevatorShiftersHigh();
		arm.setMotorsToCoast();
		wrist.setMotorsToCoast();
		intake.setMotorsToCoast();
		
		Robot.driveTrain.resetSensors();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		updateSmartDashboard();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		driveTrain.resetSensors();
		//driveTrain.setDriveShiftLow();
		driveTrain.setMotorsToBrake();
		
		elevator.setMotorsToBrake();
		arm.setMotorsToBrake();
		wrist.setMotorsToBrake();
		intake.setMotorsToBrake();
		intake.retractIntakePistons();
		
		// schedule the autonomous command (example)
		String auto = autoModeChooser.getSelected();
		switch(auto){
			case "Center Auto":
				m_autonomousCommand = new CenterAuto();
				//m_autonomousCommand = new CenterAutoManual();
				break;
			case "Drive Straight":
				m_autonomousCommand = new DriveStraight();
				break;
			case "Left Auto Switch":
				m_autonomousCommand = new AutoLeftStartSwitchFocus();
				break;
			case "Right Auto Switch":
				m_autonomousCommand = new AutoRightStartSwitchFocus();
				break;
			case "Left Auto Scale":
				m_autonomousCommand = new AutoLeftStartToScale();
				break;
			case "Right Auto Scale":
				m_autonomousCommand = new AutoRightStartToScale();
				break;
			case "Center Auto Semi-Automatic":
				m_autonomousCommand = new CenterAutoSemiAutomatic();
				break;
			case "Center Auto Manual":
				m_autonomousCommand = new CenterAutoManual();
				break;
			case "Drive Straight Manual":
				m_autonomousCommand = new DriveStraightManual();
				break;
			default:
				m_autonomousCommand = null;
		}

		if(elevator.getElevatorShiftersStatus())
			elevator.setElevatorShiftersLow();
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		updateSmartDashboard();
	}

	@Override
	public void teleopInit() {
		
		elevator.setElevatorShiftersHigh();
		
		//Sets drive train motors to coast.
		driveTrain.resetSensors();
		driveTrain.setMotorsToCoast();
		//driveTrain.setDriveShiftLow();
		elevator.setMotorsToBrake();
		arm.setMotorsToBrake();
		wrist.setMotorsToBrake();
		intake.setMotorsToBrake();
		
		if(elevator.getElevatorShiftersStatus())
			elevator.setElevatorShiftersLow();
		
		Scheduler.getInstance().removeAll();
		// This makes sure that the autonomous stops running when
		// teleOp starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();		// Consider commenting this out under certain conditions...
		}
		
		teleOpDrive = driveMode.getSelected();
		if (teleOpDrive != null) {
			teleOpDrive.start();
			Robot.driveTrain.setDefaultCommand(teleOpDrive);			// To prevent KillAll() from switching drive modes mid-match
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		oi.checkDriverInputs();
		updateSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		updateSmartDashboard();
	}
	
	void updateSmartDashboard(){
		driveTrain.updateSmartDashboard();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		intake.updateSmartDashboard();
		climber.updateSmartDashboard();
		//wings.updateSmartDashboard();
		//stabilizers.updateSmartDashboard();
		controls.updateSmartDashboard();
		//pidTuner.updateSmartDashboard();
		//AutoCalibration.updateSmartDashboard();
	}
	
}
