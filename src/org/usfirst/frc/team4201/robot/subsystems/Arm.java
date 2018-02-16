package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.UpdateArmSetpoint;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends PIDSubsystem {
	static double kP = 0.06;		// Test values for Triple Threat
	static double kI = 0;
	static double kD = 0;
	static double kF = 0;
	static double period = 0.01;

	public double angleLowerLimit = -30;	// -33;
	public double angleUpperLimit = 80;		// 60
	public double sensorLowerLimit = -41;
	public double sensorUpperLimit = 90;
	static double sensorOffset = -80;
	static double voltageUpperLimit = 3;
	static double voltageLowerLimit = 1;

	public static int state = 0;
	
	public WPI_TalonSRX[] armMotors = {
		new WPI_TalonSRX(RobotMap.armMotor),
		//new WPI_TalonSRX(RobotMap.armMotor + 1); // Using test arm
	};
	
	AnalogInput aP = new AnalogInput(RobotMap.armPot);
	public AnalogPotentiometer armPot = new AnalogPotentiometer(aP, 360, -91);
	
	public Arm() {
		super("Arm", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-1, 1);
		
		for(int i = 0; i < armMotors.length; i++) {
			armMotors[0].setNeutralMode(NeutralMode.Coast);
			armMotors[0].configPeakOutputForward(1, 0);
			armMotors[0].configPeakOutputReverse(-1, 0);
			//armMotors[i].setSafetyEnabled(true);
			//armMotors[i].configContinuousCurrentLimit(40, 0);
			//armMotors[i].configPeakCurrentLimit(80, 0);
			//armMotors[i].configPeakCurrentDuration(100, 0);
		}
		//armMotors[1].set(ControlMode.Follower, armMotor.getDeviceID());
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getAngle());
		
		// Enable the PIDController;
		enable();
		
		LiveWindow.addChild(this, this);
		
		armPot.setName("Potentiometer");
		armPot.setSubsystem("Arm");
        LiveWindow.add(armPot);
        
        armMotors[0].setName("Arm Motor");
        armMotors[0].setSubsystem("Arm");
        LiveWindow.add(armMotors[0]);
	}
	
	public double getAngle() {
		return armPot.get();
		//return (armPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	public boolean checkLimits(double value){
		if(value > angleLowerLimit && value < angleUpperLimit)
			return true;
		else
			return false;
	}

	public void setMotorsToBrake(){
		for(int i = 0; i < armMotors.length; i++)
			armMotors[i].setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorsToCoast(){
		for(int i = 0; i < armMotors.length; i++)
			armMotors[i].setNeutralMode(NeutralMode.Coast);
	}
	
	public void setDirectOutput(double output){
		armMotors[0].set(ControlMode.PercentOutput, output);
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		armMotors[0].set(ControlMode.PercentOutput, output);
	}
	
	public void updateSmartDashboard() {
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Arm", "Angle", getAngle());
		Shuffleboard.putNumber("Arm", "Pot Avg. Voltage", aP.getAverageVoltage());
		Shuffleboard.putNumber("Arm", "Setpoint", getSetpoint());
		Shuffleboard.putBoolean("Arm", "PID Enabled", getPIDController().isEnabled());
		
		// For TripleThreat Testbed
		//Shuffleboard.putNumber("Triple Threat", "Arm Angle", getAngle());
		//Shuffleboard.putNumber("Triple Threat", "Arm Pot Test", armPot.get());
		//Shuffleboard.putNumber("Triple Threat", "Arm Avg. Voltage", aP.getAverageVoltage());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Arm Angle", getAngle());
		SmartDashboard.putBoolean("Arm PID Enabled", getPIDController().isEnabled());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new UpdateArmSetpoint());
	}
}
