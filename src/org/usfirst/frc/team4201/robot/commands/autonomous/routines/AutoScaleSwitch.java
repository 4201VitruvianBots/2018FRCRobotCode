package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoScaleSwitch extends CommandGroup {
	String[] leftPaths = {
		"leftStartToLeftScale",
		"scaleToEdgeCube",
		"leftScaleToCubeOne",
		"leftStartToRightScale",
		"rightScaleToEdgeCubeFar",
		"rightScaleToCubeSix"
	}, rightPaths = {
		"rightStartToLeftScale",
		"leftScaleToCubeOne",
		"leftScaleToEdgeCubeFar",
		"rightStartToRightScale",
		"scaleToEdgeCube",
		"rightScaleToCubeSix"
	};
	
	public AutoScaleSwitch(boolean path) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addSequential(new AutoPathFinderInvertedToScaleClose(path ? leftPaths[0] : rightPaths[0], true));
			
			addSequential(new AutoSetWristScaleScoring(120, true));
			addSequential(new Delay(0.2));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' ){
				addSequential(new AutoIntakeCube(path ? leftPaths[1] : rightPaths[1], false, 1.75));
			} else {
				addParallel(new AutoSetIntakingPosition());
				addSequential(new PathFinderRead(path ? leftPaths[2] : rightPaths[2], false));
			}
		} else {
			addSequential(new AutoPathFinderInvertedToScaleFar(path ? leftPaths[3] : rightPaths[3], true));
			
			addSequential(new AutoSetWristScaleScoring(120, true));
			addSequential(new Delay(0.2));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' ){
				addSequential(new AutoIntakeCube(path ? leftPaths[4] : rightPaths[4], false, 4));	
			} else {
				addParallel(new AutoSetIntakingPosition());
				addSequential(new PathFinderRead(path ? leftPaths[1] : rightPaths[1], false));
			}
		}
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new AutoPathFinderToSwitchClose("edgeCubeToSwitch", false, 4));
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
