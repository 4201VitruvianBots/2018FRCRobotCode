package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Test extends Subsystem {
	
	public BaseMotorController[] testMotors = {
		new WPI_TalonSRX(RobotMap.driveTrainLeftFront)
	};
	
	public Test() {
		super();
        for(int i = 0; i < 1; i++){
        testMotors[i].configPeakOutputForward(1, 0);
        testMotors[i].configPeakOutputReverse(-1, 0);
        testMotors[i].setNeutralMode(NeutralMode.Brake);
        }
	}
	
	public void setTestMotorOutput(double output) {
		testMotors[1].set(ControlMode.PercentOutput, output);
	}
	
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
