package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.AdjustElevatorSetpoint;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {
	static double kP = 0.02;		
	static double kI = 0;
	static double kD = 0;
	static double kF = 0;
	static double period = 0.01;

	public double hieghtLowerLimit = 0;	// -33;
	public double hieghtUpperLimit = 50;		// 60
	public double sensorLowerLimit = 0;
	public double sensorUpperLimit = 50;
	static double sensorOffset = 0;
	static double voltageUpperLimit = 5;
	static double voltageLowerLimit = 0;
	
	public BaseMotorController[] elevatorMotors = {
		new WPI_TalonSRX(RobotMap.elevatorA),
		new WPI_TalonSRX(RobotMap.elevatorB),
	};
	
	public DoubleSolenoid elevatorShifters = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.elevatorShifterForward, RobotMap.elevatorShifterReverse);
	public DoubleSolenoid diskBrake = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.diskBrakeForward, RobotMap.diskBrakeReverse);
	public DoubleSolenoid climber = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.climberForward, RobotMap.climberReverse);
	
	public AnalogInput eP = new AnalogInput(RobotMap.elevatorLinearPot);
	public AnalogPotentiometer elevatorPot = new AnalogPotentiometer(eP, 50, 0);
	
	
	public Elevator() {
		super("Arm", kP, kI, kD, kF, period);
		setAbsoluteTolerance(0.5);
		setInputRange(hieghtLowerLimit, hieghtUpperLimit);
		setOutputRange(-1, 1);
		
		for(int i = 0; i < elevatorMotors.length; i++){
			elevatorMotors[i].configPeakOutputForward(1, 0);
			elevatorMotors[i].configPeakOutputReverse(-1, 0);
			elevatorMotors[i].setNeutralMode(NeutralMode.Brake);
		}
		elevatorMotors[1].set(ControlMode.Follower, elevatorMotors[0].getDeviceID());
		
		// Initialize the setpoint to where the elevator starts so it doesn't move
		setSetpoint(getHieght());
		
		// Enable the PIDController
		enable();
		
		LiveWindow.addChild(this, this);
	}
	
	public double getHieght(){
		return elevatorPot.get();
		//return (elevatorPot.getAverageVoltage() * ((sensorUpperLimit - sensorLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + sensorOffset;
	}
	
	public boolean checkLimits(double value){
		if(value > hieghtLowerLimit && value < hieghtUpperLimit)
			return true;
		else
			return false;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Elevator Hieght", getHieght());
		SmartDashboard.putNumber("Elevator Avg. Voltage", eP.getAverageVoltage());
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
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new AdjustElevatorSetpoint());
	}
}

	