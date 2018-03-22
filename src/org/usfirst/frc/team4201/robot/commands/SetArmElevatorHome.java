package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetArmSetpoint;
import org.usfirst.frc.team4201.robot.commands.autonomous.AutoSetElevatorSetpoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetArmElevatorHome extends CommandGroup {

    public SetArmElevatorHome() {
    	addParallel(new AutoSetArmSetpoint(-57));
    	addSequential(new AutoSetElevatorSetpoint(3.9));
    	addSequential(new Delay(0.25));
    	addSequential(new AutoSetArmSetpoint(-59));
    }
}
