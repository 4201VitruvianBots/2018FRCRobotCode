package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AdjustWristSetpoint extends Command {
	
    public AdjustWristSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
 	@Override
 	protected void execute() {
 		double yAxis = Robot.oi.xBoxController.getRawAxis(1);
 		
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.wrist.getSetpoint() + yAxis < Robot.wrist.angleUpperLimit &&
		   Robot.wrist.getSetpoint() + yAxis > Robot.wrist.angleLowerLimit)
			Robot.wrist.setSetpointRelative(yAxis);
		else {
			// Get nearest setpoint and use that instead
			
			// Haptic feedback for operator
	        Robot.oi.enableXBoxRightRumbleTimed();
		}
 	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.disableXBoxRightRumble();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
