package org.usfirst.frc.team4201.robot.commands;

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
