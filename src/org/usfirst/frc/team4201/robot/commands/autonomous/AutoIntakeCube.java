package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoIntakeCube extends CommandGroup{
	
	public AutoIntakeCube(String filename, boolean first, double maxVel) {
		addParallel(new SetIntakePressureLow());
		addParallel(new SetIntakePistonsOpen());
		addParallel(new AutoSetArmSetpoint(-61));
		addParallel(new AutoSetWristToAbsoluteZero());
		addParallel(new PathFinderRead(filename, first, maxVel));
	}
}