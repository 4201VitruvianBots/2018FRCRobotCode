/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4201.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoReleaseWristSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.routines.*;
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
	//public static Climber climber = new Climber();
	//public static Wings wings = new Wings();
	//public static Stabilizers stabilizers = new Stabilizers();
	public static Controls controls = new Controls();
	//public static PIDTuner pidTuner = new PIDTuner();
	public static OI oi;

	Command m_autonomousCommand;
	public static Command teleOpDrive;
	
	SendableChooser<Command> driveMode = new SendableChooser<>();
	SendableChooser<Command> brakeMode = new SendableChooser<>();
	public static SendableChooser<String> driverControls = new SendableChooser<>();
	public static SendableChooser<String> operatorControls = new SendableChooser<>();
	SendableChooser<String> autoModeChooser = new SendableChooser<>();
	
	String[] autoRoutines = {
		"Drive Straight",
		"Center Auto",
		"Center Auto Semi-Automatic",
		"Left Auto Scale + Switch",
		"Right Auto Scale + Switch",
		"Left Auto Double Scale",
		"Right Auto Double Scale",
		"Left Switch Auto",
		"Left Switch Auto Far",
		"Right Switch Auto",
		"Right Switch Auto Far",
		"Auto Calibration",
		"Pathfinder Test",
		"Do Nothing",
		"Partner Auto Left",
		"Partner Auto Right",
		"Center Auto Test"
	};
	
	UsbCamera camera;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		AutoTesting.initializeAutoCalibration();
		oi = new OI();
		
		// Add autos to dashboard based on current mechanism state
		if(RobotMap.WristState == 0 && RobotMap.ArmState == 0) {
			if(RobotMap.ElevatorState == 0) {
				autoModeChooser.addDefault(autoRoutines[1], autoRoutines[1]);	// Default
				autoModeChooser.addObject(autoRoutines[3], autoRoutines[3]);
				autoModeChooser.addObject(autoRoutines[4], autoRoutines[4]);
				autoModeChooser.addObject(autoRoutines[7], autoRoutines[7]);
				//autoModeChooser.addObject(autoRoutines[8], autoRoutines[8]);
				autoModeChooser.addObject(autoRoutines[9], autoRoutines[9]);
				//autoModeChooser.addObject(autoRoutines[10], autoRoutines[10]);
				autoModeChooser.addObject(autoRoutines[14], autoRoutines[14]);
				autoModeChooser.addObject(autoRoutines[15], autoRoutines[15]);
			} else {
				// Center Auto Semi-Manual
				//autoModeChooser.addDefault(autoRoutines[2], autoRoutines[2]);	// Default
			}
			// Double Scale Autos
			autoModeChooser.addObject(autoRoutines[5], autoRoutines[5]);		// Left
			autoModeChooser.addObject(autoRoutines[6], autoRoutines[6]);		// Right
		}
		// Drive Straight/Test Autos
		
		autoModeChooser.addObject(autoRoutines[0], autoRoutines[0]);
		autoModeChooser.addObject(autoRoutines[11], autoRoutines[11]);
		autoModeChooser.addObject(autoRoutines[12], autoRoutines[12]);
		autoModeChooser.addObject(autoRoutines[13], autoRoutines[13]);			// Do nothing. Remove for competition
		autoModeChooser.addObject(autoRoutines[16], autoRoutines[16]);			// Center Auto Test
		
		SmartDashboard.putData("Auto Selector", autoModeChooser);
		

		SmartDashboard.putData("Reset Max Current Draw", new ResetMaxCurrentDraw());
		
		driveMode.addDefault("Split Arcade", new SetSplitArcadeDrive());
		driveMode.addObject("Cheesy Drive", new SetCheesyDrive());
		driveMode.addObject("Tank Drive", new SetTankDrive());
		
		DriverMapping.InitDriverMapping();
		SmartDashboard.putData("Driver Controls", driverControls);
		SmartDashboard.putData("Operator Controls", operatorControls);
		SmartDashboard.putData("Set Control Mapping", new SetControlMapping());
		
		DriverMapping.DEFAULT_DRIVER();
		DriverMapping.MELITA_OPERATOR();
		
		try {
			//camera = CameraServer.getInstance().startAutomaticCapture();	// Commented out for now to remove rioLog prints
			//camera.setVideoMode(PixelFormat.kMJPEG, 320, 200, 30);
		} catch(Exception e) {
			
		}
		
		SmartDashboard.putData("Drive Type", driveMode);
		
		wrist.initializeSmartDashboard();
		SmartDashboard.putData(new UpdateWristOffset());
		//pidTuner.initializeSmartDashboard();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		
		driveTrain.setMotorsToCoast();
		//elevator.setMotorsToCoast();	// Comment this out during competitions/ change to brake
		//elevator.setElevatorShiftersHigh();
		arm.setMotorsToCoast();
		wrist.setMotorsToCoast();
		intake.setMotorsToCoast();
		
		//Robot.driveTrain.resetSensors();
		Scheduler.getInstance().add(new AutoReleaseWristSetpoint());
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
		
		arm.setOutputRange(-1, 1);
		//arm.setSetpoint(arm.getAngle());
		//elevator.setSetpoint(elevator.getHieght());
		
		elevator.setMotorsToBrake();
		arm.setMotorsToBrake();
		wrist.setMotorsToBrake();
		intake.setMotorsToBrake();
		intake.retractIntakePistons();
		
		// schedule the autonomous command (example)
		String auto = autoModeChooser.getSelected();
		if(auto!=null){
			switch(auto){
				case "Drive Straight":
					m_autonomousCommand = new DriveStraight();
					break;
				case "Center Auto":
					m_autonomousCommand = new CenterAuto();
					break;
				case "Center Auto Semi-Automatic":
					m_autonomousCommand = new CenterAutoSemiAutomatic();
					break;
				case "Center Auto Test":
					m_autonomousCommand = new CenterAutoTest();
					break;
				case "Left Auto Scale + Switch":
					m_autonomousCommand = new AutoScaleSwitchLeft();
					break;
				case "Right Auto Scale + Switch":
					m_autonomousCommand = new AutoScaleSwitchRight();
					break;
				case "Left Auto Double Scale":
					m_autonomousCommand = new AutoDoubleScale(true);
					break;
				case "Right Auto Double Scale":
					m_autonomousCommand = new AutoDoubleScale(false);
					break;
				case "Left Switch Auto":
					m_autonomousCommand = new AutoSwitchSidesLeft(true);
					break;
				//case "Left Switch Auto Far":
				//	m_autonomousCommand = new AutoSwitchSidesLeft(false);
				//	break;
				case "Right Switch Auto":
					m_autonomousCommand = new AutoSwitchSidesRight(true);
					break;
				//case "Right Switch Auto Far":
				//	m_autonomousCommand = new AutoSwitchSidesRight(false);
				//	break;
				case "Partner Auto Left":
					m_autonomousCommand = new AutoPartnerLeft();
					break;
				case "Partner Auto Right":
					m_autonomousCommand = new AutoPartnerRight();
					break;
				case "Auto Calibration":
					m_autonomousCommand = new AutoTesting();
					break;
				case "Pathfinder Test":
					m_autonomousCommand = new PathfinderTest();
					break;
				case "Do Nothing":
				default:
					m_autonomousCommand = new DoNothing();
					break;
			}
		}

		if(elevator.getElevatorShiftersStatus())
			elevator.setElevatorShiftersHigh();
		
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
		Scheduler.getInstance().removeAll();
    	UpdateWristSetpoint.autoCommand = false;
		
		//elevator.setElevatorShiftersLow();
		arm.setSetpoint(arm.getAngle());
		elevator.setSetpoint(elevator.getHieght());
		
		//Sets drive train motors to coast.
		driveTrain.resetSensors();
		driveTrain.setMotorsToCoast();
		for(int i = 0; i < 4; i++)
			driveTrain.driveMotors[i].enableVoltageCompensation(false);
		//driveTrain.setDriveShiftLow();
		elevator.setMotorsToBrake();
		arm.setMotorsToBrake();
		wrist.setMotorsToBrake();
		intake.setMotorsToBrake();
		intake.extendIntakePressure();
		//if(elevator.getElevatorShiftersStatus())
		//elevator.setElevatorShiftersLow();
		
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
	
	// Update all SmartDashboard values. This is here to make it easier to enable/disable certain subsystems easier, since we always continuously update in all robot operating modes (auto, teleop, disabled, test)
	void updateSmartDashboard(){
		driveTrain.updateSmartDashboard();
		wrist.updateSmartDashboard();
		arm.updateSmartDashboard();
		elevator.updateSmartDashboard();
		intake.updateSmartDashboard();
		//climber.updateSmartDashboard();
		//wings.updateSmartDashboard();
		//stabilizers.updateSmartDashboard();
		controls.updateSmartDashboard();
		AutoTesting.updateSmartDashboard();
		
		controls.setMaxCurrentDraw();
	}
}
