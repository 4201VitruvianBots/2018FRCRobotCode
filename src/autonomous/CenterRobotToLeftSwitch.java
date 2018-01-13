package autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToLeftSwitch extends CommandGroup{
	//all measurements are in inches
	public CenterRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(12, 1));
		 addSequential(new DriveTurnWithGyro2(0,-45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(84.85, 1));
		 addSequential(new DriveTurnWithGyro2(0, 45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(70, 1));
		 //Drop off Block
	}
}
