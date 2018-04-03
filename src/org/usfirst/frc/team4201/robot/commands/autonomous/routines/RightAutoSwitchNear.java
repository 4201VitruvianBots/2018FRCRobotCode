package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class RightAutoSwitchNear extends CommandGroup{
	
	public RightAutoSwitchNear() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			addSequential(new AutoPathFinderInvertedToSwitch("rightStartToRightSwitchReverseOne", true));
			addSequential(new PathFinderRead("rightStartToRightSwitchReverseTwo"));
		} else {
			addSequential(new PathFinderReadInverted("rightStartToLeftSwitchNearReverseOne", true));
			addSequential(new AutoPathFinderToSwitch("rightStartToLeftSwitchNearReverseTwo"));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
