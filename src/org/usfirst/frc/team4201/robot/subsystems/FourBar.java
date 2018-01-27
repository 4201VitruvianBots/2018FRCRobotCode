package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FourBar extends Subsystem {

	DoubleSolenoid fourBarPistonsOne = new DoubleSolenoid(RobotMap.PCMOne, RobotMap./*driveTrainShifterForward*/fourBarOneForward, RobotMap./*driveTrainShifterReverse*/fourBarOneReverse);
	DoubleSolenoid fourBarPistonsTwo = new DoubleSolenoid(RobotMap.PCMOne, RobotMap./*driveTrainShifterForward*/fourBarTwoForward, RobotMap./*driveTrainShifterReverse*/fourBarTwoReverse);
	DoubleSolenoid fourBarPistonsThree = new DoubleSolenoid(RobotMap.PCMOne, RobotMap./*driveTrainShifterForward*/fourBarThreeForward, RobotMap./*driveTrainShifterReverse*/fourBarThreeReverse);	
	
	public FourBar() {
		super("FourBar");
		
		
	}
	
	public void fourBarUp(){
		fourBarPistonsOne.set(Value.kForward);
		fourBarPistonsTwo.set(Value.kForward);
		fourBarPistonsThree.set(Value.kForward);
	}
	
	public void fourBarDown(){
		fourBarPistonsOne.set(Value.kReverse);
		fourBarPistonsTwo.set(Value.kReverse);
		fourBarPistonsThree.set(Value.kReverse);
	}
	
	public boolean getFourBarStatus(){
		return fourBarPistonsOne.get() == Value.kForward ? true : false;
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}

	