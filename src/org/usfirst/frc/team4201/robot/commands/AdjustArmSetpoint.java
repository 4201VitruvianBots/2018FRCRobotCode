package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AdjustArmSetpoint extends Command {
	
    public AdjustArmSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
 	@Override
 	protected void execute() {
 		// Inverted
 		double yAxis = -Robot.oi.xBoxController.getRawAxis(1);
 		
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.arm.checkLimits(Robot.arm.getSetpoint() + yAxis))
			Robot.arm.setSetpoint(Robot.arm.getSetpoint() + yAxis);
		else {
			// Get nearest setpoint and use that instead
			
			// Haptic feedback for operator
	        Robot.oi.enableXBoxLeftRumbleTimed();
		}
 	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
