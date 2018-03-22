package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoReleaseWristSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetArmSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetIntakeMotorOutputs;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetWristRelativeSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.PathFinderRead;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addParallel(new AutoSetArmSetpoint(60, 0));
		addSequential(new PathFinderRead("rightStartToLeftScale", false));
		
		addSequential(new AutoSetWristRelativeSetpoint(45));
		addSequential(new AutoSetIntakeMotorOutputs(-1, 2));
		addSequential(new AutoReleaseWristSetpoint());
	}
}
 