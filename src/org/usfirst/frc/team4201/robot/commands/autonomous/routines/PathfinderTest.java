package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		//addSequential(new PathFinderReadInverted("leftStartToRightSwitchFarReverseOne", false));
		//addSequential(new PathFinderReadInverted("leftStartToRightScale", false));
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		addSequential(new AutoPathFinderInvertedToScaleClose("leftStartToLeftScale", true));
		/*
		addSequential(new AutoSetWristScaleScoring(120, true));
		addSequential(new Delay(0.25));
		addSequential(new AutoShootCube(-1, 0.75));
		
		addSequential(new AutoSetIntakingPosition());
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderReadInverted("leftScaleToCubeOneReverse", false, 4));
		
		/*
		//addSequential(new AutoPathFinderInvertedToScaleClose("rightStartToRightScaleReverse", false, 2));
		addSequential(new AutoPathFinderInvertedToScaleClose("leftStartToLeftScaleReverse", false, 2));
		addSequential(new AutoShootCube(-0.8, 0.6));
		
		addSequential(new AutoSetIntakingPosition());
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderRead("rightScaleToCubeOneReverse", false, 4));
		
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		addSequential(new AutoPathFinderInvertedToScaleClose("cubeOneToRightScaleReverse", false, 4));
		addSequential(new AutoShootCube(-0.75, 0.75));
		
		addSequential(new AutoReleaseWristSetpoint());
		//*/
	}
}