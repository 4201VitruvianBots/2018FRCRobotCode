package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoSwitchSides extends CommandGroup {
	String[] leftPaths = {
		"leftStartToLeftSwitchReverseOne",
		"leftStartToLeftSwitchReverseTwo",
		"leftStartToRightSwitchFarReverseOne",
		"leftStartToRightSwitchFarReverseTwo",
		"leftStartToRightSwitchNearReverseOne",
		"leftStartToRightSwitchNearReverseTwo"
	}, rightPaths = {
		"rightStartToRightSwitchReverseOne",
		"rightStartToRightSwitchReverseTwo",
		"rightStartToLeftSwitchFarReverseOne",
		"rightStartToLeftSwitchFarReverseTwo",
		"rightStartToLeftSwitchNearReverseOne",
		"rightStartToLeftSwitchNearReverseTwo"
	};
	
	public AutoSwitchSides(boolean path, boolean near) {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderInvertedToSwitch(path ? (near ? leftPaths[0] : leftPaths[4]) : (near ? rightPaths[0] : rightPaths[4]), true));
			addSequential(new PathFinderRead(path ? (near ? leftPaths[1] : leftPaths[5]) : (near ? rightPaths[1] : rightPaths[5])));
		} else {
			addSequential(new PathFinderReadInverted(path ? (near ? leftPaths[2] : leftPaths[6]) : (near ? rightPaths[2] : rightPaths[6]), true));
			addSequential(new AutoPathFinderToSwitch(path ? (near ? leftPaths[3] : leftPaths[7]) : (near ? rightPaths[3] : rightPaths[7])));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
