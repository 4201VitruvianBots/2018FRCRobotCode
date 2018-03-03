package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class UpdateArmSetpoint extends Command {
	
    public UpdateArmSetpoint() {
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
 		
 		if(Arm.state == 0){
 			// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the arm can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.arm.getAngle() > Robot.arm.angleUpperLimit)
 				Robot.arm.setSetpoint(Robot.arm.angleUpperLimit - 2);
 			else if(Robot.arm.getAngle() < Robot.arm.angleLowerLimit)
 				Robot.arm.setSetpoint(Robot.arm.angleLowerLimit + 2);

 			// We do this check to make sure co-driver is actually commanding the arm and not due to minor movement of the joystick.
 			// This also prevent an issue where setSetpoint(getSetpoint() + yAxis == 0) continually adds to the setpoint (floating point rounding?)
 			if(Math.abs(yAxis) > 0.05)
 		    	// Check if new setpoint deosn't violate limits before setting
	 			if(Robot.arm.checkLimits(Robot.arm.getSetpoint() + (2 * yAxis))){
	 				// Change kP value for the PIDController when going up/down, to prevent wobbling when going down due to excessive force
					if(Robot.arm.getSetpoint() + (2 *yAxis) > Robot.arm.getSetpoint())
						Robot.arm.getPIDController().setP(Robot.arm.kPUp);
					else
						Robot.arm.getPIDController().setP(Robot.arm.kPDown);
					
		    		Robot.arm.setSetpoint(Robot.arm.getSetpoint() + (2 * yAxis));
		    	} else {
					// Haptic feedback for operator
			        Robot.oi.enableXBoxRightRumbleTimed();
				}
 		}
 		else {	// Manual Mode
 			if(Math.abs(yAxis) > 0.05)
 				Robot.arm.setDirectOutput(yAxis * 0.8);	// Do not multiply by a fraction
 			else // Provide constant motor output to prevent backdrive
 				Robot.arm.setDirectOutput(yAxis / 3);
 				
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
