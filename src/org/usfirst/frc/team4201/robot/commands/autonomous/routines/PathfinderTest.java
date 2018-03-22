package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addParallel(new AutoSetArmSetpoint(60, 0));
		addSequential(new PathFinderReadInverted("driveStraight", false));
		
		addSequential(new AutoSetWristRelativeSetpoint(45));
		addSequential(new AutoSetIntakeMotorOutputs(-1, 2));
		addSequential(new AutoReleaseWristSetpoint());
	}
}
 