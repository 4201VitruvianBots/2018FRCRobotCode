package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This Command automatically retracts the wrist if it detects a cube.
 *	This is also done with a mutex to avoid issues with the wrist auto-retracting in certain cases.
 */
public class RetractWristOnContact extends InstantCommand {

    public RetractWristOnContact() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
        requires(Robot.wrist);
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Intake.isCubePresent && !Intake.m_cubeLock.isLocked()// && 
    	   //Robot.wrist.getSetpoint() == 0 // && Robot.arm.getSetpoint() == -45 && Robot.elevator.getSetpoint() == 0
    	   ){
    		new SetWristSetpoint(60);
    		Intake.m_cubeLock.lock();
    	} else {
    		Thread t = new Thread(() -> {
    			Timer stopwatch = new Timer();
    			stopwatch.start();
    			while(stopwatch.get() < 1){
    				
    			}
    			Intake.m_cubeLock.unlock();
    		});
    		t.start();
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
