package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class LeftAutoSwitchNear extends CommandGroup {
	
	public LeftAutoSwitchNear() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderInvertedToSwitch("leftStartToLeftSwitchReverseOne", true));
			addSequential(new PathFinderRead("leftStartToLeftSwitchReverseTwo"));
		} else {
			addSequential(new PathFinderReadInverted("leftStartToRightSwitchNearReverseOne", true));
			addSequential(new AutoPathFinderToSwitch("leftStartToRightSwitchNearReverseTwo"));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
