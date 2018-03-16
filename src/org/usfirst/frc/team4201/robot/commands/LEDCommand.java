package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDCommand extends Command {
	Timer LEDTimer;
	
    public LEDCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.controls);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	for(int i = 0; i < Robot.controls.LEDS.length; i++)
    		Robot.controls.LEDS[i].set(false);
    	
    	LEDTimer = new Timer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	LEDTimer.start();
    	if(LEDTimer.get() < 0.2){
    		Robot.controls.LEDS[0].set(true);
    		Robot.controls.LEDS[1].set(false);
    		Robot.controls.LEDS[2].set(false);
    	} else if (LEDTimer.get() < 0.4){
    		Robot.controls.LEDS[0].set(false);
    		Robot.controls.LEDS[1].set(true);
    		Robot.controls.LEDS[2].set(false);
    	} else if (LEDTimer.get() < 0.6){
    		Robot.controls.LEDS[0].set(false);
    		Robot.controls.LEDS[1].set(false);
    		Robot.controls.LEDS[2].set(true);
    	} else if (LEDTimer.get() < 0.8){
    		Robot.controls.LEDS[0].set(true);
    		Robot.controls.LEDS[1].set(true);
    		Robot.controls.LEDS[2].set(false);
    	} else { 
    		Robot.controls.LEDS[0].set(true);
    		Robot.controls.LEDS[1].set(false);
    		Robot.controls.LEDS[2].set(true);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return LEDTimer.get() > 1;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.controls.LEDS[0].set(false);
		Robot.controls.LEDS[1].set(false);
		Robot.controls.LEDS[2].set(false);
		
		LEDTimer.stop();
		LEDTimer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
