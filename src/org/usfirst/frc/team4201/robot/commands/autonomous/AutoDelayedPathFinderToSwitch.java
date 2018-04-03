package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDelayedPathFinderToSwitch extends CommandGroup {
	double elevatorSetpoint = 25, wristSepoint = 0;
	
    public AutoDelayedPathFinderToSwitch(String filename, boolean first, double maxVel, double kP) {
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetWristSwitchScoring(wristSepoint));
    }
    
    public AutoDelayedPathFinderToSwitch(String filename, boolean first, double maxVel) {
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetWristSwitchScoring(wristSepoint));
    }
    
    public AutoDelayedPathFinderToSwitch(String filename, boolean first) {
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetWristSwitchScoring(wristSepoint));
    }
    
    public AutoDelayedPathFinderToSwitch(String filename) {
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetWristSwitchScoring(wristSepoint));
    }
}
