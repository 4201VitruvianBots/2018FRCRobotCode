package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addParallel(new AutoSetArmSetpoint(60));
		addSequential(new PathFinderRead("rightStartToLeftScale", false));
		
		addSequential(new AutoSetWristRelativeSetpoint(45));
		addSequential(new AutoSetIntakeMotorOutputs(-1, 2));
		addSequential(new AutoReleaseWristSetpoint());
	}
}
 