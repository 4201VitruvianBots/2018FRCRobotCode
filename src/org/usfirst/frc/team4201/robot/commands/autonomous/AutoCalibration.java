package org.usfirst.frc.team4201.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

public class AutoCalibration extends CommandGroup{
	static Command[] testCommands = new Command[10];
	static int index = 0;
	public static void initializeAutoCalibration() {
		testCommands[index++] = new AutoSetArmElevatorSetpoints(52, 12);
		//testCommands[i++] = new Delay(1.5);
		testCommands[index++] = new AutoSetWristRelativeSetpoint(180);
		testCommands[index++] = new SetIntakePistonsOpen();
		testCommands[index++] = new AutoReleaseWristSetpoint(); 
		testCommands[index++] = new Delay(0.5);
		testCommands[index++] = new SetIntakePistonsClose();
	}
	
	public AutoCalibration() {
		// Move to Scale 
		
		Shuffleboard.putString("Auto", "Auto Status", "Step 1");
		addSequential(testCommands[0]);
		// sequential: Pathfinder - Move to scale (ideal)
		//addSequential(new PathFinderRead("straightCalibration", true));
		
		Shuffleboard.putString("Auto", "Auto Status", "Step 2");
		// Score at scale
		addSequential(testCommands[1]);
		//addSequential(new AutoSetIntakeMotorOutputs(-0.75, 1));
		addSequential(testCommands[2]);
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
		for(int i = 0; i < index; i++) {
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Running", testCommands[i].isRunning());
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Finished", testCommands[i].isCompleted());
		}
	}
}
 