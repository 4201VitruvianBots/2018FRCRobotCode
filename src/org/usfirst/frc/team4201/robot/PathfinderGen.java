package org.usfirst.frc.team4201.robot;

import org.usfirst.frc.team4201.robot.commands.autonomous.PathFinderCommandGroup;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class PathfinderGen {
	static Waypoint[][] paths;
	public static Trajectory[] trajectories;
	static double max_vel;
	//
	public static boolean initializeTrajectories() {
		SmartDashboard.putBoolean("Path Ready", false);
		paths = PathFinderCommandGroup.paths;
		max_vel = 450;
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.005, max_vel, 200, (800 * 1.09361));

		// Generate the trajectory
		SmartDashboard.putString("PathFinder Status" , "Generating Trajectory...");

		try {
			for(int i = 0; i < paths.length; i++) {
				trajectories[i] = Pathfinder.generate(paths[i], config);
			}
			SmartDashboard.putBoolean("Path Ready", true);
			return true;
		} catch (Exception e){
			DriverStation.reportError("4201 Error!: Path generation failed!", false);
			return false;
		}
	}
}
