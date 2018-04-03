package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class LeftAutoSwitchFar extends CommandGroup {
	
	public LeftAutoSwitchFar() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderInvertedToSwitch("leftStartToLeftSwitchReverseOne", true));
			addSequential(new PathFinderRead("leftStartToLeftSwitchReverseTwo"));
		} else {
			addSequential(new PathFinderReadInverted("leftStartToRightSwitchFarReverseOne", true));
			addSequential(new AutoPathFinderToSwitch("leftStartToRightSwitchFarReverseTwo"));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
