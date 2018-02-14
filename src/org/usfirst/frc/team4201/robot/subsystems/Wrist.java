package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.WristLimitTable;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends PIDSubsystem {
	static double kP = 0.03;		// Test values for Triple Threat
	static double kI = 0;
	static double kD = 0;
	static double kF = 0.2;
	static double period = 0.01;
	
	int armLimitLowerBound = -42;
	int armLimitUpperBound = 138;

	public double angleLowerLimit = -90;	// -75
	public double angleUpperLimit = 90;		// 50 	
	public double sensorLowerLimit = 0;		//-133;
	public double sensorUpperLimit = 360; 	// 80; 
	static double sensorOffset = -240;
	static double voltageLowerLimit = 0;
	static double voltageUpperLimit = 5;
	
	public WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.wristMotor);
	public AnalogInput wP = new AnalogInput(RobotMap.wristPot);
	public AnalogPotentiometer wristPot = new AnalogPotentiometer(wP, 360, -224);
	
	public Wrist() {
		super("Wrist", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		//setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		wristMotor.setNeutralMode(NeutralMode.Coast);
		wristMotor.configPeakOutputForward(1, 0);
		wristMotor.configPeakOutputReverse(-1, 0);
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getRelativeAngle());
		
		// Enable the PIDController;
		enable();
		
		// Add the PIDController to LiveWindow
		LiveWindow.addChild(this, this);
	}
	
	public void setDirectOutput(double output){
		wristMotor.set(ControlMode.PercentOutput, output);
	}
	
	// Get the angle of the wrist
	public double getAbsoluteAngle() {
		return wristPot.get();
		//return (wristPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	// Get the angle of the wrist based off of the angle of the arm
	public double getRelativeAngle() {
		return getAbsoluteAngle() + (180 + Robot.arm.getAngle());
	}
	
	public boolean checkLimits(double value){
		// check if the value is bound by the hard limits
		if(value > angleLowerLimit && value < angleUpperLimit){
			// check if the value is bound by the soft limits
			if(Robot.arm.getAngle() >= armLimitLowerBound && Robot.arm.getAngle() <= armLimitUpperBound){
				int setpointLimit = WristLimitTable.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - armLimitLowerBound];
				
				if(Math.abs(getRelativeAngle()) < setpointLimit)
					return true;
			}
		}
		
		return false;
	}
	
	public void updateWristAngle(){
		// Summary of what this does:
		// 1. If the wrist does not need to be limited, setpoint of wrist is just set
		// 2. If the wrist needs to be limited, read from an array to find the limit
		// 3. If the setpoint is outside of the limit, don't use the limit, otherwise
		// 4. Move the wrist to the limit, biasing it towards where the wrist's angle is.
		// (If the wrist is below the horizon, invert the setpoint limit so that it is negative, otherwise keep the setpoint limit positive)

		// Update the wrist limits based on Arm angle();
		angleLowerLimit = getRelativeAngle() - 75;
		angleUpperLimit = getRelativeAngle() + 50;
		setInputRange(angleLowerLimit, angleUpperLimit);
		
		// If the arm is outside of our limits, do nothing
		if(Robot.arm.getAngle() < armLimitLowerBound || Robot.arm.getAngle()  > armLimitUpperBound) {// If the arm is outside of our limiting range, just pass the setpoint with no modifications
			//setSetpoint(getSetpoint());
		} else { // if(getArmAngle >= armLimitLowerBound && getArmAngle <= armLimitUpperBound){
			// Get the limit from our array. The array is basically a mirror at 0 degrees, so we swap how we access the array at that point. (from 0->arrayLength - 1 to arraayLength->0)
			/*
			int setpointLimit = WristLimitTable.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - armLimitLowerBound];
				
			// If the setpoint we're attempting to set is less than our limit, set the setpoint to our limit
			if(Math.abs(Robot.arm.getAngle()) < setpointLimit){
				int setpoint = setpointLimit;
					
				// Invert the limit setpoint if you are below the horizon (This is the nearest angle that is legal)
				if(getAbsoluteAngle() < 0)
					setpoint = -setpoint;
				
				// Set this as the new setpoint
				setSetpoint(setpoint);
			}
			*/
		}
		
	}
	
	public void updateSmartDashboard() {
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Wrist", "Wrist Absolute Angle", getAbsoluteAngle());
		Shuffleboard.putNumber("Wrist", "Wrist Relative Angle", getRelativeAngle());
		Shuffleboard.putNumber("Wrist", "Wrist Setpoint", getPIDController().getSetpoint());
		Shuffleboard.putNumber("Wrist", "Wrist Avg. Voltage", wP.getAverageVoltage());
		Shuffleboard.putNumber("Wrist", "Wrist Lower Limit", angleLowerLimit);
		Shuffleboard.putNumber("Wrist", "Wrist Upper Limit", angleUpperLimit);
		Shuffleboard.putNumber("Wrist", "Wrist Pot Test", wristPot.get());

		// For TripleThreat Testbed
		Shuffleboard.putNumber("Triple Threat", "Wrist Absolute Angle", getAbsoluteAngle());
		Shuffleboard.putNumber("Triple Threat", "Wrist Relative Angle", getRelativeAngle());
		Shuffleboard.putNumber("Triple Threat", "Wrist Setpoint", getPIDController().getSetpoint());
		Shuffleboard.putNumber("Triple Threat", "Wrist Avg. Voltage", wP.getAverageVoltage());
		Shuffleboard.putNumber("Triple Threat", "Wrist Lower Limit", angleLowerLimit);
		Shuffleboard.putNumber("Triple Threat", "Wrist Upper Limit", angleUpperLimit);
		Shuffleboard.putNumber("Triple Threat", "Wrist Pot Test", wristPot.get());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Wrist Angle", getRelativeAngle());
	}
	

	@Override
	protected double returnPIDInput() {
		return getRelativeAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		wristMotor.set(ControlMode.PercentOutput, output);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new UpdateWristSetpoint());
	}
}
