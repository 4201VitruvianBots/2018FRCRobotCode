package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDTuner extends Subsystem {
	PIDController controller;
	
	public PIDTuner(){
		super("PIDTuner");
		controller = Robot.arm.getPIDController();
		
		//SmartDashboard.putData(controller);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void setSetpoint(){
        controller.setSetpoint(SmartDashboard.getNumber("Setpoint", controller.getSetpoint()));
	}
	
	public void updatePIDValues(){
    	//SmartDashboard.putNumber("Setpoint", controller.getSetpoint());
    	//SmartDashboard.putNumber("kP", controller.getP());
    	//SmartDashboard.putNumber("kI", controller.getI());
    	//SmartDashboard.putNumber("kD", controller.getD());
    	//SmartDashboard.putNumber("kF", controller.getF());
        
        
        controller.setP(SmartDashboard.getNumber("kP", 0));
        controller.setI(SmartDashboard.getNumber("kI", 0));
        controller.setD(SmartDashboard.getNumber("kD", 0));
        controller.setF(SmartDashboard.getNumber("kF", 0));
	}
	
	public void updateSmartDashboard(){
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

