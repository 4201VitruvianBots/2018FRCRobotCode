package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class LeftRobotToRightScale extends CommandGroup{
	public LeftRobotToRightScale() {
		 addSequential(new DriveStraightWithGyroAndEncoders(208.5, 1));
		 addSequential(new DriveTurnWithGyro2(0,90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(186.31, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(107, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(5.81, 1));
	}
}
