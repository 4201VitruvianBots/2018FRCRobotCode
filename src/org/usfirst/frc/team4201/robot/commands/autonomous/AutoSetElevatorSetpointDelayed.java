package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSetElevatorSetpointDelayed extends CommandGroup {
	
	public AutoSetElevatorSetpointDelayed(double setpoint, double delay) {
		addSequential(new Delay(delay));
		addSequential(new AutoSetElevatorSetpoint(setpoint));
    }
}
