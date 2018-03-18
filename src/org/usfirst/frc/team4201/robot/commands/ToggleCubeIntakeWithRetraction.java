package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Intake;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**	This Command automatically retracts the wrist if it detects a cube.
 *	This is also done with a mutex to avoid issues with the wrist auto-retracting in certain cases.
 */
public class ToggleCubeIntakeWithRetraction extends Command {
	boolean cubeFlush, cubeStalled, finished;
	
	Timer stopwatch;
	boolean lock;
	
    public ToggleCubeIntakeWithRetraction() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.intake);
        //requires(Robot.wrist);
        //requires(Robot.arm);
        //requires(Robot.elevator);
        
        setInterruptible(false);
        
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cubeFlush = false;
    	UpdateWristSetpoint.intaking = true;
    	Robot.wrist.setSetpoint(0);
    	Robot.arm.setSetpoint(-59);
    	Robot.elevator.setSetpoint(2.8);
    	Robot.intake.retractIntakePistons();
    	finished = false;
    	UpdateArmSetpoint.lock = true;
    }
    
    @Override
	protected void execute() {
    	Robot.intake.setIntakeMotorOutput(0.9);
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		
		cubeFlush = Robot.intake.intakeMotors[0].getOutputCurrent() + Robot.intake.intakeMotors[1].getOutputCurrent() > 40;
		//cubeStalled = Robot.intake.intakeMotors[0].getOutputCurrent() > 12 || Robot.intake.intakeMotors[1].getOutputCurrent() > 12;
		
		if((cubeFlush || cubeStalled) && !lock) {
			stopwatch.start();
			lock = true;
		} else if(!cubeFlush && !cubeStalled) {
			stopwatch.stop();
			stopwatch.reset();
			lock = false;
		}
		
		if(stopwatch.get() > 0.1) {
			finished = true;
			stopwatch.stop();
			stopwatch.reset();
		}
		
		Shuffleboard.putBoolean("Intake", "Flush", cubeFlush);
		Shuffleboard.putBoolean("Intake", "Stalled", cubeStalled);
		
		return (Robot.oi.leftButtons[3].get());// || Robot.oi.testButtons[2].get());
	}
	
    // Called once after isFinished returns true
    protected void end() {
    	if(cubeStalled){
    		Robot.intake.setIntakeMotorOutput(0);
    		stopwatch.start();
    		while(stopwatch.get() < 0.25){
    			// Do nothing pause
    		}
    		stopwatch.stop();
    		stopwatch.reset();
    		stopwatch.start();
    		while(stopwatch.get() < 0.25){
            	Robot.intake.setIntakeMotorOutput(0.55, 0.5);
    		}
    		stopwatch.stop();
    		stopwatch.reset();
    	}
    	if(RobotMap.WristState == 0)
            Robot.wrist.setSetpoint(130);
    	else {
    		while(Robot.wrist.wristMotor.getOutputCurrent() < 25)
    			Robot.wrist.setDirectOutput(0.75);
    		
    		Robot.wrist.setDirectOutput(0);
    	}
    	
    	
    	if(finished) {
    		Intake.isCubePresent = true;
        	Robot.intake.setIntakeMotorOutput(0.1);
    	} else 
        	Robot.intake.setIntakeMotorOutput(0);
    	
    	UpdateWristSetpoint.intaking = false;
    	UpdateArmSetpoint.lock = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
