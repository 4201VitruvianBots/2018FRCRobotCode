package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class LeftAutoDoubleScale extends CommandGroup{
	
	public LeftAutoDoubleScale() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {		
			addSequential(new AutoPathFinderInvertedToScaleClose("leftStartToLeftScale", true));
		} else {
			addSequential(new AutoPathFinderInvertedToScaleFar("leftStartToRightScale", true));
		}
		addSequential(new AutoShootCube(-0.8, 0.6));
		
		addSequential(new AutoSetIntakingPosition());
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderRead("scaleToEdgeCube", false, 4));
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new AutoPathFinderInvertedToScaleClose("edgeCubeToScale", false, 4));
		addSequential(new AutoShootCube(-0.75, 0.75));
		
		/*
		addSequential(new AutoSetIntakingPosition());
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderRead("scaleToEdgeCube", false, 4));
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new AutoPathFinderInvertedToScaleClose("edgeCubeToScale", false, 4));
		addSequential(new AutoShootCube(-0.75, 0.75));
		//*/
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
