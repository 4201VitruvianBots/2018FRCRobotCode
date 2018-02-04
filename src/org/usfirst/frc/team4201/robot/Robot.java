/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

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
	public static Intake intake = new Intake();
	public static Elevator elevator = new Elevator();
	public static Arm arm = new Arm();
	public static Wrist wrist = new Wrist();
	public static Controls controls = new Controls();
	public static OI oi;

	Command m_autonomousCommand;
	Command teleOpDrive;
	SendableChooser<Command> autoModes = new SendableChooser<>();
	SendableChooser<Command> driveMode = new SendableChooser<>();
	SendableChooser<Command> autoModeChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		//Robot.driveTrain.initializeLiveWindow();
		
		autoModeChooser.addDefault("DriveStraight", new DriveStraight());
		autoModeChooser.addObject("Turn", new Turn());
		autoModeChooser.addObject("CenterRobotToLeftSwitch", new CenterRobotToLeftSwitch());
		autoModeChooser.addObject("RightRobotToRightScale", new RightRobotToRightScale());
		autoModeChooser.addObject("Turn", new AutoTemplate());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto Selector", autoModeChooser);
		
		driveMode.addDefault("Cheesy Drive", new SetCheesyDrive());
		driveMode.addObject("Tank Drive", new SetTankDrive());
		driveMode.addObject("Split Arcade", new SetSplitArcadeDrive());

		SmartDashboard.putData("Drive Type", driveMode);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		controls.updateSmartDashboard();
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
		m_autonomousCommand = autoModeChooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		// Sets drive train motors to brake.
		driveTrain.setMotorsToBrake();
		driveTrain.resetEncoders();

		// schedule the autonomous command (example)
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
    	SmartDashboard.putBoolean("Turning", RobotMap.isTurning);

		driveTrain.updateSmartDashboard();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		controls.updateSmartDashboard();
	}

	@Override
	public void teleopInit() {
		Robot.oi.enableXBoxLeftRumble();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		//Sets drive train motors to coast.
		driveTrain.setMotorsToCoast();
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		if (teleOpDrive != null) {
			teleOpDrive.start();
		}
		driveTrain.resetEncoders();
		//arm.initalizeSetpoints();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		//driveTrain.updateSmartDashboard();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		controls.updateSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		//driveTrain.updateSmartDashboard();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		controls.updateSmartDashboard();
	}
	
}
