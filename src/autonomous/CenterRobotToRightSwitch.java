package autonomous;

import org.usfirst.frc.team4201.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRobotToRightSwitch extends CommandGroup{
	//all measurements are in inches
	public CenterRobotToRightSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(12, 1));
		 addSequential(new DriveTurnWithGyro2(0, 45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(70.75, 1));
		 addSequential(new DriveTurnWithGyro2(0, -45)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(80, 1));
		 //Drop off Block
	}
}
