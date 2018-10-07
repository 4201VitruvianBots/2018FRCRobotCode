package org.usfirst.frc.team4201.robot.commands.autonomous.routines;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.ArrayList;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.commands.autonomous.*;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;

public class AutoTesting extends CommandGroup{
	static ArrayList<Command> testCommands = new ArrayList<>();
	int index = 0;
	
	public static void initializeAutoCalibration() {
		//testCommands.add(new SetIntakePistonsOpen());
		testCommands.add(new AutoSetArmSetpoint(-58));
		//testCommands.add(new AutoSetWristAbsoluteSetpoint(0));
		//testCommands.add(new AutoSetIntakeMotorOutputsContinouous(1));
		testCommands.add(new PathFinderReadInverted("rightScaleToCubeOneReverse", false));
		//testCommands.add(new SetIntakePistonsClose());

		testCommands.add(new AutoReleaseWristSetpoint());
		//testCommands.add(new AutoSetIntakeMotorOutputsContinouous(0.1));
		testCommands.add(new AutoSetArmSetpoint(60));
		testCommands.add(new PathFinderReadInverted("cubeOneToRightScaleReverse", false));

		//testCommands.add(new AutoSetWristRelativeSetpoint(135));
		//testCommands.add(new AutoSetIntakeMotorOutputsContinouous(0));
		//testCommands.add(new AutoSetIntakeMotorOutputs(-0.75, 0.65));
		//testCommands.add(new AutoReleaseWristSetpoint());
	}
	
	public AutoTesting() {
		addSequential(new PathFinderRead("driveCalibration"));
		
		//index = 0;
		//addParallel(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addParallel(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		
		//addParallel(testCommands.get(index++));
		//addParallel(testCommands.get(index++));
		//addSequential(testCommands.get(index++));

		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		//addSequential(testCommands.get(index++));
		
		// Move to Scale 
		/*
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
		*/
		//addSequential(testCommands[5]);
		
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
		for(int i = 0; i < testCommands.size(); i++) {
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Running", testCommands.get(i).isRunning());
			Shuffleboard.putBoolean("Auto", "Test Command " + i + " Finished", testCommands.get(i).isCompleted());
		}
	}
}
 