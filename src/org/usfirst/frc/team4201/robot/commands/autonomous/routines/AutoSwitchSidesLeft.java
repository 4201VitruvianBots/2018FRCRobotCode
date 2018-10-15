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
		"leftStartToRightSwitchFarReverseTwo",
		"driveStraight"
	};
	
	public AutoSwitchSidesLeft(boolean near) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addParallel(new AutoSetArmSetpointDelayed(-55, 0.5));
			addSequential(new PathFinderReadInverted(leftPaths[0], true, 1.8));
			addSequential(new PathFinderRead(leftPaths[1], false, 1.8));
			addSequential(new AutoSetWristScaleScoring(0, false));
			addSequential(new Delay(0.1));
			addSequential(new AutoDropCube());
			addSequential(new AutoReleaseWristSetpoint());
		} else{
			addSequential(new PathFinderReadInverted(leftPaths[0], true, 1.8));
		}
		
	}
}
