package org.usfirst.frc.team4201.robot.commands.autonomous;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPathFinderInvertedToScaleFar extends CommandGroup {

    public AutoPathFinderInvertedToScaleFar(String filename, boolean first, double maxVel, double kP) {
		//addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(60, 3));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first, maxVel, kP));
		addParallel(new AutoSetWristScaleScoring(135, true));
    }
    
    public AutoPathFinderInvertedToScaleFar(String filename, boolean first, double maxVel) {
		//addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(60, 3));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first, maxVel));
		addParallel(new AutoSetWristScaleScoring(135, true));
    }
    
    public AutoPathFinderInvertedToScaleFar(String filename, boolean first) {
		//addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(60, 3));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename, first));
		addParallel(new AutoSetWristScaleScoring(135, true));
    }
    
    public AutoPathFinderInvertedToScaleFar(String filename) {
		//addParallel(new AutoSetIntakeMotorOutputsContinouous(0.1)); // Holds the cube better
		addParallel(new AutoSetArmSetpointDelayed(60, 3));	// 3.5 for cross autos
		addParallel(new PathFinderReadInverted(filename));
		addParallel(new AutoSetWristScaleScoring(135, true));
    }
}
