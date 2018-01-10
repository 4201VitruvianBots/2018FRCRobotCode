package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnWithGyro2 extends Command {
	PIDController pidControl;
	static double kP = 0.1;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0.001;           // Start with I = P / 100
    static double kD = 0;           	// Start with D = P * 10
    static double period = 0.01;
    
    double throttle, setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public DriveTurnWithGyro2(double speed, double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        pidControl = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, null, period);
    	pidControl.setName("DriveTurnWithGyro");
    	pidControl.setContinuous();
    	pidControl.setAbsoluteTolerance(0.1);
    	pidControl.setOutputRange(-1, 1);
    	
        this.throttle = speed;
        this.setpoint = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.spartanGyro.reset();
        stopwatch = new Timer();
    	
    	pidControl.setSetpoint(setpoint);
    	pidControl.setEnabled(true);
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
        
        Robot.driveTrain.setDriveOutput(throttle, pidControl.get());
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
