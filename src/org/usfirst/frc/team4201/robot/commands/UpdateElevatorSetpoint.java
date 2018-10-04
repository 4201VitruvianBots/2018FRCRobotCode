package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class UpdateElevatorSetpoint extends Command {
	public static double setpoint;
	
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
 		
 		if(RobotMap.ElevatorState == 0){
			// If the elevator somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the elevator can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.elevator.getHieght() > Robot.elevator.hieghtUpperLimit)
 				Robot.elevator.setSetpoint(Robot.elevator.hieghtUpperLimit - 0.5);
 			
 			// We do this check to make sure co-driver is actually commanding the elevator and not due to minor movement of the joystick.
 			// This also prevent an issue where setSetpoint(getSetpoint() + yAxis == 0) continually adds to the setpoint (floating point rounding?)
 			if(Math.abs(yAxis) > 0.05) {
 				if(Robot.elevator.getHieght() < 4.5 && Robot.arm.getAngle() < -50 && yAxis > 0)
 					Robot.arm.setSetpoint(-50);
 					
 		    	// Check if new setpoint deosn't violate limits before setting
		    	if(Robot.elevator.checkLimits(Robot.elevator.getHieght() + (1 * yAxis))) {
	 				// Change kP value for the PIDController when going up/down, to prevent wobbling when going down due to excessive force
		    		
		    		// Move the arm to prevent catching if its angle is low and is not being moved
		    		//if(Robot.arm.getAngle() < -55 && Robot.arm.getSetpoint() < -55 && Robot.elevator.getHieght() < 5)
		    		//	Robot.arm.setSetpoint(-55);
		    		
					Robot.elevator.setSetpoint(Robot.elevator.getHieght() + (1 * yAxis));
		    	} else {
					// Haptic feedback for operator
			        Robot.oi.enableXBoxRightRumbleTimed();
				}
 			}
 			
 			// Set different PID values depending on elevator shifter status and whether the elevator is going up/down
			if(Robot.elevator.getElevatorShiftersStatus()) {
 				Robot.elevator.setOutputRange(-0.8, 1);
				if(Robot.elevator.getPIDController().get() > 0){
					Robot.elevator.getPIDController().setP(Elevator.kPHighUp);
					Robot.elevator.getPIDController().setI(Elevator.kIHighUp);
					Robot.elevator.getPIDController().setD(Elevator.kDHighUp);
				} else {
					Robot.elevator.getPIDController().setP(Elevator.kPHighDown);
					Robot.elevator.getPIDController().setI(Elevator.kIHighDown);
					Robot.elevator.getPIDController().setD(Elevator.kDHighDown);
				}
 			} else {
 				Robot.elevator.setOutputRange(-0.25, 1);
				if(Robot.elevator.getPIDController().get() > 0){
					Robot.elevator.getPIDController().setP(Elevator.kPLowUp);
					Robot.elevator.getPIDController().setI(Elevator.kILowUp);
					Robot.elevator.getPIDController().setD(Elevator.kDLowUp);
 				} else {
					Robot.elevator.getPIDController().setP(Elevator.kPLowDown);
					Robot.elevator.getPIDController().setI(Elevator.kILowDown);
					Robot.elevator.getPIDController().setD(Elevator.kDLowDown);
 				}
 			}
 		}
 		else {	// Manual Mode
 			if(Math.abs(yAxis) > 0.05)
 				Robot.elevator.setDirectOutput(yAxis * 0.75);
 			else { // Provide constant motor output to prevent backdrive. This depends if the elevator is on high/low gear (high gear has enough torque to prevent backdrive)
				Robot.elevator.setDirectOutput(0);		
				// No longer needed due to addition of back rope (?)
 				
 				/*
 				if(Robot.elevator.getElevatorShiftersStatus())
 					Robot.elevator.setDirectOutput(0);		
				else
 					Robot.elevator.setDirectOutput(0.05);		
				*/
 			}
 		}
 		// If the limit switch is pressed and the elevator wants to continue to go down, reset the setpoint to its current height (even if its incorrect) to stop it from moving
 		
 		if(!Robot.elevator.lowerLimitSwitch.get()) {
 			if(RobotMap.ElevatorState == 0) {
	 			if(Robot.elevator.getPIDController().getError() < -0.2)
		 				Robot.elevator.setSetpoint(Robot.elevator.getHieght());
		 		} /* else if(Robot.elevator.upperLimitSwitch.get()){
		 			if(Robot.elevator.getPIDController().getError > 0.2)
		 				Robot.elevator.setSetpoint(Robot.elevator.getHieght());
		 		}
		 		//*/
 			else {
				Robot.elevator.setDirectOutput(0);
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
