package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;
import org.usfirst.frc.team4201.robot.interfaces.CTREPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightFusion extends Command{
	PIDController leftMotorPIDController, rightMotorPIDController, driveGyroPIDController;
	double kP = 0.08;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    double kI = 0.002;           	// Start with I = P / 100  (speeds you up when you have an error for a period of time)
    double kD = 0.5;         		// Start with D = P * 10.  (slows you down as you get closer to the target)
    double period = 0.01;
    CTREPIDSource leftDriveEncoder, rightDriveEncoder;
    PIDOutputInterface leftMotorPIDOutput, rightMotorPIDOutput, driveTurnPIDOutput;
    
    
    double throttleLeft, throttleRight, setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public DriveStraightFusion(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        /*
        if(!SmartDashboard.containsKey("kP"))
        	SmartDashboard.putNumber("kP", kP);
        if(!SmartDashboard.containsKey("kI"))
        	SmartDashboard.putNumber("kI", kI);
        if(!SmartDashboard.containsKey("kD"))
        	SmartDashboard.putNumber("kD", kD);
        
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
        */
        leftDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[0]);
        rightDriveEncoder = new CTREPIDSource(Robot.driveTrain.driveMotors[2]);
        leftMotorPIDOutput = new PIDOutputInterface();
        rightMotorPIDOutput = new PIDOutputInterface();
        driveTurnPIDOutput = new PIDOutputInterface();
        
        leftMotorPIDController = new PIDController(kP, kI, kD, leftDriveEncoder, leftMotorPIDOutput, period);
        leftMotorPIDController.setName("Left Motor PID");
        leftMotorPIDController.setSubsystem("Drive Train");
        leftMotorPIDController.setAbsoluteTolerance(100);
        leftMotorPIDController.setOutputRange(-0.5, 0.5);
        
        rightMotorPIDController = new PIDController(kP, kI, kD, rightDriveEncoder, rightMotorPIDOutput, period);
        rightMotorPIDController.setName("Right Motor PID");
        rightMotorPIDController.setSubsystem("Drive Train");
        rightMotorPIDController.setAbsoluteTolerance(100);
        rightMotorPIDController.setOutputRange(-0.5, 0.5);
        
    	try {
	        driveGyroPIDController = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, driveTurnPIDOutput, period);
	        driveGyroPIDController.setName("Drive Gyro PID");
	    	driveGyroPIDController.setSubsystem("Drive Train");
	        driveGyroPIDController.setAbsoluteTolerance(2);
	        driveGyroPIDController.setOutputRange(-0.2, 0.2);
    	} catch (Exception e){
    		
    	}
        this.setpoint = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        stopwatch = new Timer();
    	
        leftMotorPIDController.setSetpoint(setpoint);
        rightMotorPIDController.setSetpoint(setpoint);
        leftMotorPIDController.setEnabled(true);
        rightMotorPIDController.setEnabled(true);
        
        try {
        	driveGyroPIDController.setSetpoint(Robot.driveTrain.spartanGyro.getAngle());
            driveGyroPIDController.setEnabled(true);
        } catch (Exception e) {
        	
        }
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
    	//SmartDashboard.putNumber("Left PID Setpoint", leftMotorPIDController.getSetpoint());
    	//SmartDashboard.putNumber("Right PID Setpoint", rightMotorPIDController.getSetpoint());
    	//SmartDashboard.putNumber("Turn PID Setpoint", driveGyroPIDController.getSetpoint());
    	//SmartDashboard.putNumber("Left PID Output", leftMotorPIDController.get());
    	//SmartDashboard.putNumber("Right PID Output", rightMotorPIDController.get());
    	//SmartDashboard.putNumber("Turn PID Output", driveGyroPIDController.get());
    	//SmartDashboard.putNumber("Left PIDS Output", PIDThrottleLeft.getPIDOutput());
    	//SmartDashboard.putNumber("Right PIDS Output", PIDThrottleRight.getPIDOutput());
    	//SmartDashboard.putNumber("Turn PIDS Output", PIDTurn.getPIDOutput());
    	//SmartDashboard.putBoolean("Lock Value: ", lock);
    	
    	//Robot.driveTrain.PIDDrive(PIDThrottleLeft.getPIDOutput(), PIDThrottleRight.getPIDOutput());
    	try {
    		Robot.driveTrain.PIDDrive(leftMotorPIDOutput.getPIDOutput() + driveTurnPIDOutput.getPIDOutput(), rightMotorPIDOutput.getPIDOutput() - driveTurnPIDOutput.getPIDOutput());
    	} catch(Exception e) {
            Robot.driveTrain.PIDDrive(leftMotorPIDOutput.getPIDOutput(), rightMotorPIDOutput.getPIDOutput());
    	}
    	//Robot.driveTrain.PIDDrive(leftMotorPIDOutput.getPIDOutput(), rightMotorPIDOutput.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	try {
	    	if(leftMotorPIDController.onTarget() && rightMotorPIDController.onTarget() && driveGyroPIDController.onTarget() && !lock) { // When you are in range && you are not locked
	    		stopwatch.start();
	    		lock = true;
	    	} else if((!leftMotorPIDController.onTarget() || !rightMotorPIDController.onTarget() || !driveGyroPIDController.onTarget()) && lock){ // When you are outside of range && you are locked
	    		stopwatch.stop();
	    		stopwatch.reset();
	    		lock = false;
	    	}
    	} catch (Exception e) {
    		if(leftMotorPIDController.onTarget() && rightMotorPIDController.onTarget() && !lock) { // When you are in range && you are not locked
	    		stopwatch.start();
	    		lock = true;
	    	} else if((!leftMotorPIDController.onTarget() || !rightMotorPIDController.onTarget()) && lock){ // When you are outside of range && you are locked
	    		stopwatch.stop();
	    		stopwatch.reset();
	    		lock = false;
	    	}
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
