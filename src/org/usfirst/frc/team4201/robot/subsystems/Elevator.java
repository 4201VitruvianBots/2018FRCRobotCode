package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.UpdateElevatorSetpoint;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {
	static double kP = 0.02;		
	static double kI = 0;
	static double kD = 0;
	static double kF = 0;
	static double period = 0.01;

	public double hieghtLowerLimit = 0;			// -33
	public double hieghtUpperLimit = 50;		// 60
	public double sensorLowerLimit = 0;
	public double sensorUpperLimit = 50;
	static double sensorOffset = 0;
	static double voltageUpperLimit = 5;
	static double voltageLowerLimit = 0;
	
	public static int state = 0;
	
	public WPI_TalonSRX[] elevatorMotors = {
		new WPI_TalonSRX(RobotMap.elevatorA),
		new WPI_TalonSRX(RobotMap.elevatorB),
	};
	
	public DoubleSolenoid elevatorShifters = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.elevatorShifterForward, RobotMap.elevatorShifterReverse);
	public DoubleSolenoid diskBrake = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.diskBrakeForward, RobotMap.diskBrakeReverse);
	
	AnalogInput eP = new AnalogInput(RobotMap.elevatorLinearPot);
	public AnalogPotentiometer elevatorPot = new AnalogPotentiometer(eP, 50, 0);
	
	public Elevator() {
		super("Elevator", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(hieghtLowerLimit, hieghtUpperLimit);
		setOutputRange(-1, 1);
		
		for(int i = 0; i < elevatorMotors.length; i++){
			elevatorMotors[i].configPeakOutputForward(1, 0);
			elevatorMotors[i].configPeakOutputReverse(-1, 0);
			elevatorMotors[i].setNeutralMode(NeutralMode.Brake);
			//elevatorMotors[i].setSafetyEnabled(true);
			//elevatorMotors[i].configContinuousCurrentLimit(40, 0);
			//elevatorMotors[i].configPeakCurrentLimit(80, 0);
			//elevatorMotors[i].configPeakCurrentDuration(100, 0);
		}
		elevatorMotors[1].set(ControlMode.Follower, elevatorMotors[0].getDeviceID());
		
		// Initialize the setpoint to where the elevator starts so it doesn't move
		setSetpoint(getHieght());
		
		// Enable the PIDController
		enable();
		
		// Add this to LiveWindow
		LiveWindow.addChild(this, this);

		elevatorPot.setName("Linear Potentiometer");
		elevatorPot.setSubsystem("Elevator");
        LiveWindow.add(elevatorPot);
        
		elevatorShifters.setName("Shifters");
        elevatorShifters.setSubsystem("Elevator");
        LiveWindow.add(elevatorShifters);
        
        //diskBrake.setName("Disk Brake");
        //diskBrake.setSubsystem("Elevator");
        //LiveWindow.add(diskBrake);
	}
	
	public double getHieght(){
		// TODO Verify this
		return elevatorPot.get();
		//return (elevatorPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	public boolean checkLimits(double value){
		if(value > hieghtLowerLimit && value < hieghtUpperLimit)
			return true;
		else
			return false;
	}
	
	public void setMotorsToBrake(){
		for(int i = 0; i < elevatorMotors.length; i++)
			elevatorMotors[i].setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorsToCoast(){
		for(int i = 0; i < elevatorMotors.length; i++)
			elevatorMotors[i].setNeutralMode(NeutralMode.Coast);
	}

	public void setDirectOutput(double output){
		elevatorMotors[0].set(ControlMode.PercentOutput, output);
	}
	
	public void setElevatorShiftersHigh(){
		elevatorShifters.set(Value.kForward);
	}
	
	public void setElevatorShiftersLow(){
		elevatorShifters.set(Value.kReverse);
	}
	
	public boolean getElevatorShiftersStatus(){
		return elevatorShifters.get() == Value.kForward ? true : false;
	}
	
	public void setDiskBrakeHigh(){
		diskBrake.set(Value.kForward);
	}
	
	public void setDiskBrakeLow(){
		diskBrake.set(Value.kReverse);
	}
	
	public boolean getDiskBrakeStatus(){
		return diskBrake.get() == Value.kForward ? true : false;
	}
	
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getHieght();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		elevatorMotors[0].set(ControlMode.PercentOutput, output);
	}
	
	public void updateSmartDashboard(){
		// Use Shuffleboard to place things in their own tabs
		Shuffleboard.putNumber("Elevator", "Hieght", getHieght());
		Shuffleboard.putNumber("Elevator", "Avg. Pot Voltage", eP.getAverageVoltage());
		Shuffleboard.putNumber("Elevator", "Setpoint", getSetpoint());
		Shuffleboard.putBoolean("Elevator", "Shifters", getElevatorShiftersStatus());
		Shuffleboard.putBoolean("Elevator", "Disk Brake", getDiskBrakeStatus());
		Shuffleboard.putBoolean("Elevator", "PID Enabled", getPIDController().isEnabled());
		
		// Use SmartDashboard to put only the important stuff for drivers
		SmartDashboard.putNumber("Elevator Hieght", getHieght());
		SmartDashboard.putBoolean("Elevator Shifters", getElevatorShiftersStatus());
		SmartDashboard.putBoolean("Disk Brake", getDiskBrakeStatus());
		SmartDashboard.putBoolean("Elevator PID Enabled", getPIDController().isEnabled());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new UpdateElevatorSetpoint());
	}
}

	