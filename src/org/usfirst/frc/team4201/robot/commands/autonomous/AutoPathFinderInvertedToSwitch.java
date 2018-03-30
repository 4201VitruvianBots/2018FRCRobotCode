package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderInvertedToSwitch extends CommandGroup {

    public AutoPathFinderInvertedToSwitch(String filename, boolean first, double maxVel, double kP) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(-45, 0.5));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetWristScaleScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename, boolean first, double maxVel) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(-45, 0.5));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetWristScaleScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename, boolean first) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(-45, 0.5));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetWristScaleScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
    
    public AutoPathFinderInvertedToSwitch(String filename) {
		addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(-45, 0.5));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetWristScaleScoring(Robot.wrist.convertRelativeToAbsoluteSetpoint(0)));
    }
}
