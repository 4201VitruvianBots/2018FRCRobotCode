package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;

public class LeftAutoSwitchFar extends CommandGroup {
	
	public LeftAutoSwitchFar() {
		//addSequential(new Delay(0));
		addSequential(new SetIntakePistonsClose());
		addSequential(new SetIntakePressureHigh());
		
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new AutoPathFinderInvertedToSwitch("leftStartToLeftSwitchFar", false, 2));

			addSequential(new AutoDropCube());
			
			//Move to cube position
			
		} else {
			addSequential(new AutoPathFinderInvertedToSwitch("leftStartToRightSwitchFar", false, 2));

			addSequential(new AutoDropCube());
			
			//Move to cube position
		}
	}
}
