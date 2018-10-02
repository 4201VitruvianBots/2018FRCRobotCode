package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.subsystems.Controls;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetMaxCurrentDraw extends InstantCommand {

    public ResetMaxCurrentDraw() {
        super();
        requires(Robot.controls);
    }

    // Called once when the command executes
    protected void initialize() {
    	Controls.maxCurrentDraw = 0;
    }

}
