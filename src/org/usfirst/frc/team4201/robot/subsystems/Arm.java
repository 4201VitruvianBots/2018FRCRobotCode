package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.LUTs;
import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.ManualArmControl;
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
	
	public static double kP = 0.2;		// Test values for Triple Threat
	public static double kI = 0;
	public static double kD = 0;
	public static double kF = 0;
	public static double period = 0.01;

	public static double kPUp = 0.2;	
	public static double kDUp = kD;
	public static double kPDown = kPUp;
	public static double kDDown = kD;
	
	public double angleLowerLimit = -61;																									// 1.5		
	public double angleUpperLimit = 61;		// Physical limit is closer to 64, but 62 is to prevent DART from getting stuck at max extension	//11.5;		
	public double angleOffset = 80;		
	public double sensorLowerLimit = 0;																														
	public double sensorUpperLimit = 105;																														
	static double sensorOffset = -75.5;																														
	static double voltageLowerLimit = 0;
	static double voltageUpperLimit = 4.5;
	
	
	double previousAngle = -60;
	double previousArmOutput = 0;
	
	public static boolean lock = false;
	
	public WPI_TalonSRX[] armMotors = {
		new WPI_TalonSRX(RobotMap.armMotor),
		//new WPI_TalonSRX(RobotMap.armMotorB)
		//new WPI_TalonSRX(RobotMap.armMotor),
		//new WPI_TalonSRX(RobotMap.armMotor + 1); // Using test arm
	};
	
	AnalogInput aP = new AnalogInput(RobotMap.armPot);
	public AnalogPotentiometer armPot = new AnalogPotentiometer(aP, sensorUpperLimit, sensorOffset);	// 360, -91: Triple Threat
																										// Dart actuator physically moves 12 inches, but pot values goes only up to ~3.5v.
																										// Using rations 12:3.5v = ~17.15:5v
	public Arm() {
		super("Arm", kP, kI, kD, kF, period);
		setAbsoluteTolerance(1.5);
		setInputRange(angleLowerLimit, angleUpperLimit);
		setOutputRange(-0.75, 1);
		
		for(int i = 0; i < armMotors.length; i++) {
			armMotors[i].setNeutralMode(NeutralMode.Brake);
			armMotors[i].configPeakOutputForward(1, 0);
			armMotors[i].configPeakOutputReverse(-1, 0);
			//armMotors[i].setInverted(true);
			//armMotors[i].setSafetyEnabled(true);
			//armMotors[i].configContinuousCurrentLimit(40, 0);
			//armMotors[i].configPeakCurrentLimit(80, 0);
			//armMotors[i].configPeakCurrentDuration(100, 0);
		}
		//armMotors[1].set(ControlMode.Follower, armMotors[0].getDeviceID());
		//armMotors[1].setInverted(true);
		
		// Initialize the setpoint to where the wrist starts so it doesn't move
		setSetpoint(getAngle());

		// Enable the PIDController if state == 0
		if(RobotMap.ArmState == 0)
			enable();
		
		LiveWindow.addChild(this, this);
		
		armPot.setName("Potentiometer");
		armPot.setSubsystem("Arm");
        LiveWindow.add(armPot);
        
        armMotors[0].setName("Arm Motor");
        armMotors[0].setSubsystem("Arm");
        LiveWindow.add(armMotors[0]);
	}
	
	public double getDARTHieght() {
		return (aP.getAverageVoltage() * ((11.125)/(3.98)));		// Using DART Actuator values. 4.5 Practice, 3.5 Comp???. Double check this with real values
	}
	
	public double getAngle() {
		try { 
			previousAngle = LUTs.armAngle[(int)Math.round((aP.getAverageVoltage()) * 50)];	// Need a try/catch to avoid rounding to a value outside of 0-5v
			return previousAngle;
		} catch(Exception e) {
			return previousAngle;
		} //*/
		//return armPot.get();
		//return getDARTHieght() * (115 / 10.5) - angleOffset;
		//return 1.2113 * (aP.getAverageValue() * aP.getAverageValue() * aP.getAverageValue()) - 9.2787 * (aP.getAverageValue() * aP.getAverageVoltage()) + 47.7672 * aP.getAverageVoltage() - 84.6061;
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
	
	public void manualArmControl() {
		double yAxis = -Robot.oi.xBoxController.getRawAxis(1);
		
		double alpha = 0.875;
		
		// Low-Pass Filter
		double armOutput = alpha * yAxis + previousArmOutput * (1-alpha);
		
		//if(Robot.arm.getAngle() > Robot.arm.angleUpperLimit)
		//	Robot.arm.setSetpoint(Robot.arm.angleUpperLimit - 2);
		//else 

		if (RobotMap.ArmState != 0)
			if (Math.abs(yAxis) > 0.05)
				if(yAxis > 0)
					setDirectOutput(armOutput);
				else
					setDirectOutput(armOutput * 0.75);
			else
				setDirectOutput(0);
		else if(Math.abs(yAxis) > 0.05 && !lock) {
	    	disable();
			if(RobotMap.ArmState != 0)
				setDirectOutput(armOutput);
			// Check if new setpoint deosn't violate limits before setting
			else if(Robot.arm.checkLimits(Robot.arm.getAngle() + armOutput)){	
 				
 				if(Robot.arm.getAngle() < -45 && armOutput < 0)
 					setDirectOutput(Math.max(armOutput, -0.4));
 				else if (armOutput < 0) 
 					setDirectOutput(armOutput * 0.75);
				else
 					setDirectOutput(armOutput);
 				
	    	} else {
				// Haptic feedback for operator
		        Robot.oi.enableXBoxLeftRumbleTimed();
			}
		} else if(!getPIDController().isEnabled() && RobotMap.ArmState == 0) {
			setSetpoint(returnPIDInput());
			enable();
		}
		
			
		previousArmOutput = armOutput;
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
		Shuffleboard.putNumber("Arm", "DART Hieght", getDARTHieght());
		Shuffleboard.putNumber("Arm", "Angle", getAngle());
		Shuffleboard.putNumber("Arm", "Pot Avg. Voltage", aP.getAverageVoltage());
		Shuffleboard.putNumber("Arm", "Setpoint", getSetpoint());
		Shuffleboard.putNumber("Arm", "Motor Output", armMotors[0].get());
		Shuffleboard.putNumber("Arm", "PID Output", getPIDController().get());
		Shuffleboard.putBoolean("Arm", "PID Enabled", getPIDController().isEnabled());
		Shuffleboard.putNumber("Arm", "kP", getPIDController().getP());
		Shuffleboard.putNumber("Arm", "kI", getPIDController().getI());
		Shuffleboard.putNumber("Arm", "kD", getPIDController().getD());
		Shuffleboard.putNumber("Arm", "PID Output", getPIDController().get());
		
		// For TripleThreat Testbed
		//Shuffleboard.putNumber("Triple Threat", "Arm Angle", getAngle());
		//Shuffleboard.putNumber("Triple Threat", "Arm Pot Test", armPot.get());
		//Shuffleboard.putNumber("Triple Threat", "Arm Avg. Voltage", aP.getAverageVoltage());
		
		// Use SmartDashboard to put only the important stuff for drivers;
		SmartDashboard.putNumber("Arm Angle", getAngle());
		SmartDashboard.putNumber("Arm Pot Voltage", aP.getAverageVoltage());
		SmartDashboard.putBoolean("Arm PID Enabled", getPIDController().isEnabled());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new ManualArmControl());
	}
}
