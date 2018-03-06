package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class AutoCalibration extends CommandGroup{
	
	public AutoCalibration() {
		// Move to Scale 
		addParallel(new AutoSetArmElevatorSetpoints(52, 12));
		// sequential: Pathfinder - Move to scale (ideal)
		addSequential(new PathFinderRead("straightCalibration", true));
		
		// Score at scale
		addSequential(new AutoSetWristRelativeSetpoint(180));
		//addSequential(new AutoSetIntakeMotorOutputs(-0.75, 1));
		addSequential(new SetIntakePistonsOpen());
		addSequential(new AutoReleaseWristSetpoint());
		//addSequential(new AutoSetIntakeMotorOutputs(0, 0));

		addSequential(new Delay(2));
		
		// Intaking
		addParallel(new AutoSetArmElevatorSetpoints(-60, 3));
		addSequential(new AutoSetWristRelativeSetpoint(0));
		addSequential(new AutoSetIntakeMotorOutputs(0.75, 2));
		//addSequential(new PathFinderRead("straightCalibration"));
		addParallel(new AutoSetIntakeMotorOutputs(0, 0));
		addSequential(new AutoReleaseWristSetpoint());
	}
}
 