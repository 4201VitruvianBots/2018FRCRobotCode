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
	
	public double angleUpperLimit = 90;
	public double angleLowerLimit = -41;
	static double angleOffset = -80;
	static double voltageUpperLimit = 3;
	static double voltageLowerLimit = 1;
	
	public WPI_TalonSRX armMotor = new WPI_TalonSRX(RobotMap.armMotor);
	public WPI_TalonSRX armMotor2 = new WPI_TalonSRX(RobotMap.armMotor + 1); // Using test arm
	
	public AnalogInput armPot = new AnalogInput(RobotMap.armPot);
	
	public Arm() {
		super("Wrist", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		armMotor.setNeutralMode(NeutralMode.Coast);
		armMotor.configPeakOutputForward(1, 0);
		armMotor.configPeakOutputReverse(-1, 0);
		armMotor2.setNeutralMode(NeutralMode.Coast);
		armMotor2.configPeakOutputForward(1, 0);
		armMotor2.configPeakOutputReverse(-1, 0);
		armMotor2.set(ControlMode.Follower, armMotor.getDeviceID());
		
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
		SmartDashboard.putNumber("Arm Angle", getAngle());
		SmartDashboard.putNumber("Arm Avg. Voltagee", armPot.getAverageVoltage());
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
