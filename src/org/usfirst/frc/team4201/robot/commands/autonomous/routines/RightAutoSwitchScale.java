package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class RightAutoSwitchScale extends CommandGroup{
	
	public RightAutoSwitchScale() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R') {
			addSequential(new AutoPathFinderInvertedToScaleClose("rightStartToRightScale", true));
			addSequential(new AutoShootCube(-0.8, 0.6));
			
			addSequential(new AutoSetIntakingPosition());
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' ){
				addSequential(new PathFinderRead("scaleToEdgeCube", false, 4));	
			} else {
				addSequential(new PathFinderRead("rightScaleToCubeSix", false));
			}
		} else {
			addSequential(new AutoPathFinderInvertedToScaleFar("rightStartToLeftScale", true));
			addSequential(new AutoShootCube(-0.8, 0.6));

			addSequential(new AutoSetIntakingPosition());
			addParallel(new AutoSetIntakeMotorOutputsContinouous(1));
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' ){
				addSequential(new PathFinderRead("scaleToEdgeCube", false, 4));	
			} else {
				addSequential(new PathFinderRead("leftScaleToCubeOne", false));
			}
		}
		addSequential(new AutoGrabCube());
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		
		addSequential(new AutoPathFinderToSwitchClose("edgeCubeToSwitch", false, 4));
		addSequential(new AutoDropCube());
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
