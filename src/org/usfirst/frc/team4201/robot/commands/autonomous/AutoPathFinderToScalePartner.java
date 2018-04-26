package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderToScalePartner extends CommandGroup {
	double armSetpoint = 30, armDelay = 0.5, wristSetpoint = 135;
	boolean wristRelative = true;
	
	public AutoPathFinderToScalePartner(String filename, boolean first, double maxVel, double kP) {
		addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
    }
    
    public AutoPathFinderToScalePartner(String filename, boolean first, double maxVel) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
    }
    
    public AutoPathFinderToScalePartner(String filename, boolean first) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename, first));
    }
    
    public AutoPathFinderToScalePartner(String filename) {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
		addParallel(new PathFinderReadInverted(filename));
    }
    
    // For testing purposes
    public AutoPathFinderToScalePartner() {
    	addParallel(new AutoSetArmSetpointDelayed(armSetpoint, armDelay));	
    }
}
