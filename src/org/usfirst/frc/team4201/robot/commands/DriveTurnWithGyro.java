package org.usfirst.frc.team4201.robot.commands;
import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnWithGyro extends PIDCommand{
    static double kP = 0.1;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0.001;           // Start with I = P / 100
    static double kD = 0;           	// Start with D = P * 10
    static double period = 0.01;
    
    double throttle, setpoint;
    Timer stopwatch;
    boolean lock = false;
    
    public DriveTurnWithGyro(double speed, double angle){
        super("DriveTurnWithGyro", kP, kI, kD, period);
        getPIDController().setContinuous();
        getPIDController().setAbsoluteTolerance(0.1);
        getPIDController().setOutputRange(-1, 1); // Is this okay, or does this need to be an angle to match gyro output?
        //getPIDController().setF(f);
        
        this.throttle = speed;
        this.setpoint = angle;
    }
    
    @Override
    protected double returnPIDInput() {
        // TODO Auto-generated method stub
    	return Robot.driveTrain.spartanGyro.getAngle();
    }
    
    @Override
    protected void usePIDOutput(double output) {
        // TODO Auto-generated method stub
        DriverStation.reportError("Using Output: " + output, false);
        Robot.driveTrain.setDriveOutput(throttle, output);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        // Use this to test/tune PID loop through smartDashboard
    	//SmartDashboard.putNumber("kP", kP);
        //SmartDashboard.putNumber("kI", kI);
        //SmartDashboard.putNumber("kD", kD);
    	
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
                
        Robot.driveTrain.spartanGyro.reset();
        stopwatch = new Timer();

        getPIDController().setSetpoint(setpoint);
        getPIDController().setEnabled(true);
        DriverStation.reportError("Initialized", false);
        
    }
   
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("PID Output", getPIDController().get());
    	SmartDashboard.putNumber("Delta Setpoint", getPIDController().getDeltaSetpoint());
    	SmartDashboard.putNumber("Setpoint", getPIDController().getSetpoint());
    	SmartDashboard.putBoolean("On Target", getPIDController().onTarget());
    	SmartDashboard.putBoolean("Enabled", getPIDController().isEnabled());

    	SmartDashboard.putNumber("Stopwatch", stopwatch.get());
    	SmartDashboard.putBoolean("Lock Value: ", lock);
    	
        DriverStation.reportError("Updating Dashboard", false);
    }
    
    
    @Override
    protected boolean isFinished() {
    	if(getPIDController().onTarget() && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if(!getPIDController().onTarget() && lock){ // When you are outside of range && you are locked
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