package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class AutoManualWristControl extends Command {
	double output;
	
    public AutoManualWristControl(double output, double time) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        
        this.output = output;
        setInterruptible(true);
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    @Override
	protected void execute() {
    	Robot.wrist.setDirectOutput(output);
    }
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
    // Called once after isFinished returns true
    protected void end() {
    	Robot.wrist.setDirectOutput(0.1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }

}
