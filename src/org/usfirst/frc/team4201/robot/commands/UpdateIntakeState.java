package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Arm;
import org.usfirst.frc.team4201.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**	This command must be an InstantCommand because of how we're using it.
 *
 */
public class UpdateIntakeState extends Command {
	Timer stopwatch;
	public static int state = 0;
	
    public UpdateIntakeState() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
        
        setInterruptible(true);
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
 	@Override
 	protected void execute() {
 		//if(Intake.isCubePresent && Robot.oi.)
 		
 		// Switching states
 		// When you intake
 		// Transitioning from intake to retract
 		
 		switch(state){
 			case 4:	// outtake 
 				Robot.intake.setIntakeMotorOutput(-0.75);
 				stopwatch.start();
 				while(stopwatch.get() < 0.5){
 					
 				}
 				stopwatch.stop();
 				stopwatch.reset();
 				state = 0;
 				break;
 			case 3: // Intake 
	 			Robot.intake.setIntakeMotorOutput(0.75);
 			case 2: // Retraction
	 			Robot.intake.setIntakeMotorOutput(0.75);
 			case 1:
	 			Robot.intake.setIntakeMotorOutput(0);
 				
 				break;
 			case 0:	// 
			default:
	 			Robot.intake.setIntakeMotorOutput(0);		
 		}
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
