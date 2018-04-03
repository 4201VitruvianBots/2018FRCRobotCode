package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSetElevatorWristSetpoints extends CommandGroup {

    public AutoSetElevatorWristSetpoints(double elevatorSetpoint, double wristSetpoint) {
		addParallel(new AutoSetElevatorSetpoint(elevatorSetpoint));
		addParallel(new AutoSetWristSwitchScoring(wristSetpoint));
    }
}
