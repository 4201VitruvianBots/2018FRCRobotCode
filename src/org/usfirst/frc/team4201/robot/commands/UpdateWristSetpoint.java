package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class UpdateWristSetpoint extends Command {
	double setpoint;
	
	public UpdateWristSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);

        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    @Override
	protected void execute() {
    	if(Wrist.state == 0) {
    		// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the wrist can become uncontrollable due to the increment not being able to set the setpoint in range.
    		if(Robot.wrist.getAbsoluteAngle() > Robot.wrist.angleUpperLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleUpperLimit - 2);
 			else if(Robot.wrist.getAbsoluteAngle() < Robot.wrist.angleLowerLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleLowerLimit + 2);
 			
    		
    		// Default to one of two setpoints if no setpoint is being actively commanded
 			if(Robot.arm.getAngle() < 0) 
 				// If the arm is in the limit range, then we always have it retracted
 				Robot.wrist.setSetpoint(120);
			else if(Robot.arm.getAngle() >= 0)
 				Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(90));
			/*
    		} else {
    			// Manual Closed-Loop Control
        		if(Robot.oi.xBoxButtons[5].get()){
	    			if(Robot.wrist.checkLimits(Robot.wrist.getSetpoint() + 1))
	    				Robot.wrist.setSetpoint(Robot.wrist.getSetpoint() + 1);
	    			else
	    		        Robot.oi.enableXBoxRightRumble();
        		} else if(Robot.oi.xBoxRightTrigger.get()){
        			if(Robot.wrist.checkLimits(Robot.wrist.getSetpoint() - 1))
	    				Robot.wrist.setSetpoint(Robot.wrist.getSetpoint() - 1);
	    			else
	    		        Robot.oi.enableXBoxRightRumble();
        		}
    		}
    		*/
    	} else {
    		if(!Robot.oi.xBoxButtons[5].get() && !Robot.oi.xBoxRightTrigger.get())
    			Robot.wrist.setDirectOutput(0.1); // Prevent backdrive in manual mode (Wrist can still move a bit after a certain point)
    	}
	}
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;	// What if this returns true? Does this eliminate all other issues?
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
