package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {
	WPI_TalonSRX conveyor = new WPI_TalonSRX(RobotMap.conveyorMotor);
	WPI_TalonSRX ballIntake = new WPI_TalonSRX(RobotMap.ballIntakeMotor);
	WPI_TalonSRX uptake = new WPI_TalonSRX(RobotMap.shooterUptake);
	WPI_TalonSRX[] shooter = {
			new WPI_TalonSRX(RobotMap.flywheelMaster),	
			new WPI_TalonSRX(RobotMap.flywheelSlave),
	};
		
	WPI_TalonSRX testMotor;
	
	public Shooter(){
		super("Shooter");
		
		//ballIntake.changeControlMode(TalonControlMode.PercentVbus);
		//conveyor.changeControlMode(TalonControlMode.PercentVbus);
		//uptake.changeControlMode(TalonControlMode.PercentVbus);
		//shooter[0].changeControlMode(TalonControlMode.Speed);
		// Set Motor Controller Feedback Device
		shooter[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		shooter[0].setSensorPhase(true);
		shooter[0].config_kP(0, 0.4, 0);
		
		shooter[1].set(ControlMode.Follower, shooter[0].getDeviceID());
		
				
				
		conveyor.setInverted(true); // Tested: This makes it move in the correct direction
	}
	
	public void enableBallIntake(){
		ballIntake.set(ControlMode.PercentOutput, 0.45);
	}
	
	public void disableBallIntake(){
		ballIntake.set(ControlMode.PercentOutput, 0);
	}
	
	public double getBallIntakeOutput(){
		return ballIntake.get();
	}
	
	public void conveyorUptakeOn(){
		conveyor.set(ControlMode.PercentOutput, 0.95);
		uptake.set(ControlMode.PercentOutput, 0.5);
		
	}
	
	public void conveyorUptakeOff(){
		conveyor.set(ControlMode.PercentOutput, 0);
		uptake.set(ControlMode.PercentOutput, 0);
	}
	public double getConveyorOutput(){
		return conveyor.getMotorOutputVoltage();
	}
	
	public void setFlywheelOutput(double RPM){
		shooter[0].set(ControlMode.Velocity, RPM);
	}
	
	public void disableFlywheel(){
		shooter[0].disable();
	}
	
	public double getFlywheelOutput(){
		return shooter[0].get();
	}
	
	public boolean getFlywheelEnable(){
		return shooter[0].get() != 0 ? true : false;
	}
	
	public void setTestMotorOutput(double value){
		ballIntake.set(ControlMode.PercentOutput, value);
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Flywheel Output", getFlywheelOutput());
		SmartDashboard.putNumber("Conveyor Output", getConveyorOutput());
		SmartDashboard.putNumber("Flywheel Status", shooter[0].get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

