package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetArmElevatorSetpoints extends CommandGroup {

    public SetArmElevatorSetpoints(double armSetpoint, double elevatorSetpoint) {
    	// Check if the arm is going to pass the horizon. If so, we retract the wrist, set the arm to the horizion, and move half the elevator hieght before going to the actual setpoints
    	if(Robot.arm.getAngle() * armSetpoint < 0){
			addParallel(new SetArmSetpoint(0));
	    	addSequential(new SetElevatorSetpoint(Math.abs(Robot.elevator.getHieght() - elevatorSetpoint ) / 2));
	    	addParallel(new SetArmSetpoint(armSetpoint));
	    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    	} else {
	    	addParallel(new SetArmSetpoint(armSetpoint));
	    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    	}
    }
}
