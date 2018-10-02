package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controls extends Subsystem{
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	public static double maxCurrentDraw = 0;
	
	public Timer autoTimer = new Timer();
	Timer elevatorTimeout, wristTimer;
	boolean wLock = false, aLock = false, eLock = false;
	boolean wCheck = true, aCheck = true, eCheck = true;
	int wristCheck = 1;
	public DigitalOutput LEDS[] = {
		new DigitalOutput(RobotMap.redSignal),
		new DigitalOutput(RobotMap.greenSignal),
		new DigitalOutput(RobotMap.blueSignal),
		//new DigitalOutput(RobotMap.flashSignal),
	};
	
	public int powerState;
	
	public Controls(){
		super("Controls");
		
		elevatorTimeout = new Timer();
		wristTimer = new Timer();
		
	}
	
	public void updateCurrentState(){
		if(pdp.getTotalCurrent() > 200)
			powerState = 1;
		else 
			powerState = 0;
		/*
		switch(powerState){
			case 1:
				for(int i = 0; i < Robot.driveTrain.driveMotors.length; i++) {
					Robot.driveTrain.driveMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.driveTrain.driveMotors[i].configPeakCurrentLimit(80, 0);
					Robot.driveTrain.driveMotors[i].configPeakCurrentDuration(100, 0);
				}
				for(int i = 0; i < Robot.elevator.elevatorMotors.length; i++){
					Robot.elevator.elevatorMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.elevator.elevatorMotors[i].configPeakCurrentLimit(80, 0);
					Robot.elevator.elevatorMotors[i].configPeakCurrentDuration(100, 0);
				}
				for(int i = 0; i < Robot.arm.armMotors.length; i++){
					Robot.arm.armMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.arm.armMotors[i].configPeakCurrentLimit(80, 0);
					Robot.arm.armMotors[i].configPeakCurrentDuration(100, 0);
				}
				break;
			case 0:
			default:
				for(int i = 0; i < Robot.driveTrain.driveMotors.length; i++) {
					Robot.driveTrain.driveMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.driveTrain.driveMotors[i].configPeakCurrentLimit(80, 0);
					Robot.driveTrain.driveMotors[i].configPeakCurrentDuration(100, 0);
				}
				for(int i = 0; i < Robot.elevator.elevatorMotors.length; i++){
					Robot.elevator.elevatorMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.elevator.elevatorMotors[i].configPeakCurrentLimit(80, 0);
					Robot.elevator.elevatorMotors[i].configPeakCurrentDuration(100, 0);
				}
				for(int i = 0; i < Robot.arm.armMotors.length; i++){
					Robot.arm.armMotors[i].configContinuousCurrentLimit(40, 0);
					Robot.arm.armMotors[i].configPeakCurrentLimit(80, 0);
					Robot.arm.armMotors[i].configPeakCurrentDuration(100, 0);
				}
				break;
		}
		*/
	}
	
	public void checkMechanismStatus(){
		/*
		if(Robot.wrist.wristMotor.getOutputCurrent() >= 25 && wLock == false && wCheck){
			wristTimer.start();
			wLock = true;
			wristCheck = 1;
		} else if(Robot.wrist.wristMotor.getOutputCurrent() >= 40 && wLock == false && wCheck){
			wristTimer.start();
			wLock = true;
			wristCheck = 0;
		} 
		if(Robot.wrist.wristMotor.getOutputCurrent() < 25 && wLock && wCheck){
			wristTimer.stop();
			wristTimer.reset();
			wLock = false;
		}
		if(wristTimer.get() > 5 && wCheck){
			Robot.wrist.disable();
			RobotMap.WristState = 1;
			Robot.oi.enableXBoxLeftRumbleTimed();
			Robot.oi.enableXBoxRightRumbleTimed();
			Scheduler.getInstance().add(new FlashDriver("Wrist"));
			wCheck = false;
		}
		//*/
		if(Robot.elevator.elevatorMotors[0].getOutputCurrent() + Robot.elevator.elevatorMotors[1].getOutputCurrent() > 130){
			elevatorTimeout.start();
		} else {
			elevatorTimeout.stop();
			elevatorTimeout.reset();
		}
		
		if(elevatorTimeout.get() > 5) {
			Robot.elevator.disable();
			RobotMap.ElevatorState = 1;
		}
		
		// NEED TO TEST
		/*
		if(Robot.elevator.getPIDController().get() > 0) {
			// Is error absolute?
			if(!Robot.elevator.checkLimits(Robot.elevator.returnPIDInput() + Robot.elevator.getPIDController().getError())){
				Scheduler.getInstance().removeAll();
				Robot.elevator.disable();
				Elevator.state = 1;
			}
		} else if(Robot.elevator.getPIDController().get() < 0) {
			if(!Robot.elevator.checkLimits(Robot.elevator.returnPIDInput() - Robot.elevator.getPIDController().getError())){
				Scheduler.getInstance().removeAll();
				Robot.elevator.disable();
				Elevator.state = 1;
			}
		}
		*/
	}
	
	public boolean getRGBFStatus(int channel){
		return LEDS[channel].isPulsing();
	}
	
	public void setRGBF(int channel, boolean mode) {
		if(mode)
			LEDS[channel].enablePWM(0.5);
		else
			LEDS[channel].disablePWM();
	}
	
	
	public void updateSmartDashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Controls", "DriveTrain Front Left Current", Robot.driveTrain.driveMotors[0].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "DriveTrain Rear Left Current", Robot.driveTrain.driveMotors[1].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "DriveTrain Front Right Current", Robot.driveTrain.driveMotors[2].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "DriveTrain Rear Right Current", Robot.driveTrain.driveMotors[3].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "ElevatorA Current", Robot.elevator.elevatorMotors[0].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "ElevatorB Current", Robot.elevator.elevatorMotors[1].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Arm Current", Robot.arm.armMotors[0].getOutputCurrent());
		//Shuffleboard.putNumber("Controls", "ArmB Current", Robot.arm.armMotors[1].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Wrist Current", Robot.wrist.wristMotor.getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Intake Left Current", Robot.intake.intakeMotors[0].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Intake Right Current", Robot.intake.intakeMotors[1].getOutputCurrent());
		
		Shuffleboard.putNumber("Controls", "Total Current Draw", pdp.getTotalCurrent());
		
		// TO-DO: Need to replace with shuffleboard implementation when finished
		SmartDashboard.putNumber("Max Current Draw", getMaxCurrentDraw());
		
		Shuffleboard.putBoolean("Controls", "Brownout", RobotController.isBrownedOut());

		Shuffleboard.putNumber("Pathfinder", "Total Auto Time", autoTimer.get());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Total Current Draw", pdp.getTotalCurrent());
		SmartDashboard.putBoolean("Brownout", RobotController.isBrownedOut());
		
		Shuffleboard.putData("Controls", new PotentiometerRecalibration(Robot.wrist.wristPot));
		Shuffleboard.putData("Controls", new PotentiometerRecalibration(Robot.elevator.elevatorPot));
	}
	
	public void setMaxCurrentDraw() {
		if(maxCurrentDraw < pdp.getTotalCurrent())
			maxCurrentDraw = pdp.getTotalCurrent();
	}
	
	public double getMaxCurrentDraw() {
		return maxCurrentDraw;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new UpdateControlState());
	}
}
