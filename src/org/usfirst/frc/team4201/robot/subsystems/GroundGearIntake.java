package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GroundGearIntake extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.gearIntakeMotor);
	//DoubleSolenoid gearIntakePistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonForward, RobotMap.gearIntakePistonReverse);
	DoubleSolenoid gearClampPistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearClampPistonForward, RobotMap.gearClampPistonReverse);
	DoubleSolenoid gearIntakePistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonForward, RobotMap.gearIntakePistonReverse);
	
	public GroundGearIntake() {
		super();

	}
	
	public void deployIntake() {
		gearIntakePistons.set(Value.kForward);
	}
	
	public void retractIntake() {
		gearIntakePistons.set(Value.kReverse);
	}
	
	public Value getIntakeStatus() {
		return gearIntakePistons.get();
	}
	
	public void activateGearIntakeMotors() {
		intakeMotor.set(ControlMode.PercentOutput, -0.8);
	}
	
	public void deactivateGearIntakeMotors() {
		intakeMotor.set(ControlMode.PercentOutput, 0);
	}
	
	public void reverseGearIntakeMotors() {
		intakeMotor.set(0.8);
	}
	
	public void openGearClamp() {
		gearClampPistons.set(Value.kForward);
	}

	public void closeGearClamp() {
		gearClampPistons.set(Value.kReverse);
	}
	
	public Value getIntakeClampStatus() {
		return gearClampPistons.get();
	}
	
	public void updateSmartDashboard() {
		//SmartDashboard.putBoolean("Intake Deployed", getIntakeStatus() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Intake Deployed", getIntakeStatus() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Gear Clamps Open", getIntakeClampStatus() == Value.kForward ? true : false);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

