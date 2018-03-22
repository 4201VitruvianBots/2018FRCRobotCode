package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSetArmElevatorSetpoints extends CommandGroup {

    public AutoSetArmElevatorSetpoints(double armSetpoint, double elevatorSetpoint) {
    	addParallel(new AutoSetArmSetpoint(armSetpoint));
    	addSequential(new AutoSetElevatorSetpoint(elevatorSetpoint));
    }
    
    public AutoSetArmElevatorSetpoints(double armSetpoint, double elevatorSetpoint, double delay) {
    	addSequential(new Delay(delay));
    	addParallel(new AutoSetArmSetpoint(armSetpoint));
    	addSequential(new AutoSetElevatorSetpoint(elevatorSetpoint));
    }
}
