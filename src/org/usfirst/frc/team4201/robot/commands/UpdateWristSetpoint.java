package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateWristSetpoint extends Command {
	
	
    public UpdateWristSetpoint() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
        
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    @Override
	protected void execute() {
    	if(Wrist.state == 0) {
    		//Robot.wrist.updateWristAngle();
    		
    		if(Robot.intake.intakeMotors[0].getOutputCurrent() > 15 && Robot.intake.intakeMotors[1].getOutputCurrent() > 15)
    			Scheduler.getInstance().add(new RetractWristOnContact());
    		
    	} else if(Wrist.state != 0)
    		Robot.wrist.setDirectOutput(0.1);		// Hold the wrist in place? (May not necessarily work (wrist keeps going up after a certain point)
	}
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
