package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrabCube extends CommandGroup {

    public AutoGrabCube() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		addSequential(new Delay(0.2));		
    }
}
