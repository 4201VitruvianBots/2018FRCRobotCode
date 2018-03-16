package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetArmElevatorSetpoints extends CommandGroup {

    public SetArmElevatorSetpoints(double armSetpoint, double elevatorSetpoint) {
    	addParallel(new SetArmSetpoint(armSetpoint));
    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    }
}
