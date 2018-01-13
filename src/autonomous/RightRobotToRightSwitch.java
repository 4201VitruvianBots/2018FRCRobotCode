package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;

public class RightRobotToRightSwitch extends CommandGroup{
	//all measurements are in inches
	public RightRobotToRightSwitch() {
		addSequential(new DriveStraightWithGyroAndEncoders(144.5, 1));
		addSequential(new DriveTurnWithGyro2(0,-90));
		addSequential(new DriveStraightWithGyroAndEncoders(23.81, 1)); 
		//Drop off Block
	}
}
