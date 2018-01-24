package org.usfirst.frc.team4201.robot.interfaces;

import com.ctre.phoenix.motion.TrajectoryPoint;

import jaci.pathfinder.Trajectory;

public class Convert {
	public static TrajectoryPoint[] pathfinderToTalon(Trajectory trajectory){
		TrajectoryPoint[] conversion = new TrajectoryPoint[trajectory.length()];
		for(int i = 0; i < trajectory.length(); i++){
			conversion[i] = new TrajectoryPoint();
			conversion[i].headingDeg = trajectory.segments[i].heading;
			conversion[i].position = trajectory.segments[i].position;
			conversion[i].velocity = trajectory.segments[i].velocity;
			//conversion[i].timeDur = 0;
			conversion[i].zeroPos = i == 0 ? true : false;
			conversion[i].isLastPoint = (i + 1) == trajectory.length() ? true : false;
		}
		return conversion;
	}
}
