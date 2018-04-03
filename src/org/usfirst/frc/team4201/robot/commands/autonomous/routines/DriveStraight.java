package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class DriveStraight extends CommandGroup{
	
	public DriveStraight() {
		addSequential(new PathFinderRead("driveStraight", true));
	}
}
 