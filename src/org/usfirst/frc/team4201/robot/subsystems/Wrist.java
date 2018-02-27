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
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends PIDSubsystem {
	static double kP = 0.03;		// Test values for Triple Threat
	static double kI = 0;
	static double kD = 0;
	static double kF = 0.2;
	static double period = 0.01;
	
	public static int armLimiterLowerBound = -50;
	public static int armLimiterUpperBound = 50;

	public double angleLowerLimit = -140;												// -75
	public double angleUpperLimit = 160;													// 50 	
	public double sensorLowerLimit = 0;													//-133;
	public double sensorUpperLimit = -1080; 	// Negative value to 'invert' sensor		// 80; 
	static double sensorOffset = 657;														// -240;
	static double voltageLowerLimit = 0;
	static double voltageUpperLimit = 5;

	public static int state = 0;
	
	public WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.wristMotor);
	public AnalogInput wP = new AnalogInput(RobotMap.wristPot);
	public AnalogPotentiometer wristPot = new AnalogPotentiometer(wP, sensorUpperLimit, sensorOffset);
	
	public Wrist() {
		super("Wrist", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		//setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		wristMotor.setNeutralMode(NeutralMode.Brake);
		wristMotor.configPeakOutputForward(1, 0);
		wristMotor.configPeakOutputReverse(-1, 0);
		//wristMotor.setSafetyEnabled(true);
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getRelativeAngle());
		
		// Enable the PIDController if state == 0
		if(state == 0)
			enable();
		
		// Add the PIDController to LiveWindow
		LiveWindow.addChild(this, this);
		
		wristPot.setName("Potentiometer");
		wristPot.setSubsystem("Wrist");
        LiveWindow.add(wristPot);
        
        wristMotor.setName("Wrist Motor");
        wristMotor.setSubsystem("Wrist");
        LiveWindow.add(wristMotor);
	}
	
	// Get the angle of the wrist
	public double getMotorAngle() {
		return wristPot.get();
		//return (wristPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	// Get the angle of the wrist
	public double getAbsoluteAngle() {
		return wristPot.get() / 2.333; // 2.333 gear ratio
		//return (wristPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	// Get the angle of the wrist based off of the angle of the arm
	public double getRelativeAngle() {
		return getAbsoluteAngle() + (-25 + Robot.arm.getAngle());
	}
	
	public boolean checkLimits(double value){
		// check if the value is bound by the hard limits
		if(value > angleLowerLimit && value < angleUpperLimit)
			return true;
		else
			return false;
	}
	
	public void setMotorsToBrake(){
		wristMotor.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorsToCoast(){
		wristMotor.setNeutralMode(NeutralMode.Coast);
	}
	
	public void setDirectOutput(double output){
		wristMotor.set(ControlMode.PercentOutput, output);
	}
	
	public void updateWristAngle(){
		// Summary of what this does:
		// 1. If the wrist does not need to be limited, setpoint of wrist is just set
		// 2. If the wrist needs to be limited, read from an array to find the limit
		// 3. If the setpoint is outside of the limit, don't use the limit, otherwise
		// 4. Move the wrist to the limit, biasing it towards where the wrist's angle is.
		// (If the wrist is below the horizon, invert the setpoint limit so that it is negative, otherwise keep the setpoint limit positive)

		// Update the wrist limits based on Arm angle();
		angleLowerLimit = getRelativeAngle() - 140;
		angleUpperLimit = getRelativeAngle() + 160;
		setInputRange(angleLowerLimit, angleUpperLimit);
		
		// If the arm is outside of our limits, do nothing
		
		if(Robot.arm.getAngle()  >= armLimiterLowerBound && Robot.arm.getAngle()  <= armLimiterUpperBound){
			// Get the limit from our array. The array is basically a mirror at 0 degrees, so we swap how we access the array at that point. (from 0->arrayLength - 1 to arraayLength->0)

			int setpointLimit = WristLimitTable.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - armLimiterLowerBound];
				
			// If the setpoint we're attempting to set is less than our limit, set the setpoint to our limit
			if(Math.abs(getSetpoint()) < setpointLimit){
				int setpoint = setpointLimit;
					
				// Invert the limit setpoint if you are below the horizon (This is the nearest angle that is legal)
				if(getSetpoint() < 0)
					setpoint = -setpoint;
				
				// Set this as the new setpoint
				setSetpoint(setpoint);
			}
		}
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
	
	public void updateSmartDashboard() {
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Wrist", "Absolute Angle", getAbsoluteAngle());
		Shuffleboard.putNumber("Wrist", "Relative Angle", getRelativeAngle());
		Shuffleboard.putNumber("Wrist", "Motor Angle", getMotorAngle());
		Shuffleboard.putNumber("Wrist", "Setpoint", getPIDController().getSetpoint());
		Shuffleboard.putNumber("Wrist", "Pot Avg. Voltage", wP.getAverageVoltage());
		Shuffleboard.putNumber("Wrist", "Lower Limit", angleLowerLimit);
		Shuffleboard.putNumber("Wrist", "Upper Limit", angleUpperLimit);
		Shuffleboard.putNumber("Wrist", "Pot Test", wristPot.get());
		Shuffleboard.putBoolean("Wrist", "PID Enabled", getPIDController().isEnabled());

		// For TripleThreat Testbed
		//Shuffleboard.putNumber("Triple Threat", "Wrist Absolute Angle", getAbsoluteAngle());
		//Shuffleboard.putNumber("Triple Threat", "Wrist Relative Angle", getRelativeAngle());
		//Shuffleboard.putNumber("Triple Threat", "Wrist Setpoint", getPIDController().getSetpoint());
		//Shuffleboard.putNumber("Triple Threat", "Wrist Avg. Voltage", wP.getAverageVoltage());
		//Shuffleboard.putNumber("Triple Threat", "Wrist Lower Limit", angleLowerLimit);
		//Shuffleboard.putNumber("Triple Threat", "Wrist Upper Limit", angleUpperLimit);
		//Shuffleboard.putNumber("Triple Threat", "Wrist Pot Test", wristPot.get());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Wrist Angle", getRelativeAngle());
		SmartDashboard.putBoolean("Wrist PID Enabled", getPIDController().isEnabled());
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new UpdateWristSetpoint());
	}
}
