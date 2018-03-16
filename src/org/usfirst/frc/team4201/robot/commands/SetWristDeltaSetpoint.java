package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetWristDeltaSetpoint extends InstantCommand {
	
	double inc;
    public SetWristDeltaSetpoint(double increment) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        this.inc = increment;
        
        setInterruptible(true);
        //Robot.wrist.setDefaultCommand(null);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(RobotMap.WristState == 0){
	    	// Check if new setpoint deosn't violate limits before setting
    		double input = Robot.wrist.getSetpoint() + inc;
			double checkSetpoint = Robot.wrist.getValidAngle(input);
    		if(checkSetpoint != -500)
    			// I forget why I did this, but if you are being limited, then ??? else just move to your target
    			if(input != checkSetpoint){
    				
    			}
				else
    				Robot.wrist.setSetpoint(checkSetpoint);
			else
		        Robot.oi.enableXBoxRightRumble();
    	}
    	else
    		if(inc > 0)
    			Robot.wrist.setDirectOutput(0.8);
    		else if(inc < 0)
    			Robot.wrist.setDirectOutput(-0.8);
    		else
    			Robot.wrist.setDirectOutput(0);
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.disableXBoxRightRumble();
        //Robot.wrist.setDefaultCommand(new UpdateWristSetpoint());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
