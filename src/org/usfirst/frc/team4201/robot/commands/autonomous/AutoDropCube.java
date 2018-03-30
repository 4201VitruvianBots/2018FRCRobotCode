package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDropCube extends CommandGroup {

    public AutoDropCube() {
		addSequential(new SetIntakePistonsOpen());
		addSequential(new SetIntakePressureLow());
		addSequential(new Delay(0.2));
    }
}
