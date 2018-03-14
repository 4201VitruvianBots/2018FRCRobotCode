package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Intake;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


public class AutoIntakeControl extends Command {
	boolean cubeDetected;
	
	Timer stopwatch;
	
    public AutoIntakeControl() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
        requires(Robot.wrist);
        
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cubeDetected = false;
    	Robot.intake.retractIntakePistons();
    	//new SetWristArmElevatorSetpoints(0, -60, 0);
    }
    
    @Override
	protected void execute() {
    	Robot.intake.setIntakeMotorOutput(1, 0.75);
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		cubeDetected = Robot.intake.intakeMotors[0].getOutputCurrent() > 15 && Robot.intake.intakeMotors[1].getOutputCurrent() > 15;
		return cubeDetected;
	}
	
    // Called once after isFinished returns true
    protected void end() {
    	if(cubeDetected && RobotMap.WristState == 0) {	// && !Intake.isCubePresent){
        	Robot.intake.setIntakeMotorOutput(0);
    		stopwatch.start();
    		while(stopwatch.get() < 0.1){
    			// Do nothing pause
    		}
    		stopwatch.stop();
    		stopwatch.reset();
    		stopwatch.start();
    		while(stopwatch.get() < 0.1){
            	Robot.intake.setIntakeMotorOutput(0.75, 0.75);
    		}
    		stopwatch.stop();
    		stopwatch.reset();
    		//Intake.isCubePresent = true;
        	Robot.wrist.setSetpoint(130);
    	} else if(cubeDetected){
            Robot.intake.setIntakeMotorOutput(0);
            
    		while(Robot.wrist.wristMotor.getOutputCurrent() < 10)
    			Robot.wrist.setDirectOutput(0.5);
    		
			Robot.wrist.setDirectOutput(0);
    	}
    	
        Robot.intake.setIntakeMotorOutput(0.1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
