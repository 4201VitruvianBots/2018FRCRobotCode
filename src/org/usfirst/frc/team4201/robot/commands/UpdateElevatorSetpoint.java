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
 			//else if(Robot.elevator.getHieght() < Robot.elevator.hieghtLowerLimit)		// This does not apply to the elevator because it has an actual hard-stop. 
 				//Robot.elevator.setSetpoint(Robot.elevator.hieghtLowerLimit + 0.5);	// Disabled due to issues with this overriding user input.
 			
 			// We do this check to make sure co-driver is actually commanding the elevator and not due to minor movement of the joystick.
 			// This also prevent an issue where setSetpoint(getSetpoint() + yAxis == 0) continually adds to the setpoint (floating point rounding?)
 			if(Math.abs(-yAxis) > 0.05)
 		    	// Check if new setpoint deosn't violate limits before setting
		    	if(Robot.elevator.checkLimits(Robot.elevator.getSetpoint() + (0.5 * yAxis))) {
	 				// Change kP value for the PIDController when going up/down, to prevent wobbling when going down due to excessive force
		    		
					Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() + (0.5 * yAxis));
		    	} else {
					// Haptic feedback for operator
			        Robot.oi.enableXBoxLeftRumbleTimed();
				}
 			
			if(Robot.elevator.getElevatorShiftersStatus()) {
				if(Robot.elevator.getPIDController().get() > 0){
					Robot.elevator.getPIDController().setP(Elevator.kPLowUp);
					Robot.elevator.getPIDController().setD(Elevator.kILowUp);
					Robot.elevator.getPIDController().setI(Elevator.kDLowUp);
				} else {
					Robot.elevator.getPIDController().setP(Elevator.kPLowDown);
					Robot.elevator.getPIDController().setD(Elevator.kILowDown);
					Robot.elevator.getPIDController().setI(Elevator.kDLowDown);
					
				}
 			} else {
				if(Robot.elevator.getPIDController().get() > 0){
					Robot.elevator.getPIDController().setP(Elevator.kPHighUp);
					Robot.elevator.getPIDController().setD(Elevator.kIHighUp);
					Robot.elevator.getPIDController().setI(Elevator.kDHighUp);
 					
 				} else {
					Robot.elevator.getPIDController().setP(Elevator.kPHighDown);
					Robot.elevator.getPIDController().setD(Elevator.kIHighDown);
					Robot.elevator.getPIDController().setI(Elevator.kDHighDown);
 				}
 			}
 			
 		}
 		else {	// Manual Mode
 			if(Math.abs(yAxis) > 0.05)
 				Robot.elevator.setDirectOutput(yAxis * 0.75);
 			else { // Provide constant motor output to prevent backdrive. This depends if the elevator is on high/low gear (high gear has enough torque to prevent backdrive)
 				// No longer needed due to addition of back rope
 				
 				/*
 				if(Robot.elevator.getElevatorShiftersStatus())
 					Robot.elevator.setDirectOutput(0);		
				else
 					Robot.elevator.setDirectOutput(0.05);		
				*/
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
