package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.SetArmSetpoint;
import org.usfirst.frc.team4201.robot.commands.SetArmWristPosition;
import org.usfirst.frc.team4201.robot.commands.SetWristSetpoint;
import org.usfirst.frc.team4201.robot.interfaces.AnalogPotentiometerSource;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JointedArm extends Subsystem{

	
	public BaseMotorController[] armMotors = {
		new WPI_TalonSRX(RobotMap.armMotor),
	};
	
	public WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.wristMotor);
	
	public AnalogInput armPot = new AnalogInput(RobotMap.armPot);
	public AnalogInput wristPot = new AnalogInput(RobotMap.wristPot);
	
	public PIDController armPIDController, wristPIDController;
	AnalogPotentiometerSource armSource, wristSource;
	PIDOutputInterface armPIDOutput, wristPIDOutput;
	
	double armSetpoint, wristSetpoint;
	
	
	
	
	// Absoulte Limits are physical limits, soft limits are used to constrain intake to extension limit
	public double wristForwardAbsoluteLimit = -65, 	//69,	//-65 
				  wristReverseAbsoluteLimit = -135,	//-123, //-135
				  armForwardAbsoluteLimit = -42,	//90, 	// -42
				  armReverseAbsoluteLimit = -150;	// -40.8;	// -150
	public double wristForwardVoltage = 4.52880813,
				  wristReverseVoltage = 1.508788908,
				  armForwardVoltage = 2.471923575,
				  armReverseVoltage = 0.690917898;

	public double wristForwardSoftLimit;
	
	//DoubleSolenoid leftArm = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmOne, RobotMap.leftArmTwo);
	//DoubleSolenoid rightArm = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmOne, RobotMap.rightArmTwo);
	/*
	public JointedArm() {
		super("Arm");
		
		//armSource = new AnalogPotentiometerSource(armPot, 0);
		//wristSource = new AnalogPotentiometerSource(wristPot, 1);
		armPIDOutput = new PIDOutputInterface();
		wristPIDOutput = new PIDOutputInterface();
		
		// 330 values
		armPIDController = new PIDController(0.06, 0, 0, armSource, armPIDOutput, 0.01);
		armPIDController.setAbsoluteTolerance(0.5);
		armPIDController.setOutputRange(-1, 1);
		//armPIDController.setInputRange(armReverseAbsoluteLimit, armForwardAbsoluteLimit);
		
		wristPIDController = new PIDController(0.045, 0, 0, wristSource, wristPIDOutput, 0.01);
		wristPIDController.setAbsoluteTolerance(0.5);
		wristPIDController.setOutputRange(-1, 1);
		//wristPIDController.setInputRange(wristReverseAbsoluteLimit, wristForwardAbsoluteLimit);
		
		for(int i = 0; i < armMotors.length; i++){
			armMotors[i].configPeakOutputForward(1, 0);
			armMotors[i].configPeakOutputReverse(-1, 0);
			armMotors[i].setNeutralMode(NeutralMode.Coast);
		}
		
		armMotors[1].set(ControlMode.Follower, armMotors[0].getDeviceID());
		wristMotor.configPeakOutputForward(1, 0);    
		wristMotor.configPeakOutputReverse(-1, 0);   
		wristMotor.setNeutralMode(NeutralMode.Coast);	
	}
	
	public void initalizeSetpoints(){
		armPIDController.setSetpoint(getArmAngle());
		wristPIDController.setSetpoint(getWristAngle());
		armSetpoint = armPIDController.getSetpoint();
		wristSetpoint = wristPIDController.getSetpoint();
		armPIDController.enable();
		wristPIDController.enable();
	}
	
	public double getArmAngle() {
		
		double angleFromMast;
		//double sensorRange = getArmVertical() - getArmFrontLimit();		
    	double sensorRange = 3 - 1;		// What are these constants?
    	//double angleRange  = ArmPos.verticalCalAngle - ArmPos.frontCalAngle;
    	double angleRange  = 180 - 49;
    	
    	angleFromMast = (armPot.getAverageVoltage() - 3) * (angleRange/sensorRange);
	
    	return angleFromMast;
		//double angleFromHorizon = Robot.mast.getMastAngle() + angleFromMast;
	    //return angleFromHorizon;
	    
		//return armPot.getAverageVoltage() * (armForwardAbsoluteLimit - armReverseAbsoluteLimit) / (armForwardVoltage - armReverseVoltage);
    }
	
	public double getAngleFromArm() {
		
    	// Using defaults
		//double sensorRange = getHandRearLimit() - getHandFrontLimit();
    	double sensorRange = 5 - 0;
    	//double angleRange = HandConst.rearLimitAngle - HandConst.frontLimitAngle;
    	double angleRange = 285 - 72;
    	
    	double angleFromArm = angleRange/sensorRange * (wristPot.getAverageVoltage()- 0) + 72;
    	return angleFromArm;
    	
	}
	
	public double getWristAngle() {
		return -(180 - getAngleFromArm() - getArmAngle() - 3.5);
		//return wristPot.getAverageVoltage() * (wristForwardAbsoluteLimit - wristReverseAbsoluteLimit) / (wristForwardVoltage - wristReverseVoltage);
    }
	
	public void setArmSetpoint(double number) {
		armSetpoint += number;
		// Uncomment for absolute wrist position
		//wristSetpoint += armSetpoint + number;
	}
	
	public void setWristSetpoint(double number) {
		wristSetpoint += number;
	}
	
	public void updateArmSetpoint() {
		armPIDController.setSetpoint(armSetpoint);
		armMotors[0].set(ControlMode.PercentOutput, armPIDOutput.getPIDOutput());
		// Uncomment for absolute wrist position
		//wristPIDController.setSetpoint(wristPIDController.getSetpoint() - number);
	}
	
	public void updateWristSetpoint() {
		wristPIDController.setSetpoint(wristSetpoint);
		wristMotor.set(ControlMode.PercentOutput, wristPIDOutput.getPIDOutput());
	}
	
	public void updateArmWristPositions() {
		// This will be called periodically during TeleOp.
		// This function limits wrist positions based on arm position
		
		// Set wrist limits
		if(getArmAngle() > 45 || getArmAngle() < (180 - 45) ) {
			//wristForwardSoftLimit = array[Math.ceil(getArmAngle() - 45)];
		}
		
		// Set wrist to a set angle
		// if the wrist is not limited, move to intake position, otherwise rumble to tell operator that movement is invalid
		if(Robot.oi.xBoxButtons[1].get()) {
			// wristSetpoint = -armSetpoint 
			//setWristSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[2].get())
			while(Robot.oi.xBoxButtons[1].get()) {
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		
		// if the wrist is not limited, move to intake position, otherwise rumble to tell operator that movement is invalid
		/*
		if(RobotMap.armState == 0 && Robot.oi.xBoxButtons[2].get()) { // Need another qualifier
			// wristSetpoint = -armSetpoint + offset1 
			setWristSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[2].get())
			while(Robot.oi.xBoxButtons[2].get()) {
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		
		// if the wrist is not limited, move to intake position, otherwise rumble to tell operator that movement is invalid
		if(RobotMap.armState == 0 && Robot.oi.xBoxButtons[3].get()) { // Need another qualifier
			// wristSetpoint = armSetpoint + offset2
			if(wristSetpoint > wristForwardSoftLimit)
			setWristSetpoint(wristSetpoint);
		} else if(Robot.oi.xBoxButtons[3].get())
			while(Robot.oi.xBoxButtons[3].get()) {
				Robot.oi.enableXBoxLeftRumble();
			Robot.oi.disableXBoxLeftRumble();
		}
		
		// Commands declared here to avoid issues
		// Only start a new command if the queue isn't overloaded
		//Robot.oi.xBoxButtons[4].whileHeld(new SetArmSetpoint(1));			// Left Button: Adjust arm up
        //Robot.oi.xBoxLeftTrigger.whileHeld(new SetArmSetpoint(-1));			// Left Trigger: Adjust arm down
        //Robot.oi.xBoxButtons[5].whileHeld(new SetWristSetpoint(1));			// Right Button: Adjust wrist up
        //Robot.oi.xBoxRightTrigger.whileHeld(new SetWristSetpoint(-1));		// Right Trigger: Adjust wrist down
		
		// Called periodically in this loop to maintain its position
		updateWristSetpoint();
		updateArmSetpoint();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Arm Angle", getArmAngle());
		SmartDashboard.putNumber("Arm Pot", armPot.getAverageVoltage());
		SmartDashboard.putNumber("Wrist Angle", getWristAngle());
		SmartDashboard.putNumber("Wrist Pot", wristPot.getAverageVoltage());
		SmartDashboard.putNumber("Wrist Setpoint", wristPIDController.getSetpoint());
		//SmartDashboard.putNumber("Wrist Command Count", wristCommandCount);
		
		
	}
	*/
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new SetArmWristPosition());
	}
	
}
