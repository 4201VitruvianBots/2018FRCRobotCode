package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
	
	public WPI_TalonSRX[] intakeMotors = {
		new WPI_TalonSRX(RobotMap.intakeLeft),	// To maintain consistency, left should always go before right
		new WPI_TalonSRX(RobotMap.intakeRight)
	};
	
	DoubleSolenoid intakePistons = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.intakeForward, RobotMap.intakeReverse);
	
	public Intake() {
		super("Intake");
		
		// Set right motor to follow left motor
		
		// Set Motor Controller Feedback Device
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < intakeMotors.length; i++){	// Changed to intakeMotors.length so it adjusts to array length
			intakeMotors[i].configPeakOutputForward(1, 0);
			intakeMotors[i].configPeakOutputReverse(-1, 0);
			intakeMotors[i].setNeutralMode(NeutralMode.Brake);	// Brake is probably preferred for this game, due to the 1 cube control limit
		}
		intakeMotors[1].set(ControlMode.Follower, intakeMotors[0].getDeviceID());
		intakeMotors[1].setInverted(true);
		
	}
	
	public void setIntakeMotorOutput(double intakeSpeed){
		intakeMotors[0].set(ControlMode.PercentOutput, intakeSpeed);
	}
	
	public boolean getIntakePistonStatus() {
		return intakePistons.get() == Value.kForward ? true : false;
	}
	
	public void extendIntakePistons() {
		intakePistons.set(Value.kForward);
	}
	
	public void retractIntakePistons() {
		intakePistons.set(Value.kReverse);
	}
	
	public void setMotorsToBrake(){
		for(int i = 0; i < intakeMotors.length; i++)
			intakeMotors[i].setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorsToCoast(){
		for(int i = 0; i < intakeMotors.length; i++)
			intakeMotors[i].setNeutralMode(NeutralMode.Coast);
	}
	
	public void updateSmartDashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putBoolean("Intake", "Pistons", getIntakePistonStatus());
		Shuffleboard.putNumber("Intake", "Speed", intakeMotors[0].get());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putBoolean("Intake Pistons", getIntakePistonStatus());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
