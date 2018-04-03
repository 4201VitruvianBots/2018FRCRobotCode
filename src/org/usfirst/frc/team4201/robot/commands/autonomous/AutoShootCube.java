package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootCube extends CommandGroup {

    public AutoShootCube() {
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		addSequential(new AutoSetIntakeMotorOutputs(-0.75, 0.5));
    }
    
    public AutoShootCube(double speed, double time) {
		addSequential(new AutoSetIntakeMotorOutputsContinouous(0));
		addSequential(new AutoSetIntakeMotorOutputs(speed, time));
    }
}
