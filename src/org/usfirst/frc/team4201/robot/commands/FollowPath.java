package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import com.ctre.phoenix.motion.TrajectoryPoint;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class FollowPath extends Command {
	TankModifier points;
	
    public FollowPath() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Generate Path
    	Waypoint[] waypoints = new Waypoint[] {
    		// X, Y, exit angle. +/- X is forward/reverse, +/- Y is right/left
    		new Waypoint(10, 10, 0),
    		new Waypoint(5, 5, Pathfinder.d2r(90)),	
    		new Waypoint(0, 0, 0)	
    	};
    	// Need to figure out max velocity, max acceleration, and max jerk
    	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.05, 67, 78.75, 2362.2);
    	
    	Trajectory trajectory = Pathfinder.generate(waypoints, config);
    	TankModifier points = new TankModifier(trajectory).modify(28);	// Clutch's wheelbase is ~ 28 in.
    	
    	for(int i = 0; i < trajectory.length(); i++) {
	        Trajectory.Segment leftPoint = points.getLeftTrajectory().get(i);
	        Trajectory.Segment rightPoint = points.getLeftTrajectory().get(i);
	        TrajectoryPoint left = new TrajectoryPoint();
	        TrajectoryPoint right = new TrajectoryPoint();
	        
	        // Need to look into more stuff after this. not sure if I need to convert PathFinder -> Talon units, or just use PathFinder units by themselves
	        // PathFinder points seem more accurate than Talon units
	        //left.profileSlotSelect = 0;
	        //left.headingDeg = Pathfinder.r2d(leftPoint.heading);	// What unit is heading in?
	        //left.position = leftPoint.position;
	        //left.velocity = leftPoint.velocity;
	        //left.zeroPos = i == 0 ? true : false;
	        //left.isLastPoint = (i + 1) == trajectory.length() ? true : false;
	        
	        //right.profileSlotSelect = 0;
	        //right.headingDeg = Pathfinder.r2d(rightPoint.heading);	// What unit is heading in?
	        //right.position = rightPoint.position;
	        //right.velocity = rightPoint.velocity;
	        //right.zeroPos = i == 0 ? true : false;
	        //right.isLastPoint = (i + 1) == trajectory.length() ? true : false;
	        
	        //Robot.driveTrain.driveMotors[0].pushMotionProfileTrajectory(left);
	        //Robot.driveTrain.driveMotors[2].pushMotionProfileTrajectory(right);
	    }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
