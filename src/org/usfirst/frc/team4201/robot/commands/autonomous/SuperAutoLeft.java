package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.commands.*;

public class SuperAutoLeft extends CommandGroup{
	
	public SuperAutoLeft() {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L'){
			// score at scale (ideal):
			// parallel: move arm/wrist/intake to scale
			addParallel(new SetWristArmElevatorSetpoints(0, -60, 0));
			// sequential: Pathfinder - Move to scale (ideal)
			addSequential(new PathFinderRead("leftStartToLeftScale"));
			// sequential: score
			addSequential(new SetIntakeMotorIndividualOutputs(1, 0.75));
			addSequential(new Delay(0.1));
			if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L'){
				// intake and score at switch (ideal), then intake and score at scale (ideal)
				// Parallel: move/arm/wrist/intake to intake position
				addParallel(new SetWristArmElevatorSetpoints(0, 0, 0));
				// Parallel: Activate intake motors
				addParallel(new SetIntakeMotorOutputs(-0.75));
				// sequential: Pathfinder - Move to cube 1
				addSequential(new PathFinderRead("leftScaleToCubeOne"));
				
				// sequential: move/arm/wrist/intake to switch
				addSequential(new SetWristArmElevatorSetpoints(0, 0, 0));
				// sequential score
				addSequential(new SetIntakeMotorOutputs(0.75));
				addSequential(new Delay(0.1));
				
				// Parallel: move/arm/wrist/intake to intake position
				addSequential(new SetWristArmElevatorSetpoints(0, 0, 0));
				// Parallel: Activate intake motors
				addParallel(new SetIntakeMotorOutputs(-0.75));
				// sequential: Pathfinder - Move to cube 2
				addSequential(new PathFinderRead("backupFromSwitch"));
				addSequential(new PathFinderRead("backupToCubeTwo"));
				
				// parallel: move arm/wrist/intake to scale
				addParallel(new SetWristArmElevatorSetpoints(0, 0, 0));
				// sequential: Pathfinder - Move to scale
				// sequential: score
			} else {
				// intake and score at scale (ideal), then intake:
				// Parallel: move/arm/wrist/intake to intake position
				// Parallel: Activate intake motors
				
				// sequential: Pathfinder - Move to cube 1
				// parallel: move arm/wrist/intake to scale
				// sequential: Pathfinder - Move to scale
				// sequential: score
				
				// Parallel: move/arm/wrist/intake to intake position
				// Parallel: Activate intake motors
				// sequential: Pathfinder - Move to cube 2
			}
		} else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L'){
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
		} else {
			// score at scale (non-ideal):
			// parallel: move arm/wrist/intake to scale
			// sequential: Pathfinder - Move to scale (non-ideal)
			// sequential: score
			
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
