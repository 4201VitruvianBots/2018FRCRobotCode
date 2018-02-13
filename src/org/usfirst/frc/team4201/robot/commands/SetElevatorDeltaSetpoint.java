package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class SetElevatorDeltaSetpoint extends InstantCommand {
	
	double inc;
    public SetElevatorDeltaSetpoint(double increment) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
        this.inc = increment;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Robot.elevator.checkLimits(Robot.elevator.getSetpoint() + inc))
			Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() + inc);
		else {
			// Get nearest setpoint and use that instead
			
			// Haptic feedback for operator
	        Robot.oi.enableXBoxRightRumble();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
