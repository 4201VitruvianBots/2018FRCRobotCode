package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
	
	public BaseMotorController[] elevatorMotors = {
			new WPI_TalonSRX(RobotMap.elevatorA),
			new WPI_TalonSRX(RobotMap.elevatorB),
		};
	
	public Elevator() {
		super("Elevator");
	}
	
	public void elevatorUp() {
		
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}

	