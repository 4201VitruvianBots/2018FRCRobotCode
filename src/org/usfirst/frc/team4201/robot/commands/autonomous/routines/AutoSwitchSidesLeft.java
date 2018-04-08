package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoSwitchSidesLeft extends CommandGroup {
	String[] leftPaths = {
		"leftStartToLeftSwitchReverseOne",
		"leftStartToLeftSwitchReverseTwo",
		"leftStartToRightSwitchNearReverseOne",
		"leftStartToRightSwitchNearReverseTwo",
		"leftStartToRightSwitchFarReverseOne",
		"leftStartToRightSwitchFarReverseTwo"
	};
	
	public AutoSwitchSidesLeft(boolean near) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addParallel(new AutoSetArmSetpointDelayed(-55, 0.5));
			addSequential(new AutoPathFinderInvertedToSwitch(leftPaths[0], true));
			addParallel(new AutoSetWristScaleScoring(0, false));
			addSequential(new PathFinderRead(leftPaths[1]));
		} else {
			addParallel(new AutoSetArmSetpointDelayed(-55, 0.5));
			addSequential(new PathFinderReadInverted(near ? leftPaths[2] : leftPaths[4], true));
			addParallel(new AutoSetWristScaleScoring(0, false));
			addSequential(new AutoPathFinderToSwitch(near ? leftPaths[3] : leftPaths[5]));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
