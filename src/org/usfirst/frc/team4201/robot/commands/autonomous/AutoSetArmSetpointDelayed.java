package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSetArmSetpointDelayed extends CommandGroup {
	
	public AutoSetArmSetpointDelayed(double setpoint, double delay) {
		addSequential(new Delay(delay));
		addSequential(new AutoSetArmSetpoint(setpoint));
    }
}
