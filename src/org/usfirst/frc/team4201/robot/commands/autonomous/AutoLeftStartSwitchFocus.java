package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoLeftStartSwitchFocus extends CommandGroup{
	
	public AutoLeftStartSwitchFocus() {
		addParallel(new SetArmElevatorSetpoints(-55, 6.25));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addParallel(new AutoSetArmElevatorSetpoints(52, 12));
			addSequential(new PathFinderRead("leftStartToLeftSwitch", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
		} else {
			//addParallel(new AutoSetArmElevatorSetpoints(52, 12));
			//addSequential(new PathFinderRead("leftStartToRightSwitch", true));
		}
	}
}
