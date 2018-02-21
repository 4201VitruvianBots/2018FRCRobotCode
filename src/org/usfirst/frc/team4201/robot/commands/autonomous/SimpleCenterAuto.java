package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class SimpleCenterAuto extends CommandGroup{
	
	public SimpleCenterAuto() {
		//addParallel(new SetWristArmElevatorSetpoints(0, 0, 0));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L')
			addSequential(new PathFinderRead("centerToLeftSwitch"));
		else
			addSequential(new PathFinderRead("centerToRightSwitch"));
		//addSequential(new SetIntakeMotorOutputs(0.75));
	}
}
 