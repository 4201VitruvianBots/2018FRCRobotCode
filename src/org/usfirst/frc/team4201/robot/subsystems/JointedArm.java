package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.SetArmWristPosition;
import org.usfirst.frc.team4201.robot.commands.SetSplitArcadeDrive;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;
import org.usfirst.frc330.Robot;
import org.usfirst.frc330.constants.ArmPos;
import org.usfirst.frc330.constants.HandConst;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JointedArm extends Subsystem{
	
	public BaseMotorController[] armAndWristMotors = {
		new WPI_TalonSRX(RobotMap.armLeftMotor),
		new WPI_TalonSRX(RobotMap.armRightMotor),
		new WPI_TalonSRX(RobotMap.wristMotor),
	};
	
	public AnalogInput armPot = new AnalogInput(RobotMap.armPot);
	public AnalogInput wristPot = new AnalogInput(RobotMap.wristPot);
	
	PIDController armPIDController, wristPIDController;
	PIDOutputInterface armPIDOutput, wristPIDOutput;
	
	double armSetpoint, wristSetpoint;
	
	// Absoulte Limits are physical limits, soft limits are used to constrain intake to extension limit
	double wristForwardAbsoluteLimit, wristReverseAbsoluteLimit, armForwardAbsoluteLimit, armReverseAbsoluteLimit;
	double wristForwardSoftLimit;
	
	//DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmOne, RobotMap.leftArmTwo);
	//DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmOne, RobotMap.rightArmTwo);
	
	public JointedArm() {
		super("Arm");
		
		// 330 values
		armPIDController = new PIDController(0.06, 0, 0, armPot, armPIDOutput, 0.01);
		armPIDController.setAbsoluteTolerance(0.5);
		//armPIDController.setOutputRange(armReverseAbsoluteLimit, armForwardAbsoluteLimit);
		
		wristPIDController = new PIDController(0.045, 0, 0, wristPot, wristPIDOutput, 0.01);
		wristPIDController.setAbsoluteTolerance(0.5);
		//wristPIDController.setOutputRange(wristForwardAbsoluteLimit, wristReverseAbsoluteLimit);
		
		for(int i = 0; i < 2; i++){
			armAndWristMotors[i].configPeakOutputForward(1, 0);
			armAndWristMotors[i].configPeakOutputReverse(-1, 0);
			armAndWristMotors[i].setNeutralMode(NeutralMode.Brake);
		}
		armPIDController.setSetpoint(getArmAngle());
		wristPIDController.setSetpoint(getWristAngle());
		armPIDController.enable();
		wristPIDController.enable();
	}
	
	public double getArmAngle()
    {
		double angleFromMast;
		//double sensorRange = getArmVertical() - getArmFrontLimit();		
    	double sensorRange = 3 - 1;		// What are these constants?
    	//double angleRange  = ArmPos.verticalCalAngle - ArmPos.frontCalAngle;
    	double angleRange  = 180 - 49;
    	
    	angleFromMast = (armPot.getAverageVoltage() - 3) * (angleRange/sensorRange);
	
    	return angleFromMast;
		//double angleFromHorizon = Robot.mast.getMastAngle() + angleFromMast;
	    //return angleFromHorizon;
    }
	
	public double getAngleFromArm(){
    	// Using defaults
		//double sensorRange = getHandRearLimit() - getHandFrontLimit();
    	double sensorRange = 5 - 0;
    	//double angleRange = HandConst.rearLimitAngle - HandConst.frontLimitAngle;
    	double angleRange = 285 - 72;
    	
    	double angleFromArm = angleRange/sensorRange * (wristPot.getAverageVoltage()- 0) + 72;
    	return angleFromArm;
	}
	
	public double getWristAngle()
    {
		return -(180 - getAngleFromArm() - getArmAngle() - 3.5);
    }
	
	public void setArmSetpoint(double number) {
		armPIDController.setSetpoint(armPIDController.getSetpoint() + number);
	}
	public void setWristSetpoint(double number) {
		wristPIDController.setSetpoint(wristPIDController.getSetpoint() + number);
	}
	
	public void updateArmState(){
		// This will be called periodically during TeleOp to determine arm/wrist limits
		
		
		if(getArmAngle() > 45 || getArmAngle() < (180 - 45) )
			RobotMap.armState = 1;
		else
			RobotMap.armState = 0;
		
		
		// Limit wrist movement based on arm angle
		switch(RobotMap.armState){
			case 0:
				wristForwardSoftLimit = 0;
				break;
			case 1:
				// Some complex trig function to adjust the forward soft limit. May need try/catch to avoid a div/0 error
				// Math.cos(((43.5 - (Math.sin(50 * Math.PI/ 180) * 43)) / 12) * Math.PI/ 180)
				//wristForwardSoftLimit = 
				wristPIDController.setOutputRange(wristForwardAbsoluteLimit, wristReverseAbsoluteLimit);
				break;
		}
		
		// if the wrist is not limited, move to intake position, otherwise rumble to tell operator that movement is invalid
		if(RobotMap.armState == 0 && Robot.oi.xBoxButtons[1].get()) {
			// wristSetpoint = 
			wristPIDController.setSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[2].get())
			while(Robot.oi.xBoxButtons[1].get()) {
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		
		// if the wrist is not limited, move to intake position, otherwise rumble to tell operator that movement is invalid
		if(RobotMap.armState == 0 && Robot.oi.xBoxButtons[2].get()) { // Need another qualifier
			// wristSetpoint = 
			wristPIDController.setSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[2].get())
			while(Robot.oi.xBoxButtons[2].get()) {
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		
		// adjust wrist angle from right trigger/button
		if(Robot.oi.xBoxButtons[5].get() && wristSetpoint + 1 < (wristForwardSoftLimit + wristForwardAbsoluteLimit)) {
			wristSetpoint += 1;
			wristPIDController.setSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[5].get()) {
			while(Robot.oi.xBoxButtons[5].get())
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		if(Robot.oi.xBoxButtons[5].get() && wristSetpoint - 1 > wristReverseAbsoluteLimit) {
			wristSetpoint -= 1;
			wristPIDController.setSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[7].get()) {
			while(Robot.oi.xBoxButtons[7].get())
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
			
		// adjust arm angle from left trigger/button
		if(Robot.oi.xBoxButtons[4].get() && armSetpoint + 1 < armForwardAbsoluteLimit) {
			armSetpoint += 1;
			armPIDController.setSetpoint(armSetpoint);
		} else if(Robot.oi.xBoxButtons[4].get()) {
			while(Robot.oi.xBoxButtons[4].get())
				Robot.oi.enableXBoxRightRumble();
			Robot.oi.disableXBoxRightRumble();
		}
		if(Robot.oi.xBoxButtons[6].get() && armSetpoint - 1 > armReverseAbsoluteLimit) {
			armSetpoint -= 1;
			armPIDController.setI(armSetpoint);
		} else if(Robot.oi.xBoxButtons[6].get()) {
			while(Robot.oi.xBoxButtons[6].get())
				Robot.oi.enableXBoxRightRumble();
			Robot.oi.disableXBoxRightRumble();
		}
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Arm Angle", getArmAngle());
		SmartDashboard.putNumber("Wrist Pot", getWristAngle());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		//setDefaultCommand(new SetArmWristPosition());
	}
	
}
