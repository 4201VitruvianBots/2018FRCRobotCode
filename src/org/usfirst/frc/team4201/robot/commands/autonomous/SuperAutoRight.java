package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.*;

public class SuperAutoRight extends CommandGroup{
	
	public SuperAutoRight() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R'){
			// Move to Scale 
			addParallel(new AutoSetArmElevatorSetpoints(52, 12));
			// sequential: Pathfinder - Move to scale (ideal)
			addSequential(new PathFinderRead("straightCalibration"));
			
			// Score at scale
			addSequential(new AutoSetWristRelativeSetpoint(180));
			addSequential(new AutoSetIntakeMotorOutputs(-0.75, 1));
			addSequential(new AutoReleaseWristSetpoint());
			addSequential(new SetIntakeMotorOutputs(0));

			if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') { /*
				// Reverse and intake cube one
				addParallel(new ToggleCubeIntakeWithRetraction());
				addSequential(new PathFinderRead("rightScaleToCubeOne"));
				
				// Move elevator up and score in switch
				addSequential(new SetArmElevatorSetpoints(-55, 12));
				addSequential(new HoldWristSetpoint());
				addSequential(new SetWristSetpoint(0));
				addSequential(new SetIntakeMotorOutputs(-0.5));
				addSequential(new Delay(0.1));
				addSequential(new ReleaseWristSetpoint());
				
				// Reverse and then go forward to intake cube two
				addSequential(new PathFinderRead("cubeOneToRightHold"));
				addParallel(new ToggleCubeIntakeWithRetraction());
				addSequential(new PathFinderRead("rightHoldToCubeTwo"));
				
				// reverse and score at the scale again
				addParallel(new SetArmElevatorSetpoints(50, 0));
				addSequential(new PathFinderRead("cubeTwoToRightScale"));

				// reverse and intake cube three
				addParallel(new ToggleCubeIntakeWithRetraction());
				addSequential(new PathFinderRead("rightScaleToCubeThree"));
				//*/
			} else {
				// Reverse and intake cube one
				addParallel(new ToggleCubeIntakeWithRetraction());
				addSequential(new PathFinderRead("cubeOneToRightScale"));

				// reverse to scale
				addParallel(new SetArmElevatorSetpoints(50, 0));
				addSequential(new PathFinderRead("cubeOneToRightScale"));
				
				// Score at scale
				addSequential(new HoldWristSetpoint());
				addSequential(new SetWristRelativeSetpoint(180));
				addSequential(new SetIntakeMotorOutputs(-0.5));
				addSequential(new Delay(0.1));
				addSequential(new ReleaseWristSetpoint());
			}
		} else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			// score at switch (ideal), then intake and score at scale (non-ideal) then intake
			
			// Parallel: move/arm/wrist/intake to intake position
			// Parallel: Activate intake motors
			// sequential: Pathfinder - Move to cube 1 (Curve)

			// parallel: move arm/wrist/intake to scale
			// sequential: Pathfinder - Move to scale (non-ideal)
			// sequential: score
			
			// Parallel: move/arm/wrist/intake to intake position
			// Parallel: Activate intake motors
			// sequential: Pathfinder - Move to cube 6
		} else { // If both scoring platforms are opposite of your starting position
			// score at scale (non-ideal):
			// parallel: move arm/wrist/intake to scale
			addSequential(new PathFinderRead("rightStartToLeftScaleOne"));
			//addSequential(new PathFinderRead("rightStartToLeftScaleTwo"));
			
			// Parallel: move/arm/wrist/intake to intake position
			// Parallel: Activate intake motors
			// sequential: Pathfinder - Move to cube 6
			
			// sequential: move/arm/wrist/intake to switch
			// sequential score
			
			// Parallel: move/arm/wrist/intake to intake position
			// Parallel: Activate intake motors
			// sequential: Pathfinder - Move to cube 5
			
			// parallel: move arm/wrist/intake to scale
			// sequential: Pathfinder - Move to scale
			// sequential: score
		}
	}
}
