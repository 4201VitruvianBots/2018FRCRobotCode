package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		// 1a. Move the Arm/Elevator to a given scoring position, in this case, the default switch scoring setpoints
		
		// 1b. At the same time, move to the correct scoring platform by reading the FMS data
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderToSwitch("centerAutoLeft", true));
		} else {
			addSequential(new AutoPathFinderToSwitch("centerAutoRight", true));
		}

		// 2. Sequence of actions to score by dropping the cube 
		addSequential(new AutoDropCube());

		addSequential(new AutoReleaseWristSetpoint());
	}
}
 