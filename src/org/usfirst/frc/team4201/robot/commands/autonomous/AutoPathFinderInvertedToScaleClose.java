package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderInvertedToScaleClose extends CommandGroup {
	double armSetpoint = 0, armDelay = 0.5, wristSetpoint = 135;
	boolean wristRelative = true;
	
	public AutoPathFinderInvertedToScaleClose(String filename, boolean first, double maxVel, double kP) {
		addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetWristScaleScoring(wristSetpoint, wristRelative));
    }
    
    public AutoPathFinderInvertedToScaleClose(String filename, boolean first, double maxVel) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetWristScaleScoring(wristSetpoint, wristRelative));
    }
    
    public AutoPathFinderInvertedToScaleClose(String filename, boolean first) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetWristScaleScoring(wristSetpoint, wristRelative));
    }
    
    public AutoPathFinderInvertedToScaleClose(String filename) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetWristScaleScoring(wristSetpoint, wristRelative));;
    }
    
    // For testing purposes
    public AutoPathFinderInvertedToScaleClose() {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new AutoSetWristScaleScoring(wristSetpoint, wristRelative));
    }
}
