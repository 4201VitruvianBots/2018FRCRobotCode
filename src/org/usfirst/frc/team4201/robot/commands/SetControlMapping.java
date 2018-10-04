package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetControlMapping extends InstantCommand {

    public SetControlMapping() {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.setRunWhenDisabled(true);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.oi.initializeButtons();
    	Robot.oi.initializeDriverOperatorControls();
    }

}
