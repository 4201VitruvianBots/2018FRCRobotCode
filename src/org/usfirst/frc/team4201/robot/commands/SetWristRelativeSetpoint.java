package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.LUTs;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class SetWristRelativeSetpoint extends InstantCommand {
	
	static double setpoint;
    public SetWristRelativeSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        SetWristRelativeSetpoint.setpoint = setpoint;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.arm.getAngle() > 0){
    		double absoluteSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(setpoint);
    		if(Robot.wrist.checkLimits(absoluteSetpoint)) {
    			if(Robot.arm.getAngle() <= 50){
	    			try{
	    				double setpointLimit = LUTs.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - 50];
	    				if(absoluteSetpoint < setpointLimit)
	    					absoluteSetpoint = setpointLimit;
	    				
	    			} catch(Exception e) {
	    				
	    			}
    			}
    			
	    		Robot.wrist.setSetpoint(absoluteSetpoint);
	    	} 
    	}
    	else 
	        Robot.oi.enableXBoxRightRumble();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
