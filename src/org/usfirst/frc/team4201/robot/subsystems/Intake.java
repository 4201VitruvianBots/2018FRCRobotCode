package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
	
	public WPI_TalonSRX[] intakeMotors = {
		new WPI_TalonSRX(RobotMap.intakeLeft),	// To maintain consistency, left should always go before right
		new WPI_TalonSRX(RobotMap.intakeRight)
	};
	
	DoubleSolenoid intakePistons = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.intakeForward, RobotMap.intakeReverse);
	DoubleSolenoid intakePressure = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.intakePressureForward, RobotMap.intakePressureReverse);
	DigitalInput bumpSwitch = new DigitalInput(RobotMap.intakeBumpSwitch);
	
	public static boolean isCubePresent = false;
	
	public Intake() {
		super("Intake");
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < intakeMotors.length; i++){	// Changed to intakeMotors.length so it adjusts to array length
			//intakeMotors[i].configPeakOutputForward(1, 0);
			//intakeMotors[i].configPeakOutputReverse(-1, 0);
			intakeMotors[i].setNeutralMode(NeutralMode.Brake);	// Brake is probably preferred for this game, due to the 1 cube control limit
			intakeMotors[i].configContinuousCurrentLimit(20, 0);
			intakeMotors[i].configPeakCurrentLimit(0, 0);
			intakeMotors[i].enableCurrentLimit(true);
		}
		intakeMotors[1].set(ControlMode.Follower, intakeMotors[0].getDeviceID());
		intakeMotors[1].setInverted(true);
		
		intakePistons.setName("Pistons");
		intakePistons.setSubsystem("Intake");
        LiveWindow.add(intakePistons);
        
        intakeMotors[0].setName("Intake Left Motor");
        intakeMotors[0].setSubsystem("Intake");
        LiveWindow.add(intakeMotors[0]);
        
        intakeMotors[1].setName("Intake Right Motor");
        intakeMotors[1].setSubsystem("Intake");
        LiveWindow.add(intakeMotors[1]);
	}
	
	public void setIntakeMotorOutput(double intakeSpeed){
		intakeMotors[0].set(ControlMode.PercentOutput, intakeSpeed);
		intakeMotors[1].set(ControlMode.Follower, intakeMotors[0].getDeviceID());
	}
	
	public void setIntakeMotorOutput(double leftSpeed, double rightSpeed){
		intakeMotors[0].set(ControlMode.PercentOutput, leftSpeed);
		intakeMotors[1].set(ControlMode.PercentOutput, rightSpeed);
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
	
	public boolean getIntakePressureStatus() {
		return intakePressure.get() == Value.kForward ? true : false;
	}
	
	public void extendIntakePressure() {
		intakePressure.set(Value.kForward);
	}
	
	public void retractIntakePressure() {
		intakePressure.set(Value.kReverse);
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
		Shuffleboard.putBoolean("Intake", "Pressure", getIntakePressureStatus());
		Shuffleboard.putNumber("Intake", "Speed", intakeMotors[0].get());
		Shuffleboard.putNumber("Intake", "Current", Robot.wrist.wristMotor.getOutputCurrent());
		Shuffleboard.putNumber("Intake", "Left Motor Current", intakeMotors[0].getOutputCurrent());
		Shuffleboard.putNumber("Intake", "Right Motor Current", intakeMotors[1].getOutputCurrent());
		Shuffleboard.putNumber("Intake", "Total Motor Current", intakeMotors[0].getOutputCurrent() + intakeMotors[1].getOutputCurrent());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putBoolean("Intake Pistons", getIntakePistonStatus());
		SmartDashboard.putBoolean("Intake Pressure", getIntakePressureStatus());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		//setDefaultCommand(new UpdateIntakeState());
	}
}
