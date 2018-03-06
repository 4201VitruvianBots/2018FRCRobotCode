package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

public class AutoCalibration extends CommandGroup{
	static Command[] testCommands = new Command[10];
	
	public static void initializeAutoCalibration(){
		testCommands[0] = new AutoSetArmElevatorSetpoints(52, 12);
		testCommands[1] = new Delay(1.5);
		testCommands[2] = new AutoSetWristRelativeSetpoint(180);
		testCommands[3] = new SetIntakePistonsOpen();
		testCommands[4] = new AutoReleaseWristSetpoint(); 
		testCommands[5] = new SetIntakePistonsClose();
	}
	
	public AutoCalibration() {
		// Move to Scale 
		
		Shuffleboard.putString("Auto", "Auto Status", "Step 1");
		addParallel(testCommands[0]);
		//addParallel(new AutoSetArmSetpoint(52));
		//addParallel(new AutoSetElevatorSetpoint(12));
		// sequential: Pathfinder - Move to scale (ideal)
		//addSequential(new PathFinderRead("straightCalibration", true));
		addSequential(testCommands[1]);
		
		Shuffleboard.putString("Auto", "Auto Status", "Step 2");
		// Score at scale
		addSequential(testCommands[2]);
		//addSequential(new AutoSetIntakeMotorOutputs(-0.75, 1));
		addSequential(testCommands[3]);
		addSequential(testCommands[4]);
		addSequential(testCommands[5]);
		//addSequential(new AutoSetIntakeMotorOutputs(0, 0));
		
		/*
		addSequential(new Delay(2));
		
		Shuffleboard.putString("Auto", "Auto Status", "Step 3");
		// Intaking
		addParallel(new AutoSetArmElevatorSetpoints(-60, 3));
		addSequential(new AutoSetWristRelativeSetpoint(0));
		addSequential(new AutoSetIntakeMotorOutputs(0.75, 2));
		//addSequential(new PathFinderRead("straightCalibration"));
		addParallel(new AutoSetIntakeMotorOutputs(0, 0));
		addSequential(new AutoReleaseWristSetpoint());
		//*/
	}
	
	public static void updateSmartDashboard(){
		for(int i = 0; i < 6; i++) {
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Running", testCommands[i].isRunning());
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Finished", testCommands[i].isCompleted());
		}
	}
}
 