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
			addSequential(new AutoPathFinderInvertedToSwitch(leftPaths[0], true));
			addSequential(new PathFinderRead(leftPaths[1]));
		} else {
			addSequential(new PathFinderReadInverted(near ? leftPaths[2] : leftPaths[4], true));
			addSequential(new AutoPathFinderToSwitch(near ? leftPaths[3] : leftPaths[5]));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
