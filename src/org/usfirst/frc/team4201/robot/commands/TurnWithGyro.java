package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnWithGyro extends Command {
	PIDController pidControl;
	static double kP = 0.1;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0.001;           // Start with I = P / 100
    static double kD = 0;           	// Start with D = P * 10
    static double period = 0.01;
    PIDOutputInterface driveTrainOutput;
    
    
    double setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public TurnWithGyro(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        driveTrainOutput = new PIDOutputInterface();
        pidControl = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, driveTrainOutput, period);
    	pidControl.setName("TurnWithGyro");
    	pidControl.setContinuous(false);
    	pidControl.setAbsoluteTolerance(1);
    	pidControl.setOutputRange(-0.75, 0.75);
        this.setpoint = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pidControl.disable();
    	Robot.driveTrain.spartanGyro.reset();
        stopwatch = new Timer();
    	
    	pidControl.setSetpoint(setpoint);
    	pidControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("PID Output", pidControl.get());
    	SmartDashboard.putNumber("Delta Setpoint", pidControl.getDeltaSetpoint());
    	SmartDashboard.putNumber("Setpoint", pidControl.getSetpoint());
    	SmartDashboard.putBoolean("On Target", pidControl.onTarget());
    	SmartDashboard.putBoolean("Enabled", pidControl.isEnabled());
    	SmartDashboard.putNumber("PIDOutput Value", driveTrainOutput.getPIDOutput());

    	SmartDashboard.putNumber("Stopwatch", stopwatch.get());
    	SmartDashboard.putBoolean("Lock Value: ", lock);
        
    	DriverStation.reportError("PIDOutput Value: " + driveTrainOutput.getPIDOutput(), false);
        Robot.driveTrain.setTankDrive(driveTrainOutput.getPIDOutput(), -driveTrainOutput.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(pidControl.onTarget() && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if(!pidControl.onTarget() && lock){ // When you are outside of range && you are locked
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
