package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;
import org.usfirst.frc.team4201.robot.interfaces.CTREPIDSource;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightFusion extends Command{
	
	PIDController throttleControlLeft, throttleControlRight, turnControl;
	double kP = 0.03;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    double kI = 0;           		// Start with I = P / 100
    double kD = 0;           		// Start with D = P * 10
    double period = 0.01;
    CTREPIDSource leftDriveEncoder, rightDriveEncoder;
    PIDOutputInterface PIDThrottleLeft, PIDThrottleRight, PIDTurn;
    
    
    double throttleLeft, throttleRight, setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public DriveStraightFusion(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
        leftDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[0]);
        rightDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[2]);
        PIDThrottleLeft = new PIDOutputInterface();
        PIDThrottleRight = new PIDOutputInterface();
        PIDTurn = new PIDOutputInterface();
        
        throttleControlLeft = new PIDController(kP, kI, kD, leftDriveEncoder, PIDThrottleLeft, period);
        throttleControlLeft.setName("DriveStraightDistanceRight");
        throttleControlLeft.setAbsoluteTolerance(100);
        throttleControlLeft.setOutputRange(-0.5, 0.5);
        
        throttleControlRight = new PIDController(kP, kI, kD, rightDriveEncoder, PIDThrottleRight, period);
        throttleControlRight.setName("DriveStraightDistanceRight");
        throttleControlRight.setAbsoluteTolerance(100);
        throttleControlRight.setOutputRange(-0.5, 0.5);
    	
        turnControl = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, PIDTurn, period);
        turnControl.setName("DriveStraightCorrection");
        turnControl.setAbsoluteTolerance(2);
        turnControl.setOutputRange(-0.2, -0.2);
        
        this.setpoint = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.spartanGyro.reset();
    	Robot.driveTrain.resetEncoders();
        stopwatch = new Timer();
    	
        throttleControlLeft.setSetpoint(setpoint);
        throttleControlRight.setSetpoint(setpoint);
        turnControl.setSetpoint(0);
        
        throttleControlLeft.setEnabled(true);
        throttleControlRight.setEnabled(true);
        turnControl.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*
    	SmartDashboard.putNumber("PID Output", pidControl.get());
    	SmartDashboard.putNumber("Delta Setpoint", pidControl.getDeltaSetpoint());
    	SmartDashboard.putNumber("Setpoint", pidControl.getSetpoint());
    	SmartDashboard.putBoolean("On Target", pidControl.onTarget());
    	SmartDashboard.putBoolean("Enabled", pidControl.isEnabled());
		*/
    	SmartDashboard.putNumber("Left PID Setpoint", throttleControlLeft.getSetpoint());
    	SmartDashboard.putNumber("Right PID Setpoint", throttleControlRight.getSetpoint());
    	SmartDashboard.putNumber("Turn PID Setpoint", turnControl.getSetpoint());
    	SmartDashboard.putNumber("Left PID Output", throttleControlLeft.get());
    	SmartDashboard.putNumber("Right PID Output", throttleControlRight.get());
    	SmartDashboard.putNumber("Turn PID Output", turnControl.get());
    	//SmartDashboard.putNumber("Left PIDS Output", PIDThrottleLeft.getPIDOutput());
    	//SmartDashboard.putNumber("Right PIDS Output", PIDThrottleRight.getPIDOutput());
    	//SmartDashboard.putNumber("Turn PIDS Output", PIDTurn.getPIDOutput());
    	//SmartDashboard.putBoolean("Lock Value: ", lock);
    	
    	//Robot.driveTrain.PIDDrive(PIDThrottleLeft.getPIDOutput(), PIDThrottleRight.getPIDOutput());
        Robot.driveTrain.PIDDrive(PIDThrottleLeft.getPIDOutput() + PIDTurn.getPIDOutput(), PIDThrottleRight.getPIDOutput() - PIDTurn.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(throttleControlLeft.onTarget() && throttleControlRight.onTarget() && turnControl.onTarget() && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if((!throttleControlLeft.onTarget() || !throttleControlRight.onTarget() || !turnControl.onTarget()) && lock){ // When you are outside of range && you are locked
    		stopwatch.stop();
    		stopwatch.reset();
    		lock = false;
    	}
    	
    	return stopwatch.get() > 1; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setDriveOutput(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
