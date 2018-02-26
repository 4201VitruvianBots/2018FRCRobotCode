package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetPIDTunerValues extends InstantCommand {

    public SetPIDTunerValues() {
        //requires(Robot.pidTuner);
    }

    // Called once when the command executes
    protected void initialize() {
    	//Robot.pidTuner.updatePIDValues();
    }
}
