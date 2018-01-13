package autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRobotToLeftSwitch extends CommandGroup{
	//all measurements are in inches
	public LeftRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(114.5,1));
		 addSequential(new DriveTurnWithGyro2(0,90));
		 addSequential(new DriveStraightWithGyroAndEncoders(23.81,1));
		 //Drop off Block
	}
}
