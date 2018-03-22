package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAutoSemiAutomatic extends CommandGroup{
	
	public CenterAutoSemiAutomatic() {
		//addParallel(new SetWristArmElevatorSetpoints(0, 0, 0));
		addParallel(new AutoManualElevatorControl(1, 2));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L'){
			addSequential(new PathFinderRead("centerAutoLeft", true, 2));
		} else {
			addSequential(new PathFinderRead("centerAutoRight", true, 2));
		}

		addSequential(new AutoManualWristControl(-0.75, 0.7));
		addSequential(new SetIntakePistonsOpen());
		addSequential(new Delay(1));
		addSequential(new SetIntakePistonsClose());
	}
}
 