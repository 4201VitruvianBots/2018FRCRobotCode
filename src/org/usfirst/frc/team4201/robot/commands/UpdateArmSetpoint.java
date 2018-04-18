package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class UpdateArmSetpoint extends Command {
	public static boolean lock = false;
	
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
 		
 		if(RobotMap.ArmState == 0){
 			// If the arm somehow gets out of range, pull it back in range automatically.
 			// If this isn't done, then there is a chance the arm can become uncontrollable due to the increment not being able to set the setpoint in range.
 			if(Robot.arm.getAngle() > Robot.arm.angleUpperLimit)
 				Robot.arm.setSetpoint(Robot.arm.angleUpperLimit - 2);
 			//else if(Robot.arm.getAngle() < Robot.arm.angleLowerLimit)
 			//	Robot.arm.setSetpoint(Robot.arm.angleLowerLimit + 2);

 			// We do this check to make sure co-driver is actually commanding the arm and not due to minor movement of the joystick.
 			// This also prevent an issue where setSetpoint(getSetpoint() + yAxis == 0) continually adds to the setpoint (floating point rounding?)
 			else if(Math.abs(yAxis) > 0.05 && !lock) {
 		    	// Check if new setpoint deosn't violate limits before setting
	 			if(Robot.arm.checkLimits(Robot.arm.getAngle() + (10 * yAxis))){	// Add 1 to prevent going past upper limit
	 				// Change kP value for the PIDController when going up/down, to prevent wobbling when going down due to excessive force
		    		Robot.arm.setSetpoint(Robot.arm.getAngle() + (10 * yAxis));	// try this (?)
		    	} else {
					// Haptic feedback for operator
			        Robot.oi.enableXBoxLeftRumbleTimed();
				}
 			}
 			
 			// Set different PID values depending on arm is going up/down
 			/*
 			if(Robot.arm.getPIDController().get() > 0) {
				Robot.arm.getPIDController().setP(Arm.kPUp);
			} else {
				Robot.arm.getPIDController().setP(Arm.kPDown);
			}
 			*/
 			// When the arm is low, lower the lower output range to prevent slamming to the hardstop that can cause damage
 			if(!DriverStation.getInstance().isAutonomous()) {
	 			if(Robot.arm.getAngle() < -45) {
	 				Robot.arm.setOutputRange(-0.25, 1);
	 				Robot.arm.getPIDController().setP(Robot.arm.kPDown);
	 			} else {
	 				Robot.arm.setOutputRange(-0.75, 1);
	 				Robot.arm.getPIDController().setP(Robot.arm.kPUp);
	 			}
 			}
 		}
 		else {	// Manual Mode
 			if(Math.abs(yAxis) > 0.05)
 				if(yAxis > 0)
 					Robot.arm.setDirectOutput(yAxis);			
 				else
 					Robot.arm.setDirectOutput(yAxis * 0.8);
			else
 				Robot.arm.setDirectOutput(0);
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
