package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		addParallel(new SetArmElevatorSetpoints(-45, 25));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderRead("centerToLeftSwitch", true, 2));
		} else {
			addSequential(new PathFinderRead("centerToRightSwitch", true, 2));
		}

		addSequential(new AutoSetWristRelativeSetpoint(0));
		addSequential(new AutoSetIntakeMotorOutputs(-0.5, 1));
		addSequential(new AutoReleaseWristSetpoint());
	}
}
 