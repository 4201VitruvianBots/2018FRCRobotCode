package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetWristArmElevatorSetpoints extends CommandGroup {

    public SetWristArmElevatorSetpoints(double wristSetpoint, double armSetpoint, double elevatorSetpoint) {
    	addParallel(new SetWristDeltaSetpoint(wristSetpoint));
    	addParallel(new SetArmSetpoint(armSetpoint));
    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    }
}
