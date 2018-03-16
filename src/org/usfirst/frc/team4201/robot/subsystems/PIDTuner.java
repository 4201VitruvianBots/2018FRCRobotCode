package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**	This is a test subsystem to tune PID Values.
 *	We made this because LiveWindow on Shuffleboard exhibits some weird behaviors.
 */
public class PIDTuner extends Subsystem {
	PIDController controller;
	
	public PIDTuner(){
		super("PIDTuner");
		//controller = Robot.arm.getPIDController();
		
		//SmartDashboard.putData(controller);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void updatePIDValues(){
        controller.setSetpoint(Shuffleboard.getNumber("PIDTuner",  "Setpoint", controller.getSetpoint()));
        controller.setP(Shuffleboard.getNumber("PIDTuner", "kP", controller.getP()));
        controller.setI(Shuffleboard.getNumber("PIDTuner", "kI", controller.getI()));
        controller.setD(Shuffleboard.getNumber("PIDTuner", "kD", controller.getD()));
        controller.setF(Shuffleboard.getNumber("PIDTuner", "kF", controller.get()));
	}
	
	public void initializeSmartDashboard(){
		Shuffleboard.putNumber("PIDTuner", "kP", controller.getP());
		Shuffleboard.putNumber("PIDTuner", "kI", controller.getI());
		Shuffleboard.putNumber("PIDTuner", "kD", controller.getD());
		Shuffleboard.putNumber("PIDTuner", "kF", controller.getF());
	}
	
	public void updateSmartDashboard(){
		Shuffleboard.putBoolean("PIDTuner", "Output", controller.onTarget());
		Shuffleboard.putNumber("PIDTuner", "Output", controller.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

