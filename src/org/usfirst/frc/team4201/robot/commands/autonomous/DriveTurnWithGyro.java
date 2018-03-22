package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.PIDOutputInterface;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnWithGyro extends Command {
	PIDController driveGyroPIDController;
	static double kP = 0.08;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0;           // Start with I = P / 100
    static double kD = 0.6;           	// Start with D = P * 10
    static double period = 0.01;
    PIDOutputInterface driveTurnPIDOutput;
    
    
    double setpoint;
    Timer stopwatch;
    boolean lock = false;
	
    public DriveTurnWithGyro(double angle) {
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
        driveTurnPIDOutput = new PIDOutputInterface();
        try {
	        driveGyroPIDController = new PIDController(kP, kI, kD, Robot.driveTrain.spartanGyro, driveTurnPIDOutput, period);
	    	driveGyroPIDController.setName("Drive Gyro PID");
	    	driveGyroPIDController.setSubsystem("Drive Train");
	    	driveGyroPIDController.setAbsoluteTolerance(1.5);
	    	driveGyroPIDController.setOutputRange(-0.8, 0.8);
        } catch (Exception e) {
        	DriverStation.reportError("4201 Error!: SpartanGyro was not detected. Interrupting DriveTurnWithGyro", false);
        	interrupted();
        }
        this.setpoint = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        stopwatch = new Timer();

    	driveGyroPIDController.setSetpoint(setpoint);
    	driveGyroPIDController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Shuffleboard.putNumber("Auto", "PID Output", driveGyroPIDController.get());
    	Shuffleboard.putNumber("Auto", "Delta Setpoint", driveGyroPIDController.getDeltaSetpoint());
    	Shuffleboard.putNumber("Auto", "Setpoint", driveGyroPIDController.getSetpoint());
    	Shuffleboard.putBoolean("Auto", "On Target", driveGyroPIDController.onTarget());
    	Shuffleboard.putBoolean("Auto", "Enabled", driveGyroPIDController.isEnabled());
    	Shuffleboard.putNumber("Auto", "PIDOutput Value", driveTurnPIDOutput.getPIDOutput());

    	Shuffleboard.putNumber("Auto", "Stopwatch", stopwatch.get());
    	Shuffleboard.putBoolean("Auto", "Lock Value: ", lock);
        
    	DriverStation.reportError("PIDOutput Value: " + driveTurnPIDOutput.getPIDOutput(), false);
        Robot.driveTrain.setTankDrive(driveTurnPIDOutput.getPIDOutput(), -driveTurnPIDOutput.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(driveGyroPIDController.onTarget() && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if(!driveGyroPIDController.onTarget() && lock){ // When you are outside of range && you are locked
    		stopwatch.stop();
    		stopwatch.reset();
    		lock = false;
    	}
    	
    	return stopwatch.get() > 0.05; 
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
