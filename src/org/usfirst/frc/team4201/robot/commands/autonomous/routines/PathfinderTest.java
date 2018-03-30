package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		addSequential(new AutoPathFinderInvertedToScaleClose("rightStartToRightScaleReverse", false, 2));
		addSequential(new AutoShootCube());
		
		addSequential(new AutoSetIntakingPosition());
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderRead("rightScaleToCubeOneReverse", false, 4));
		
		addSequential(new AutoGrabCube());
		
		addSequential(new AutoPathFinderInvertedToScaleClose("cubeOneToRightScaleReverse", false, 4));
		addSequential(new AutoShootCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}