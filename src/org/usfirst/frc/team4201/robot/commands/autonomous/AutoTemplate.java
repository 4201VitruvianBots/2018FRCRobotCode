package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class AutoTemplate extends CommandGroup{
	public AutoTemplate() {
		 addSequential(new DriveTurnWithGyro(90)); 
	}
}
