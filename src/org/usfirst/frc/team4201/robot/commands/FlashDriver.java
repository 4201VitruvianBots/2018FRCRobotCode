package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FlashDriver extends Command {
	Timer timer;
	String mechanismName;
    public FlashDriver(String name) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.controls);
        timer = new Timer();
        mechanismName = name;
    }

    protected void initialize() {
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.get() < 0.5)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", false);
    	else if(timer.get() < 1)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", true);
    	else if(timer.get() < 1.5)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", false);
    	else if(timer.get() < 2)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", true);
    	else if(timer.get() < 2.5)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", false);
    	else if(timer.get() < 3)
    		SmartDashboard.putBoolean("Disabled Mechanism Alert!", true);
		SmartDashboard.putString("Disabled Mechanism", mechanismName);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
