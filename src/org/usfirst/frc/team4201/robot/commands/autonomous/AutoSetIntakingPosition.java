package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoSetIntakingPosition extends CommandGroup{
	
	public AutoSetIntakingPosition() {
		addParallel(new SetIntakePistonsOpen());
		addParallel(new SetIntakePressureLow());
		addParallel(new AutoSetArmSetpoint(-58));
		addParallel(new AutoSetWristIntakePosition());
	}
}