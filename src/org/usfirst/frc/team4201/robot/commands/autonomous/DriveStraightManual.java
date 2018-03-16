package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class DriveStraightManual extends CommandGroup{
	
	public DriveStraightManual() {
		addSequential(new PathFinderRead("driveStraight", true, 2));
	}
}
 