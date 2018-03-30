package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderToSwitch extends CommandGroup {

    public AutoPathFinderToSwitch(String filename, boolean first, double maxVel, double kP) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetElevatorSetpoint(25));
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetWristSwitchScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderToSwitch(String filename, boolean first, double maxVel) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetElevatorSetpoint(25));
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetWristSwitchScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderToSwitch(String filename, boolean first) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetElevatorSetpoint(25));
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetWristSwitchScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderToSwitch(String filename) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetElevatorSetpoint(25));
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetWristSwitchScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
}
