package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSetArmElevatorSetpoints extends CommandGroup {

    public AutoSetArmElevatorSetpoints(double armSetpoint, double elevatorSetpoint) {
    	addParallel(new AutoSetArmSetpoint(armSetpoint));
    	addSequential(new AutoSetElevatorSetpoint(elevatorSetpoint));
    }
}
