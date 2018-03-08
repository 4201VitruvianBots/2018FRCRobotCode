package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		//addParallel(new SetWristArmElevatorSetpoints(0, 0, 0));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L')
			addSequential(new PathFinderRead("centerToLeftSwitch", true, 3));
		else
			addSequential(new PathFinderRead("centerToRightSwitch", true, 3));
		//addSequential(new SetIntakeMotorOutputs(0.75));
	}
}
 