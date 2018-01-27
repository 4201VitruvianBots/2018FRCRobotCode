package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
	public BaseMotorController[] elevatorMotors = {
		new WPI_TalonSRX(RobotMap.elevatorA),
		new WPI_TalonSRX(RobotMap.elevatorB),
	};
	
	AnalogInput linePot;
	
	public Elevator() {
		super("Elevator");
		
		for(int i = 0; i < 2; i++){
			elevatorMotors[i].configPeakOutputForward(1, 0);
			elevatorMotors[i].configPeakOutputReverse(-1, 0);
			elevatorMotors[i].setNeutralMode(NeutralMode.Brake);
		}
		linePot = new AnalogInput(0);
	}
	
	public void elevatorAUp() {
		elevatorMotors[0].set(ControlMode.PercentOutput, 1);
	}
	
	public void elevatorADown() {
		elevatorMotors[0].set(ControlMode.PercentOutput, -1);
	}
	
	public void elevatorAStop() {
		elevatorMotors[0].set(ControlMode.PercentOutput, 0);
	}
	
	public void elevatorBUp() {
		elevatorMotors[1].set(ControlMode.PercentOutput, 1);
	}
	
	public void elevatorBDown() {
		elevatorMotors[1].set(ControlMode.PercentOutput, -1);
	}
	
	public void elevatorBStop() {
		elevatorMotors[1].set(ControlMode.PercentOutput, 0);
	}
	
	public double getElevatorIN(){
		return linePot.getAverageVoltage() * 0.09; // 0.09 v/in 
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Line Potentiometer", linePot.getAverageVoltage());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}

	