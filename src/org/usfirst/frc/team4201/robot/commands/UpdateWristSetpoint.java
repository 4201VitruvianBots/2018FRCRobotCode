package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class UpdateWristSetpoint extends Command {
	boolean setSetpoint = false;
	double setpoint;
	
	public UpdateWristSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);

        this.setSetpoint = false;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    @Override
	protected void execute() {
    	/*
    	if(Wrist.state == 0) {
    		// Update the wrists max limits based on current arm angle.
    		Robot.wrist.updateWristLimits();
    		
    		// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the arm can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.wrist.getRelativeAngle() > Robot.wrist.angleUpperLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleUpperLimit - 2);
 			else if(Robot.wrist.getRelativeAngle() < Robot.wrist.angleLowerLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleLowerLimit + 2);
 			
 			// Override the retraction when within arm limit range. This is done by programmers who know how far to move.
 			if(override)
 				Robot.wrist.setSetpoint(setpoint);
 			else if(Robot.arm.getAngle() > Robot.wrist.armLimiterLowerBound && Robot.arm.getAngle() < Robot.wrist.armLimiterUpperBound){
 				// If the arm is in the limit range, then we always have it retracted
 				Robot.wrist.setSetpoint(120);
 			} else if(increment){
 				// Manual Closed-Loop Control
 				if(Robot.wrist.checkLimits(setpoint))
 					Robot.wrist.setSetpoint(setpoint);
 				else
 			        Robot.oi.enableXBoxRightRumble();
 			}
    	} else if(Wrist.state != 0) { // Manual Mode
    		if(setpoint != 0 && increment)
    			Robot.wrist.setDirectOutput(setpoint / 2);
    		else
    			Robot.wrist.setDirectOutput(0.1);		// Hold the wrist in place? (May not necessarily work (wrist keeps going up after a certain point)
    	}
    	*/
    	if(Wrist.state == 0) {
    		// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the arm can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.wrist.getRelativeAngle() > Robot.wrist.angleUpperLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleUpperLimit - 2);
 			else if(Robot.wrist.getRelativeAngle() < Robot.wrist.angleLowerLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleLowerLimit + 2);
 			
 			if(Robot.arm.getAngle() > Wrist.armLimiterLowerBound && Robot.arm.getAngle() < Wrist.armLimiterUpperBound){
 				// If the arm is in the limit range, then we always have it retracted
 				Robot.wrist.setSetpoint(120);
    		} else {
    			// Manual Closed-Loop Control
        		if(Robot.oi.xBoxButtons[5].get()){
	    			if(Robot.wrist.checkLimits(Robot.wrist.getSetpoint() + 1))
	    				Robot.wrist.setSetpoint(Robot.wrist.getSetpoint() + 1);
	    			else
	    		        Robot.oi.enableXBoxRightRumble();
        		} else if(Robot.oi.xBoxLeftTrigger.get()){
        			if(Robot.wrist.checkLimits(Robot.wrist.getSetpoint() - 1))
	    				Robot.wrist.setSetpoint(Robot.wrist.getSetpoint() - 1);
	    			else
	    		        Robot.oi.enableXBoxRightRumble();
        		}
    		}
    	} else { // Manual Mode
    		if(Robot.oi.xBoxButtons[5].get())
    			Robot.wrist.setDirectOutput(0.5);
    		else if(Robot.oi.xBoxLeftTrigger.get()){
    			Robot.wrist.setDirectOutput(-0.5);
    		} else
    			Robot.wrist.setDirectOutput(0.1); // Prevent backdrive in manual mode (Wrist can still move a bit after a certain point)
    			
    	}
	}
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
