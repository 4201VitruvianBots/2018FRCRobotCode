package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoDoubleScale extends CommandGroup{
	String[] leftPaths = {
		"leftStartToLeftScale",
		"scaleToEdgeCubeLeft",
		"edgeCubeToScaleShoot",
		"leftStartToRightScale",
		"rightScaleToEdgeCubeFar",
		"edgeCubeFarToScale"
	}, rightPaths = {
		"rightStartToLeftScale",
		"leftScaleToEdgeCubeFar",
		"edgeCubeFarToScale",
		"rightStartToRightScale",
		"scaleToEdgeCubeRight",
		"edgeCubeToScaleShoot"
	};
	
	public AutoDoubleScale(boolean path) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {		
			addSequential(new AutoPathFinderInvertedToScaleClose(path ? leftPaths[0] : rightPaths[0], true));
			
			addSequential(new AutoSetWristScaleScoring(135, true));
			addSequential(new Delay(0.1));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			addSequential(new AutoIntakeCube(path ? leftPaths[1] : rightPaths[1], false, 1.75));
			addSequential(new AutoGrabCube());
			addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
				
			addSequential(new AutoPathFinderInvertedToScaleCloseHigher(path ? leftPaths[2] : rightPaths[2], false, 4));
			addSequential(new AutoSetWristScaleScoring(135, true));
			addSequential(new AutoShootCube(-1, 0.5));
		} else {
			addSequential(new AutoPathFinderInvertedToScaleFar(path ? leftPaths[3] : rightPaths[3], true));
		
			addSequential(new AutoSetWristScaleScoring(135, true));
			addSequential(new Delay(0.1));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			addSequential(new AutoIntakeCube(path ? leftPaths[4] : rightPaths[4], false, 1));
			addSequential(new AutoGrabCube());
			addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
				
			addSequential(new AutoPathFinderInvertedToScaleCloseHigher(path ? leftPaths[5] : rightPaths[5], false, 4));
			addSequential(new AutoSetWristScaleScoring(135, true));
			addSequential(new AutoShootCube(-1, 0.5));
		}
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
