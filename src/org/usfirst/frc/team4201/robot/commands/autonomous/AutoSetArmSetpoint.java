package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.UpdateWristSetpoint;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSetArmSetpoint extends Command {
	double setpoint, delay = 0, initialPosition;
	Timer stopwatch;
	
	public AutoSetArmSetpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.setpoint = setpoint;
        
        setTimeout(3);
    }
	
    public AutoSetArmSetpoint(double setpoint, double delay) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.setpoint = setpoint;
        this.delay = delay;

        setTimeout(3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialPosition = Robot.arm.getAngle();
    	
    	if(delay > 0) {
    		stopwatch = new Timer();
    		stopwatch.start();
    		
    		while(stopwatch.get() < delay) {
    			
    		}
    		
    		stopwatch.stop();
    	}
    	// Check if new setpoint deosn't violate limits before setting
		if(Robot.arm.checkLimits(setpoint))
    		Robot.arm.setSetpoint(setpoint);
		else
			Robot.oi.enableXBoxLeftRumbleTimed();
		
    }
    
    protected void execute() {
    	if(Robot.arm.getAngle() > 16 && initialPosition <= 16) {
			UpdateWristSetpoint.autoSetpoint = Robot.wrist.convertRelativeToAbsoluteSetpoint(90);
			Robot.wrist.setSetpoint(Robot.wrist.convertRelativeToAbsoluteSetpoint(90));
		} else if(Robot.arm.getAngle() > 16 && initialPosition > 16) {
			UpdateWristSetpoint.autoSetpoint = 130;
			Robot.wrist.setSetpoint(130);
		}
		//*/
    }
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.arm.onTarget() || isTimedOut();
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
