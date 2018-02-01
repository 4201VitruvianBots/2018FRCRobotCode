package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends PIDSubsystem {
	static double kP = 0.06;		// Test values for Triple Threat
	static double kI = 0;
	static double kD = 0;
	static double kF = 0;
	static double period = 0.01;
	
	public double angleUpperLimit = 180;
	public double angleLowerLimit = 49;
	static double angleOffset = 0;
	static double voltageUpperLimit = 3;
	static double voltageLowerLimit = 1;
	
	public WPI_TalonSRX armMotor = new WPI_TalonSRX(RobotMap.armMotor);
	
	public AnalogInput armPot = new AnalogInput(RobotMap.armPot);
	
	public Arm() {
		super("Wrist", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		armMotor.setNeutralMode(NeutralMode.Brake);
		armMotor.configPeakOutputForward(1, 0);
		armMotor.configPeakOutputReverse(-1, 0);
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getAngle());
		
		// Enable the PIDController;
		//enable();
		
		LiveWindow.addChild(this, this);
	}
	
	//public void setSetpointRelative(double deltaSetpoint) {}
	
	//public void setSetpoint(double setpoint) {}
	
	public double getAngle() {
		return (armPot.getAverageVoltage() * ((angleUpperLimit - angleLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + angleOffset;
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Wrist Angle", getAngle());
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		armMotor.set(ControlMode.PercentOutput, output);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
