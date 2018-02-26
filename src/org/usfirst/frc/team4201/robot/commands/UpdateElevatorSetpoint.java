package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class UpdateElevatorSetpoint extends Command {
	
    public UpdateElevatorSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
 	@Override
 	protected void execute() {
 		// Inverted
 		double yAxis = -Robot.oi.xBoxController.getRawAxis(5);
 		
 		if(Elevator.state == 0){
	    	// Check if new setpoint deosn't violate limits before setting
	    	if(Robot.elevator.checkLimits(Robot.elevator.getSetpoint() + yAxis))
				Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() + yAxis);
			else {
				// Get nearest setpoint and use that instead
				
				// Haptic feedback for operator
		        Robot.oi.enableXBoxRightRumbleTimed();
			}
 		}
 		else {
 			if(yAxis != 0)
 				Robot.elevator.setDirectOutput(yAxis / 2);
 			else {
 				//Robot.elevator.setDirectOutput(0.15);		// Test one thing at a time
 			}
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
