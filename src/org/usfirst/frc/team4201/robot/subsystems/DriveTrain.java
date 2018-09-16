package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	
	public WPI_TalonSRX[] driveMotors = {
		new WPI_TalonSRX(RobotMap.driveTrainLeftFront),
		new WPI_TalonSRX(RobotMap.driveTrainRightFront),
		new WPI_TalonSRX(RobotMap.driveTrainLeftRear),
		new WPI_TalonSRX(RobotMap.driveTrainRightRear)
	};
	
	DifferentialDrive robotDrive = new DifferentialDrive(driveMotors[0],driveMotors[1]);

	public DriveTrain() {
		super("DriveTrain");

		//set Motor Controller Control Mode
		driveMotors[0].set(ControlMode.Follower, driveMotors[2].getDeviceID());
		driveMotors[1].set(ControlMode.Follower, driveMotors[3].getDeviceID());
		
        for(int i = 0; i < 4; i++){
        driveMotors[i].configPeakOutputForward(1, 0);
        driveMotors[i].configPeakOutputReverse(-1, 0);
        driveMotors[i].enableVoltageCompensation(true);
        driveMotors[i].configVoltageCompSaturation(12, 0);
        driveMotors[i].setNeutralMode(NeutralMode.Coast);
        }
	}
	
	public void setDirectDriveOutput(double leftOutput, double rightOutput) {
		robotDrive.tankDrive(leftOutput, rightOutput);
	}
	
	public void setArcadeDrive(double leftOutput, double rightOutput) {
		double leftReturn;
	 	double rightReturn;
	 	
	 	leftReturn = leftOutput + rightOutput;
	 	rightReturn = leftOutput - rightOutput;
	 	
	 	if(leftReturn < -1) {
	 		leftReturn = -1;
	 	}
	 	if(leftReturn > 1) {
	 		leftReturn = 1;
	 	}
		if(rightReturn < -1) {
			rightReturn = -1;
		}
	 	if(rightReturn > 1) {
	 		rightReturn = 1;
	 	}
	 	
	 	robotDrive.tankDrive(leftReturn, rightReturn);
	}
	
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
