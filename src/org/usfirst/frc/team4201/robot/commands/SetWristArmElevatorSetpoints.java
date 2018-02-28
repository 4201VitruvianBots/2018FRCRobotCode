package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetWristArmElevatorSetpoints extends CommandGroup {

    public SetWristArmElevatorSetpoints(double wristSetpoint, double armSetpoint, double elevatorSetpoint) {
    	// Check if the arm is going to pass the horizon. If so, we retract the wrist, set the arm to the horizion, and move half the elevator hieght before going to the actual setpoints
    	if(Robot.arm.getAngle() > -50 && Robot.arm.getAngle() < 50){
	    	addParallel(new SetWristSetpoint(120));
	    	addParallel(new SetArmSetpoint(0));
	    	addSequential(new SetElevatorSetpoint(elevatorSetpoint / 2));
	    	addParallel(new SetWristSetpoint(wristSetpoint));
	    	addParallel(new SetArmSetpoint(armSetpoint));
	    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    	} else {
	    	addParallel(new SetWristSetpoint(wristSetpoint));
	    	addParallel(new SetArmSetpoint(armSetpoint));
	    	addSequential(new SetElevatorSetpoint(elevatorSetpoint));
    	}
    }
}
