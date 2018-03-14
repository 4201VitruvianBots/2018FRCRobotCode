package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;

public class AutoLeftStartToScale extends CommandGroup{
	
	public AutoLeftStartToScale() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addParallel(new AutoSetArmElevatorSetpoints(46, 12));
			addSequential(new PathFinderRead("leftStartToLeftScale", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
		}/* 
		else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addParallel(new AutoSetArmElevatorSetpoints(4, 25));
			addSequential(new PathFinderRead("leftStartToRightScale", true));

			addSequential(new AutoSetWristRelativeSetpoint(0));
			addSequential(new SetIntakePistonsOpen());
			addSequential(new Delay(1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakePistonsClose());
		}
		*/
		else {
			addSequential(new PathFinderRead("driveStraight", true));
		}
	}
}
