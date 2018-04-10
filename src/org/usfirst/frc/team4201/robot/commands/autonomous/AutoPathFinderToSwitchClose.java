package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderToSwitchClose extends CommandGroup {
	double elevatorSetpoint = 24.5, wristSetpoint = 0;
	
    public AutoPathFinderToSwitchClose(String filename, boolean first, double maxVel, double kP) {
		addSequential(new AutoSetElevatorWristSetpoints(elevatorSetpoint, wristSetpoint));
		addSequential(new PathFinderReadInverted(filename, first, maxVel, kP));
    }
    
    public AutoPathFinderToSwitchClose(String filename, boolean first, double maxVel) {
		addSequential(new AutoSetElevatorWristSetpoints(elevatorSetpoint, wristSetpoint));
		addSequential(new PathFinderReadInverted(filename, first, maxVel));
    }
    
    public AutoPathFinderToSwitchClose(String filename, boolean first) {
		addSequential(new AutoSetElevatorWristSetpoints(elevatorSetpoint, wristSetpoint));
		addSequential(new PathFinderReadInverted(filename, first));
    }
    
    public AutoPathFinderToSwitchClose(String filename) {
		addSequential(new AutoSetElevatorWristSetpoints(elevatorSetpoint, wristSetpoint));
		addSequential(new PathFinderReadInverted(filename));
    }
}
