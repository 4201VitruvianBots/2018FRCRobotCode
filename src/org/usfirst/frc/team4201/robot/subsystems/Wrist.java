package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.WristLimitTable;
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

	int armLimitLowerBound = -42;
	int armLimitUpperBound = 138;
	
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
	
	public void updateWristAngle(){
		// Summary of what this does:
		// 1. If the wrist does not need to be limited, setpoint of wrist is just set
		// 2. If the wrist needs to be limited, read from an array to find the limit
		// 3. If the setpoint is outside of the limit, don't use the limit, otherwise
		// 4. Move the wrist to the limit, biasing it towards where the wrist's angle is.
		// (If the wrist is below the horizon, invert the setpoint limit so that it is negative, otherwise keep the setpoint limit positive)
			
		// If the arm is outside of our limits, do nothing
		if(Robot.arm.getAngle() < armLimitLowerBound || Robot.arm.getAngle()  > armLimitUpperBound)// If the arm is outside of our limiting range, just pass the setpoint with no modifications
			setSetpoint(getSetpoint());
		else { // if(getArmAngle >= armLimitLowerBound && getArmAngle <= armLimitUpperBound){
			// Get the limit from our array. The array is basically a mirror at 0 degrees, so we swap how we access the array at that point. (from 0->arrayLength - 1 to arraayLength->0)
			
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
		}
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
