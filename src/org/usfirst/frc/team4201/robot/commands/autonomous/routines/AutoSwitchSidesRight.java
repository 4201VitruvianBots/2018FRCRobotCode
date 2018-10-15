package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoSwitchSidesRight extends CommandGroup {
	String[] rightPaths = {
		"rightStartToRightSwitchReverseOne",
		"rightStartToRightSwitchReverseTwo",
		"rightStartToLeftSwitchNearReverseOne",	//No
		"rightStartToLeftSwitchNearReverseTwo", //No
		"rightStartToLeftSwitchFarReverseOne", //No
		"rightStartToLeftSwitchFarReverseTwo", //No
		"driveStraight"
	};
	
	public AutoSwitchSidesRight(boolean near) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			addParallel(new AutoSetArmSetpointDelayed(-55, 0.5));
			addSequential(new PathFinderReadInverted(rightPaths[0], true, 1.8));
			addSequential(new PathFinderRead(rightPaths[1], false, 1.8));
			addSequential(new AutoSetWristScaleScoring(0, false));
			addSequential(new Delay(0.1));
			addSequential(new AutoDropCube());
			addSequential(new AutoReleaseWristSetpoint());
		} else {
			addSequential(new PathFinderReadInverted(rightPaths[0], true, 1.8));
		}
	}
}
