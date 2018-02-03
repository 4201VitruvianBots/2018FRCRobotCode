package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4201.robot.AutoPaths;
import org.usfirst.frc.team4201.robot.commands.*;

public class PathFinderCommandGroup extends CommandGroup{
	public PathFinderCommandGroup() {
		 SmartDashboard.putString("Auto Status" , "Executing");
		 addSequential(new PathFinder(AutoPaths.rightRobotToRightSwitch));
		 SmartDashboard.putString("Auto Status" , "Exited");
	}
}
