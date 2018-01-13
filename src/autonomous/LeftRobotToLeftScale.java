package autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftScale extends CommandGroup{
	//all measurements are in inches
	public LeftRobotToLeftScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(315.5, 1));
		 addSequential(new DriveTurnWithGyro2(0,90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(5.81, 1));
		 //Drop off Block
	}
}
