package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.Delay;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		// 1a. Move the Arm/Elevator to a given scoring position, in this case, the default switch scoring setpoints
		addParallel(new AutoSetArmSetpoint(-55));
		// 1b. At the same time, move to the correct scoring platform by reading the FMS data
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderToSwitch("centerAutoLeft", true, 1.5));
		} else {
			addSequential(new AutoPathFinderToSwitch("centerAutoRight", true, 1.5));
		}

		addSequential(new AutoSetWristScaleScoring(0, false));
		addSequential(new Delay(0.05));
		// 2. Sequence of actions to score by dropping the cube 
		addSequential(new AutoDropCube());

		addSequential(new AutoReleaseWristSetpoint());
		
		addParallel(new AutoResetArmElevatorSequence());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderReadInverted("switchBackLeft", false, 1.8));
		} else {
			addSequential(new PathFinderReadInverted("switchBackRight", false, 1.8));
		}
		addSequential(new AutoSetWristScaleScoring(0, false));
		
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new PathFinderRead("centerAutoGrabCube", false, 2));
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new PathFinderReadInverted("centerAutoGrabCubeReverse", false, 1.8));
		
		addParallel(new AutoSetArmSetpoint(-55));

		addSequential(new AutoReleaseWristSetpoint());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addSequential(new PathFinderRead("centerAutoLeftFaster", false, 1.8));
		} else {
			addSequential(new PathFinderRead("centerAutoRightFaster", false, 1.8));
		}
		
		/*
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderToSwitch("centerAutoLeftFaster", false, 1.8));
		} else {
			addSequential(new AutoPathFinderToSwitch("centerAutoRightFaster", false, 1.8));
		}
		/*
		addSequential(new AutoSetWristScaleScoring(0, false));
		addSequential(new Delay(0.05));
		addSequential(new AutoDropCube());
		 */
	}
}
 