package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class CenterAutoSemiAutomatic extends CommandGroup{
	
	public CenterAutoSemiAutomatic() {
		addParallel(new AutoManualElevatorControl(1, 2));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new PathFinderRead("centerAutoLeft", true));
		} else {
			addSequential(new PathFinderRead("centerAutoRight", true));
		}
		if(RobotMap.WristState == 0)
			addSequential(new AutoSetWristToAbsoluteZero());
		else
			addSequential(new AutoManualWristControl(-0.5, 0.75));
			
		addSequential(new AutoDropCube());

		addSequential(new AutoReleaseWristSetpoint());
	}
}
 