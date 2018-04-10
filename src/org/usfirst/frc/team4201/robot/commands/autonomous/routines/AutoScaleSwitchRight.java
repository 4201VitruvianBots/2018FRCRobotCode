package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoScaleSwitchRight extends CommandGroup {
	String[] rightPaths = {
		"rightStartToRightScale",
		"scaleToEdgeCube",
		"rightScaleToCubeSix",
		"rightStartToLeftScale",
		"leftScaleToEdgeCubeFar",
		"leftScaleToCubeOne"
	};
	
	public AutoScaleSwitchRight() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addSequential(new AutoPathFinderInvertedToScaleClose(rightPaths[0], true));
			
			addSequential(new AutoSetWristScaleScoring(120, true));
			addSequential(new Delay(0.2));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' ){
				addSequential(new AutoIntakeCube(rightPaths[1], false, 1.75));
			} else {
				addParallel(new AutoSetIntakingPosition());
				addSequential(new PathFinderRead(rightPaths[2], false));
			}
		} else {
			addSequential(new AutoPathFinderInvertedToScaleFar(rightPaths[3], true));
			
			addSequential(new AutoSetWristScaleScoring(120, true));
			addSequential(new Delay(0.2));
			addSequential(new AutoShootCube(-1, 0.5));
			
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' ){
				addSequential(new AutoIntakeCube(rightPaths[4], false, 4));	
			} else {
				addParallel(new AutoSetIntakingPosition());
				addSequential(new PathFinderRead(rightPaths[5], false));
			}
		}
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new AutoPathFinderToSwitchClose("edgeCubeToSwitch", false, 4));
		addSequential(new AutoSetArmSetpoint(-55));
		addSequential(new AutoSetWristScaleScoring(0, false));
		addSequential(new Delay(0.1));
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
