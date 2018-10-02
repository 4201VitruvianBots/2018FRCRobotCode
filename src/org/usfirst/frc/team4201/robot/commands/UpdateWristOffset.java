package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateWristOffset extends InstantCommand {

    public UpdateWristOffset() {
        super();
        requires(Robot.wrist);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.wrist.setSensorOffset(SmartDashboard.getNumber("Wrist Sensor Offset", Robot.wrist.getSensorOffset()));
    }

}
