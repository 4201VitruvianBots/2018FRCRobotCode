package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class RightAutoDoubleScale extends CommandGroup{
	
	public RightAutoDoubleScale() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R') {
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("rightStartToRightScale", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
			
			/*
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));		// Make an AutoEnableCubeIntake
			addSequential(new PathFinderRead("rightScaleToCubeOne"));
			addSequential(new Delay(0.5));
			addSequential(new AutoRetractIntake());
			
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("cubeOneToRightScale"));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
			*/
		} else {
			addParallel(new AutoSetArmElevatorSetpoints(4, 25));
			addSequential(new PathFinderRead("rightStartToLeftScale", true));

			/*
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));		// Make an AutoEnableCubeIntake
			addSequential(new PathFinderRead("leftScaleToCubeSix"));
			addSequential(new Delay(0.5));
			addSequential(new AutoRetractIntake());
			
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("cubeSixToLeftScale"));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
			*/
		}
	}
}
