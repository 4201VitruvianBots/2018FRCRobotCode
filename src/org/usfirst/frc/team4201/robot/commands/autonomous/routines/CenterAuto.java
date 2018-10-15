package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.Delay;
import org.usfirst.frc.team4201.robot.commands.SetIntakePistonsOpen;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAuto extends CommandGroup{
	
	public CenterAuto() {
		// 1a. Move the Arm/Elevator to a given scoring position, in this case, the default switch scoring setpoints
		// 1b. At the same time, move to the correct scoring platform by reading the FMS data
		
		// Remove Toast's bad idea if necessary
		addParallel(new AutoSetArmSetpoint(-25));
		//addParallel(new AutoSetElevatorSetpoint(8));
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderRead("centerAutoLeft", true, 1.6));
		} else {
			addSequential(new PathFinderRead("centerAutoRight", true, 1.6));
		}

		addSequential(new AutoSetWristScaleScoring(45, true));
		addSequential(new Delay(0.05));
		// 2. Sequence of actions to score by dropping the cube
		addSequential(new AutoShootCube(-0.5, 0.4));

		addSequential(new AutoReleaseWristSetpoint());
		
		//addParallel(new AutoSetElevatorSetpoint(3.8));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderReadInverted("switchBackLeft", false, 2));
		} else {
			addSequential(new PathFinderReadInverted("switchBackRight", false, 2));
		}
		addParallel(new AutoSetArmSetpoint(-60));
		addSequential(new AutoSetWristScaleScoring(-5, false));
		
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addParallel(new SetIntakePistonsOpen());
		addSequential(new PathFinderRead("centerAutoGrabCube", false, 2));
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));

		addSequential(new AutoReleaseWristSetpoint());
		addSequential(new PathFinderReadInverted("centerAutoGrabCubeReverse", false, 2));
		
		addSequential(new AutoReleaseWristSetpoint());
		
		// Remove Toast's bad idea if necessary
		addParallel(new AutoSetArmSetpoint(-25));
		//addParallel(new AutoSetElevatorSetpoint(8));
				
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderRead("centerAutoLeftFaster", false, 1.6));
		} else {
			addSequential(new PathFinderRead("centerAutoRightFaster", false, 1.6));
		}
		
		addSequential(new AutoSetWristScaleScoring(45, true));		// 64
		addSequential(new Delay(0.05));
		// 2. Sequence of actions to score by dropping the cube
		addSequential(new AutoShootCube(-1, 0.4));
		
		addSequential(new AutoReleaseWristSetpoint());
		

		// Remove Toast's bad idea if necessary
		addSequential(new AutoSetElevatorSetpoint(3.8));
		addSequential(new AutoSetArmSetpoint(-60));
	}
}
 