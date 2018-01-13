package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToLeftSwitch extends CommandGroup{
	public RightRobotToLeftSwitch() {
		 addSequential(new DriveStraightWithGyroAndEncoders(208.5, 1));
		 addSequential(new DriveTurnWithGyro2(0,-90)); 
		 addSequential(new DriveStraightWithGyroAndEncoders(124.31, 1));
		 addSequential(new DriveTurnWithGyro2(0, -90));
		 addSequential(new DriveStraightWithGyroAndEncoders(64, 1));
	}
}
