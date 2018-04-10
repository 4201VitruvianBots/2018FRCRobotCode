package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class SetWristSetpoint extends Command {
	
	double setpoint;
    
	public SetWristSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        this.setpoint = setpoint;
        
        setInterruptible(true);
    }
	
	public SetWristSetpoint(double setpoint, double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        this.setpoint = setpoint;
        //Robot.wrist.setDefaultCommand(null);
        
        setTimeout(timeout);
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.wrist.checkLimits(setpoint)) {
    		Robot.wrist.setSetpoint(setpoint);
    	}
    }
    
    @Override
	protected void execute() {
    	
    }
    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.wrist.onTarget();
	}
	
    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
