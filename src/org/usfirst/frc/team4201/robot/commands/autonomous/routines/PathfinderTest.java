package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class PathfinderTest extends CommandGroup{
	
	public PathfinderTest() {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpoint(60, 0.5));	// 3.5 for cross autos
		addSequential(new PathFinderReadInverted("rightStartToRightScaleReverse", false, 2, 1.2));

		addSequential(new AutoSetWristRelativeSetpoint(135, true));
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		addSequential(new AutoSetIntakeMotorOutputs(-0.75, 0.65));
		addSequential(new AutoSetWristAbsoluteSetpoint(110));
		
		//addParallel(new AutoSetElevatorSetpoint();		
		addParallel(new SetIntakePistonsOpen());
		addSequential(new AutoSetArmSetpoint(-58));
		addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
		addSequential(new AutoSetWristAbsoluteSetpoint(0, true));
		addSequential(new PathFinderRead("rightScaleToCubeOneReverse", false, 4));
		
		addSequential(new SetIntakePistonsClose());
		addSequential(new Delay(0.25));					// Delay to allow pistons to grab cube
		addSequential(new AutoSetWristAbsoluteSetpoint(110));
		
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1));
		addParallel(new AutoSetArmSetpoint(60));
		addSequential(new PathFinderReadInverted("cubeOneToRightScaleReverse", false, 4));
		
		addSequential(new AutoSetWristRelativeSetpoint(135, true));
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		addSequential(new AutoSetIntakeMotorOutputs(-0.75, 0.65));
		addSequential(new AutoReleaseWristSetpoint());
		//*/
	}
}