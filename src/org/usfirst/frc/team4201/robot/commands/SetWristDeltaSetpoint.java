package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
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
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//SmartDashboard.putNumber("Wrist Setpoint", Robot.wrist.PIDControl.getSetpoint() + inc);
    	if(Wrist.state == 0){
	    	// Check if new setpoint deosn't violate limits before setting
			if(Robot.wrist.checkLimits(Robot.wrist.getSetpoint() + inc))
				Robot.wrist.setSetpoint(Robot.wrist.getSetpoint() + inc);
			else
		        Robot.oi.enableXBoxRightRumble();
    	}
    	else
			Robot.wrist.setDirectOutput(inc / 4);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.disableXBoxRightRumble();
		if(Wrist.state != 0)
			Robot.wrist.setDirectOutput(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
