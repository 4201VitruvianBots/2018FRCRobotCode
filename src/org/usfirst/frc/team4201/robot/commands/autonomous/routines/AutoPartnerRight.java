package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class AutoPartnerRight extends CommandGroup{

	public AutoPartnerRight() {
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R') {		
			addSequential(new AutoPathFinderInvertedToScaleClose("rightStartToRightScalePartnerOne", true, 1.5));
			
			addSequential(new AutoSetWristScaleScoring(135, true));
			addSequential(new Delay(0.1));
			addSequential(new AutoShootCube(-1, 0.5));
				
			addSequential(new AutoReleaseWristSetpoint());
			
			addParallel(new AutoResetArmElevatorSequence());
		} else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R'){
			addSequential(new PathFinderReadInverted("rightStartToRightSwitchReverseOne", true, 1.5));
			addSequential(new PathFinderRead("rightStartToRightSwitchReverseTwo", false, 1.25));

			addSequential(new AutoSetWristScaleScoring(64, false));
			addSequential(new Delay(0.05));
			// 2. Sequence of actions to score by dropping the cube
			addSequential(new AutoShootCube(-1, 0.4));

			addSequential(new AutoReleaseWristSetpoint());
			
			addParallel(new AutoResetArmElevatorSequence());
		} else {
			addSequential(new PathFinderReadInverted("rightStartToLeftScalePartner", true, 1.5));
			addSequential(new DriveTurnWithGyro(90));
		}
		
		addSequential(new AutoReleaseWristSetpoint());
	}
}
