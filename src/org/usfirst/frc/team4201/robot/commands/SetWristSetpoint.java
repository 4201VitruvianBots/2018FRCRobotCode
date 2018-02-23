package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.WristLimitTable;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class SetWristSetpoint extends InstantCommand {
	
	double setpoint;
    public SetWristSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        this.setpoint = setpoint;
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Check if new setpoint deosn't violate limits before setting
    	if(Wrist.state == 0){
	    	if(Robot.wrist.checkLimits(setpoint))
				Robot.wrist.setSetpoint(setpoint);
			else if(Robot.arm.getAngle() >= Wrist.armLimiterLowerBound && Robot.arm.getAngle() <= Wrist.armLimiterUpperBound) {
				int setpointLimit = WristLimitTable.wristLimits[(int)Math.ceil(Robot.arm.getAngle()) - Wrist.armLimiterLowerBound];
				
				if(Robot.wrist.getSetpoint() < 0)
					Robot.wrist.setSetpoint(-setpointLimit);
				else 
					Robot.wrist.setSetpoint(setpointLimit);
			} else {
				// Get nearest setpoint and use that instead
				
				
				// Haptic feedback for operator
		        Robot.oi.enableXBoxRightRumble();
			}
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
