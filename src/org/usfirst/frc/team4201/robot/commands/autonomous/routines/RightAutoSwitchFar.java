package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class RightAutoSwitchFar extends CommandGroup{
	
	public RightAutoSwitchFar() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			addSequential(new AutoPathFinderInvertedToSwitch("rightStartToRightSwitchReverseOne", true));
			addSequential(new PathFinderRead("rightStartToRightSwitchReverseTwo"));
		} else {
			addSequential(new PathFinderReadInverted("rightStartToLeftSwitchFarReverseOne", true));
			addSequential(new AutoPathFinderToSwitch("rightStartToLeftSwitchFarReverseTwo"));
		}
		
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
