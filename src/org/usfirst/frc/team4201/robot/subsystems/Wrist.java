package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.AdjustWristSetpoint;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends PIDSubsystem {
	static double kP = 0.4;		// Test values for Triple Threat
	static double kI = 0;
	static double kD = 0.4;
	static double kF = 0;
	static double period = 0.01;
	
	public double angleUpperLimit = 285;
	public double angleLowerLimit = 72;
	static double angleOffset = 72;
	static double voltageUpperLimit = 5;
	static double voltageLowerLimit = 0;
	
	public WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.wristMotor);
	
	public AnalogInput wristPot = new AnalogInput(RobotMap.wristPot);
	
	public Wrist() {
		super("Wrist", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		wristMotor.setNeutralMode(NeutralMode.Brake);
		wristMotor.configPeakOutputForward(1, 0);
		wristMotor.configPeakOutputReverse(-1, 0);
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getAbsoluteAngle());
		
		// Enable the PIDController;
		enable();
		
		LiveWindow.addChild(this, this);
	}
	
	//public void setSetpointRelative(double deltaSetpoint) {}
	
	//public void setSetpoint(double setpoint) {}
	
	
	// Get the angle of the wrist
	public double getAbsoluteAngle() {
		return (wristPot.getAverageVoltage() * ((angleUpperLimit - angleLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + angleOffset;
	}
	
	// Get the angle of the wrist based off of the angle of the arm
	public double getRelativeAngle() {
		return (wristPot.getAverageVoltage() * ((angleUpperLimit - angleLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) - Robot.arm.getAngle() + angleOffset;
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Wrist Absolute Angle", getAbsoluteAngle());
		SmartDashboard.putNumber("Wrist Relative Angle", getRelativeAngle());
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getAbsoluteAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		wristMotor.set(ControlMode.PercentOutput, output);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new AdjustWristSetpoint());
	}

}
