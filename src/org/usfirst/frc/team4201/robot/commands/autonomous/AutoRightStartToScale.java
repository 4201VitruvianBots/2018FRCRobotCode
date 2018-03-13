package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoRightStartToScale extends CommandGroup{
	
	public AutoRightStartToScale() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R') {
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("rightStartToRightScale", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
		} else if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("rightStartToLeftScale", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
		} else {
			addSequential(new PathFinderRead("driveStraight", true));
		}
	}
}
