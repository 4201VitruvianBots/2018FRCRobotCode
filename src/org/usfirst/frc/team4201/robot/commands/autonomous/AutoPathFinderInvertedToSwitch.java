package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderInvertedToSwitch extends CommandGroup {
	double elevatorSetpoint = 24.5, wristSetpoint = 0;

    public AutoPathFinderInvertedToSwitch(String filename, boolean first, double maxVel, double kP) {
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename, boolean first, double maxVel) {
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename, boolean first) {
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename) {
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
}
