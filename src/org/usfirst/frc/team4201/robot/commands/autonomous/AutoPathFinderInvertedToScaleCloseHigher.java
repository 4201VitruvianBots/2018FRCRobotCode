package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderInvertedToScaleCloseHigher extends CommandGroup {
	double armSetpoint = 40, armDelay = 0, wristSetpoint = 135, delay = 0.4;
	boolean wristRelative = true;
	
	public AutoPathFinderInvertedToScaleCloseHigher(String filename, boolean first, double maxVel, double kP) {
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addSequential(new Delay(delay));	
		addSequential(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
    }
    
    public AutoPathFinderInvertedToScaleCloseHigher(String filename, boolean first, double maxVel) {
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addSequential(new Delay(delay));	
		addSequential(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
    }
    
    public AutoPathFinderInvertedToScaleCloseHigher(String filename, boolean first) {
		addParallel(new PathFinderReadInverted(filename, first));
    }
    
    public AutoPathFinderInvertedToScaleCloseHigher(String filename) {
		addParallel(new PathFinderReadInverted(filename));
		addSequential(new Delay(delay));	
		addSequential(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
    }
    
    // For testing purposes
    public AutoPathFinderInvertedToScaleCloseHigher() {
		addSequential(new Delay(delay));	
		addSequential(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
    }
}
