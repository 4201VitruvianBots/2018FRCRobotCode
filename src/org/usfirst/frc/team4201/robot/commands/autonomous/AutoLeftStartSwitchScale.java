package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoLeftStartSwitchScale extends CommandGroup {
	
	public AutoLeftStartSwitchScale() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			// 1a. Score at the left switch platform if it is in front of you
			addParallel(new SetArmElevatorSetpoints(-55, 6.25));
			addSequential(new PathFinderRead("leftStartToLeftSwitch", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
			/*
			// 2. Intake cube six
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));		// Make an AutoEnableCubeIntake
			addSequential(new PathFinderRead("leftStartToCubeSix"));
			addSequential(new Delay(0.5));
			addSequential(new AutoRetractIntake());
			
			if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
				// 3a. If scale is also on the left, score there
				addParallel(new AutoSetArmElevatorSetpoints(46, 12));		// Make an AutoEnableCubeIntake
				addSequential(new PathFinderRead("cubeSixToLeftScale"));

				addSequential(new AutoSetWristRelativeSetpoint(0));
				addSequential(new SetIntakePistonsOpen());
				addSequential(new Delay(1));
				addSequential(new AutoReleaseWristSetpoint());
				addSequential(new SetIntakePistonsClose());
			} else {
				// 3b. Otherwise, score in the right scale
				addParallel(new AutoSetArmElevatorSetpoints(46, 12));		// Make an AutoEnableCubeIntake
				addSequential(new PathFinderRead("cubeSixToRightScale"));

				addSequential(new AutoSetWristRelativeSetpoint(0));
				addSequential(new SetIntakePistonsOpen());
				addSequential(new Delay(1));
				addSequential(new AutoReleaseWristSetpoint());
				addSequential(new SetIntakePistonsClose());
			}
			*/
		} else if (DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L' ){
			// 1b. If the left switch is not in front of you, but the scale is score at the scale first then right switch
			//addParallel(new AutoSetArmElevatorSetpoints(52, 12));
			//addSequential(new PathFinderRead("leftStartToRightSwitch", true));
			
		} else {
			// 1c. Otherwise, go for the left switch (?) then left scale (?) // Order TBD
		}
	}
}
