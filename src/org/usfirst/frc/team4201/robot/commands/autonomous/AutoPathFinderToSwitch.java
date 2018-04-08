package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderToSwitch extends CommandGroup {
	double elevatorSetpoint = 24.5, wristSetpoint = 0;
	
    public AutoPathFinderToSwitch(String filename, boolean first, double maxVel, double kP) {
		addParallel(new PathFinderRead(filename, first, maxVel, kP));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		//addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderToSwitch(String filename, boolean first, double maxVel) {
		addParallel(new PathFinderRead(filename, first, maxVel));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		//addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderToSwitch(String filename, boolean first) {
		addParallel(new PathFinderRead(filename, first));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		//addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
    
    public AutoPathFinderToSwitch(String filename) {
		addParallel(new PathFinderRead(filename));
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		//addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
}
