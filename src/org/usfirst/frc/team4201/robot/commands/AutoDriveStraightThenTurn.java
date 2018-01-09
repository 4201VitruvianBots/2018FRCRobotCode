package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveStraightThenTurn extends CommandGroup {

    public AutoDriveStraightThenTurn() {
        addSequential(new DriveStraightWithGyro(5, 0.5));
        addSequential(new DriveTurnWithGyro(0, 90));
    }
}
