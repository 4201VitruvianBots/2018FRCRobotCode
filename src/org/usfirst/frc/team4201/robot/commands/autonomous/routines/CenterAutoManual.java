package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAutoManual extends CommandGroup{
	
	public CenterAutoManual() {
		addParallel(new AutoManualElevatorControl(1, 2));
		/*if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L'){
			addSequential(new PathFinderRead("centerToLeftSwitch", true, 2));
		} else {
			addSequential(new PathFinderRead("centerToRightSwitch", true, 2));
		}
		 */

		addSequential(new AutoManualWristControl(-0.5, 0.75));
		addSequential(new AutoDropCube());
	}
}
 