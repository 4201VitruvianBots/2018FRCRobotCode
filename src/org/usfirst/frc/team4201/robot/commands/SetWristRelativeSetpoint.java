package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.LUTs;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class SetWristRelativeSetpoint extends InstantCommand {
	static double absoluteSetpoint, setpointLimit, increment;
	static double setpoint;
	static int state = 0;
	static int adjustment = 0;
	Button thisButton;
    public SetWristRelativeSetpoint(double setpoint, Button thisButton) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        SetWristRelativeSetpoint.setpoint = setpoint;
        
        this.thisButton = thisButton;
        
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Wrist.state == 0){
    		absoluteSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint);
    		Robot.wrist.setSetpoint(absoluteSetpoint);
    		/*
    		if(Robot.wrist.checkLimits(absoluteSetpoint)) {
    			if(Robot.arm.getAngle() <= 50){
	    			try{
	    				setpointLimit = LUTs.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) + 50];
	    				if(absoluteSetpoint < setpointLimit)
	    					absoluteSetpoint = setpointLimit;
	    				
	    				if(Math.abs(Robot.wrist.getSetpoint() - absoluteSetpoint) < 2);	// Rumble when arm cannot proceed further
	    		       		Robot.oi.enableXBoxRightRumble();
	    			} catch(Exception e) {
	    				
	    			}
    			}
    			
	    		Robot.wrist.setSetpoint(absoluteSetpoint);
	    	} 
    	} else if (Robot.arm.getAngle() > 0 && state == 1){
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
    		if(Robot.wrist.checkLimits(absoluteSetpoint))
    			Robot.wrist.setSetpoint(absoluteSetpoint + increment);
    		*/
    	} else 
	        Robot.oi.enableXBoxRightRumble();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!thisButton.get()){
    		state = 0;
    		increment = 0;
	        Robot.oi.disableXBoxRightRumble();
	        //Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(90));
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
