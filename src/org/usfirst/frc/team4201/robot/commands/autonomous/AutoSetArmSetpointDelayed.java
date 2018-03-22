package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoSetArmSetpointDelayed extends Command {
	double setpoint;
	
    public AutoSetArmSetpointDelayed(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.checkLimits(setpoint))
    		Robot.arm.setSetpoint(setpoint);
		else
			Robot.oi.enableXBoxLeftRumbleTimed();

    }
    
    protected void execute() {
    	
    }
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.arm.onTarget();
	}
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.oi.disableXBoxLeftRumble();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
