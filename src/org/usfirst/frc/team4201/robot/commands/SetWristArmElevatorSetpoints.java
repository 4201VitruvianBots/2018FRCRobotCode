package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetWristArmElevatorSetpoints extends CommandGroup {

    public SetWristArmElevatorSetpoints(double wristSetpoint, double armSetpoint, double elevatorSetpoint) {
    	// Check if the arm is going to pass the horizon. If so, we retract the wrist, set the arm to the horizion, and move half the elevator hieght before going to the actual setpoints

    	addParallel(new SetArmSetpoint(armSetpoint));
    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));;
    	addSequential(new HoldWristSetpoint());
    	addSequential(new SetWristSetpoint(wristSetpoint));
    }
}
