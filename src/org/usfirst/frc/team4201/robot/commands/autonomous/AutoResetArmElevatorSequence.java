package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoResetArmElevatorSequence extends CommandGroup {
	
	public AutoResetArmElevatorSequence() {
		addSequential(new AutoSetElevatorSetpoint(3.8));
		addSequential(new AutoSetArmSetpoint(-60));
    }
}
