package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controls extends Subsystem{
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	DigitalOutput LEDS[] = {
		new DigitalOutput(RobotMap.redSignal),
		new DigitalOutput(RobotMap.greenSignal),
		new DigitalOutput(RobotMap.blueSignal),
		new DigitalOutput(RobotMap.flashSignal),
	};
	
	public int powerState;
	
	public Controls(){
		super("Controls");
	}
	
	public void updateCurrentState(){
		if(pdp.getTotalCurrent() > 200)
			powerState = 1;
		else powerState = 0;
		
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
	}
	
	public void checkSensorHealth(){
	}
	
	public void setRGBF(int channel) {
		LEDS[channel].set(!LEDS[channel].get());
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
		Shuffleboard.putNumber("Controls", "Intake Left Current", Robot.intake.intakeMotors[0].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Intake Right Current", Robot.intake.intakeMotors[1].getOutputCurrent());
		Shuffleboard.putNumber("Controls", "Total Current Draw", pdp.getTotalCurrent());
		Shuffleboard.putBoolean("Controls", "Brownout", RobotController.isBrownedOut());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Total Current Draw", pdp.getTotalCurrent());
		SmartDashboard.putBoolean("Brownout", RobotController.isBrownedOut());
	}
	
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		//setDefaultCommand(command);
	}
}