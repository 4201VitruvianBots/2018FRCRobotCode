package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Delay extends Command {
    double timeout, time;
    Timer stopwatch;
    
    public Delay() {
        time = RobotMap.delay;
        this.timeout = time;
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        stopwatch.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        SmartDashboard.putNumber("Delay", time);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stopwatch.get() > timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
