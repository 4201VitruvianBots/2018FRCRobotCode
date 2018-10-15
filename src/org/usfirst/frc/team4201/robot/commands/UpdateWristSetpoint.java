package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.LUTs;
import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateWristSetpoint extends Command {
	public static boolean intaking, autoCommand;
	public static double autoSetpoint, armTargetSetpoint;
	double setpoint, setpointLimit;
	
	public static int WRIST_FORWARD = 1;
	public static int WRIST_BACKWARDS = 0;
	
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
    	if(RobotMap.WristState == 0) {
    		// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the wrist can become uncontrollable due to the increment not being able to set the setpoint in range.
    		if(Robot.wrist.getAbsoluteAngle() > Robot.wrist.angleUpperLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleUpperLimit - 2);
 			else if(Robot.wrist.getAbsoluteAngle() < Robot.wrist.angleLowerLimit)
 				Robot.wrist.setSetpoint(Robot.wrist.angleLowerLimit + 2);
 			
    		if(intaking || Robot.oi.xBoxButtons[WRIST_FORWARD].get() || Robot.oi.xBoxButtons[WRIST_BACKWARDS].get() || Robot.oi.xBoxButtons[2].get() || Robot.oi.xBoxButtons[3].get() || autoCommand){
    			
	 			if(Robot.oi.xBoxButtons[WRIST_BACKWARDS].get())
	    			setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(0);
				else if(Robot.oi.xBoxButtons[WRIST_FORWARD].get())
	    			setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(180);	// 45
				else if(Robot.oi.xBoxButtons[2].get())
	    			setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(45);
				else if(Robot.oi.xBoxButtons[3].get())
	    			setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(135);
				else if(autoCommand)
					setpoint = autoSetpoint;
				else if(intaking) {
					if(Robot.arm.getAngle() < -45)
						setpoint = 0;
					else
						setpoint = 130;
				}
	 			//else if(Robot.oi.xBoxButtons[2].get())
	    		//	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(180));
				//else if(Robot.oi.xBoxButtons[3].get())
	    		//	Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(135));
	 			if(!intaking) {
		 			if(Robot.arm.getAngle() >= Wrist.armLimiterLowerBound && Robot.arm.getAngle() <= Wrist.armLimiterUpperBound) {
		 				try { 
		 					setpointLimit = LUTs.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - Wrist.armLimiterLowerBound];
		 					setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(setpointLimit);
		 					//if(setpoint < setpointLimit)
		 					//	setpoint = setpoint >= 0 ? setpointLimit : -setpointLimit;
		 				} catch (Exception e){
		 					
		 				}
		 			}
	 			}
    		} else		
				// Default to one of two setpoints if no setpoint is being actively commanded
	 			if(Robot.arm.getAngle() < 13) 
	 				// If the arm is in the limit range, then we always have it retracted
	 				setpoint = 130;
				else if(Robot.arm.getAngle() >= 0)
	 				setpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(90);
 			
			Robot.wrist.setSetpoint(setpoint > Robot.wrist.angleUpperLimit ? Robot.wrist.angleUpperLimit - 1: setpoint < Robot.wrist.angleLowerLimit ? Robot.wrist.angleLowerLimit + 1: setpoint);
    	} else {
    		if(!Robot.oi.xBoxButtons[5].get() && !Robot.oi.xBoxRightTrigger.get())
    			Robot.wrist.setDirectOutput(0.1); // Prevent backdrive in manual mode (Wrist can still move a bit after a certain point)
    	}
	}
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;	// What if this returns true? Does this eliminate all other issues?
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
