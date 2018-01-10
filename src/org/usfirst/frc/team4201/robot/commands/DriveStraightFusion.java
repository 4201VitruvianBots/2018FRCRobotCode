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
public class DriveStraightFusion extends Command {
	PIDController throttleControl, turnControl;
	static double kP = 0.1;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0.001;           // Start with I = P / 100
    static double kD = 0;           	// Start with D = P * 10
    static double period = 0.01;
    PIDOutputInterface PIDThrottle, PIDTurn;
    
    
    double throttle, setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public DriveStraightFusion(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        throttleControl = new PIDController(kP, kI, kD, Robot.driveTrain.encoders, PIDThrottle, period);
        throttleControl.setName("DriveStraightDistance");
        throttleControl.setContinuous();
        throttleControl.setAbsoluteTolerance(0.1);
        throttleControl.setOutputRange(-1, 1);
    	
        turnControl = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, PIDTurn, period);
        turnControl.setName("DriveStraightCorrection");
        turnControl.setContinuous();
        turnControl.setAbsoluteTolerance(0.1);
        turnControl.setOutputRange(-1, 1);
        
        this.setpoint = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.spartanGyro.reset();
        stopwatch = new Timer();
    	
        throttleControl.setSetpoint(setpoint);
        throttleControl.setEnabled(true);
        turnControl.setSetpoint(0);
        turnControl.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("PID Output", pidControl.get());
    	SmartDashboard.putNumber("Delta Setpoint", pidControl.getDeltaSetpoint());
    	SmartDashboard.putNumber("Setpoint", pidControl.getSetpoint());
    	SmartDashboard.putBoolean("On Target", pidControl.onTarget());
    	SmartDashboard.putBoolean("Enabled", pidControl.isEnabled());

    	SmartDashboard.putNumber("Stopwatch", stopwatch.get());
    	SmartDashboard.putBoolean("Lock Value: ", lock);

        DriverStation.reportError("Updating Dashboard", false);
        
        Robot.driveTrain.setDriveOutput(PIDThrottle.getPIDOutput(), PIDTurn.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(throttleControl.onTarget() && turnControl.onTarget() && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if((!throttleControl.onTarget() || !turnControl.onTarget()) && lock){ // When you are outside of range && you are locked
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
