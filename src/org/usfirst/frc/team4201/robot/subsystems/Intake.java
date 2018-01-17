package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Intake extends Subsystem {
	
	WPI_TalonSRX[] intakeMotors = {
		new WPI_TalonSRX(RobotMap.intakeLeft),	// TO maintain consistancy, left should always go before right
		new WPI_TalonSRX(RobotMap.intakeRight)
	};
	DifferentialDrive armDrive = new DifferentialDrive((WPI_TalonSRX)intakeMotors[0], (WPI_TalonSRX)intakeMotors[1]);
	//DifferentialDrive robotDrive = new DifferentialDrive(intakeMotors[0], intakeMotors[1]);
	DoubleSolenoid intakeDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.intakeForward, RobotMap.intakeReverse);
	
	public Intake() {
		super("Intake");
		
		// Set right motor to follow left motor
		//intakeMotors[1].set(ControlMode.Follower, intakeMotors[0].getDeviceID());
		
		// Set Motor Controller Feedback Device
		intakeMotors[0].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		intakeMotors[1].configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		
		// Set Motor Controller Peak Output Voltages & Set Motors to Coast
		for(int i = 0; i < intakeMotors.length; i++){	// Changed to intakeMotors.length so it adjusts to array length
			intakeMotors[i].configPeakOutputForward(1, 0);
			intakeMotors[i].configPeakOutputReverse(-1, 0);
			intakeMotors[i].setNeutralMode(NeutralMode.Brake);	// Brake is probably preferred for this game, due to the 1 cube control limit
		}
		//Invert right motor. This will still work if the motor is following another motor
	}
	
	public void setIntakeMotorOutput(double intakeLeftSpeed, double intakeRightSpeed){
		armDrive.tankDrive(intakeLeftSpeed, intakeRightSpeed);
	}
	
	public boolean getIntakePistonStatus() {
		return intakeDeploy.get() == Value.kForward ? true : false;
	}
	
	public void deployIntakePistons() {
		intakeDeploy.set(Value.kForward);
	}
	
	public void retractIntakePistons() {
		intakeDeploy.set(Value.kReverse);
	}
	
	public void updateSmartDashboard(){
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
