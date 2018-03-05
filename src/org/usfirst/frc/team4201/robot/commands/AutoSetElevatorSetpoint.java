package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoSetElevatorSetpoint extends Command {
	
	double setpoint;
    public AutoSetElevatorSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
        this.setpoint = setpoint;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.elevator.checkLimits(setpoint))
			Robot.elevator.setSetpoint(setpoint);
		else {
			// Get nearest setpoint and use that instead
			
			// Haptic feedback for operator
	        Robot.oi.enableXBoxRightRumble();
		}
    }

    @Override
	protected void execute() {
    	
    }
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.elevator.onTarget();
	}
	
    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
