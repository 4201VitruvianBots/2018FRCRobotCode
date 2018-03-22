package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoReleaseWristSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetArmElevatorSetpoints;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetIntakeMotorOutputs;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetWristRelativeSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.PathFinderRead;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		// 1a. Move the Arm/Elevator to a given scoring position, in this case, the default switch scoring setpoints
		addParallel(new AutoSetArmElevatorSetpoints(-45, 25));
		
		// 1b. At the same time, move to the correct scoring platform by reading the FMS data
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderRead("centerAutoLeft", true, 2));
		} else {
			addSequential(new PathFinderRead("centerAutoRight", true, 2));
		}

		// 2. Sequence of actions to score by launching the cube
		addSequential(new AutoSetWristRelativeSetpoint(0));
		addSequential(new AutoSetIntakeMotorOutputs(-0.5, 1));
		addSequential(new AutoReleaseWristSetpoint());
		
		// Sequence of actions to score by dropping the cube by retracting the intake pistons
		/*
		addSequential(new AutoSetWristRelativeSetpoint(0));
		addSequential(new SetIntakePistonsOpen());			
		addSequential(new Delay(1));						// This delay is so that the intake doesn't suddenly close/wrist goes to neutral position, preventing the cube from fully dropping
		addSequential(new AutoReleaseWristSetpoint());		// Due to how the UpdateWristSetpoint() default command works, we need a separate release command in auto 
		addSequential(new SetIntakePistonsClose());
		*/
	}
}
 