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
 			// If the elevator somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the elevator can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.elevator.getHieght() > Robot.elevator.hieghtUpperLimit)
 				Robot.elevator.setSetpoint(Robot.elevator.hieghtUpperLimit - 0.5);
 			else if(Robot.elevator.getHieght() < Robot.elevator.hieghtLowerLimit)
 				Robot.elevator.setSetpoint(Robot.elevator.hieghtLowerLimit + 0.5);
 			
	    	// Check if new setpoint deosn't violate limits before setting
	    	if(Robot.elevator.checkLimits(Robot.elevator.getSetpoint() + yAxis))
				Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() + yAxis);
			else {
				// Get nearest setpoint and use that instead
				
				// Haptic feedback for operator
		        Robot.oi.enableXBoxRightRumbleTimed();
			}
 		}
 		else {	// Manual Mode
 			if(Math.abs(yAxis) > 0.05)
 				Robot.elevator.setDirectOutput(yAxis * 0.75);
 			else { // Provide constant motor output to prevent backdrive
 				if(Robot.elevator.getElevatorShiftersStatus())
 					Robot.elevator.setDirectOutput(0);		
				else
 					Robot.elevator.setDirectOutput(0.05);		
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
