package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.interfaces.Shuffleboard;
import org.usfirst.frc.team4201.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**	This Command automatically retracts the wrist if it detects a cube.
 *	This is also done with a mutex to avoid issues with the wrist auto-retracting in certain cases.
 */
public class ToggleCubeIntakeWithRetraction extends Command {
	public static int intakeState = 0;
	boolean cubeFlush, cubeStalled, finished;
	
	Timer stopwatch;
	boolean lock, wristLock = false, lockHighPressure = false, delayRetraction = false;
	
    public ToggleCubeIntakeWithRetraction() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.intake);
        //requires(Robot.wrist);
        //requires(Robot.arm);
        //requires(Robot.elevator);
        
        setInterruptible(false);
        
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cubeFlush = false;
    	UpdateWristSetpoint.intaking = true;
    	Robot.arm.setSetpoint(-61);
    	Robot.elevator.setSetpoint(2.8);
    	finished = false;
    	Arm.lock = true;
    	lockHighPressure = false;
    			
    	// Depressurize the intake pistons
		//Robot.intake.retractIntakePressure();
		//Robot.intake.extendIntakePistons();

		Robot.intake.retractIntakePressure();
		//Thread t = new Thread(()->{
			Robot.intake.extendIntakePistons();
			stopwatch.start();
			while(stopwatch.get() < 0.2){
				
			}
			Robot.intake.retractIntakePistons();
    	//});
    	//t.start();
    	//Robot.intake.retractIntakePistons();
    }
    
    @Override
	protected void execute() {
		if(Robot.intake.getIntakePistonStatus())
			Robot.intake.setIntakeMotorOutput(0.75);
		else
			Robot.intake.setIntakeMotorOutput(0.9);
		
		if(Robot.oi.rightButtons[0].get() && !lockHighPressure) {
			Robot.intake.extendIntakePressure();
			lockHighPressure = true;
		}
		
		switch(intakeState){
			case 2:
				Robot.elevator.setSetpoint(24.5);
				Robot.arm.setSetpoint(-55);
				break;
			case 1:
				Robot.elevator.setSetpoint(14);
				Robot.arm.setSetpoint(-55);
				break;
			case 0:
			default:
				Robot.elevator.setSetpoint(3.8);
				Robot.arm.setSetpoint(-60);
				break;
		}
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		
		cubeFlush = Robot.intake.intakeMotors[0].getOutputCurrent() + Robot.intake.intakeMotors[1].getOutputCurrent() > 40;
		//cubeStalled = Robot.intake.intakeMotors[0].getOutputCurrent() > 12 || Robot.intake.intakeMotors[1].getOutputCurrent() > 12;
		
		if((cubeFlush || cubeStalled) && !lock) {
			stopwatch.start();
			lock = true;
		} else if(!cubeFlush && !cubeStalled && lock) {
			stopwatch.stop();
			stopwatch.reset();
			lock = false;
		}
		
		if(stopwatch.get() > 0.25) {
			finished = true;
			stopwatch.stop();
			stopwatch.reset();
		}
		
		Shuffleboard.putBoolean("Intake", "Flush", cubeFlush);
		Shuffleboard.putBoolean("Intake", "Stalled", cubeStalled);
		
		return (Robot.oi.leftButtons[3].get() || Robot.oi.rightButtons[5].get());// || Robot.oi.testButtons[2].get());
	}
	
    // Called once after isFinished returns true
    protected void end() {
    	delayRetraction = Robot.intake.getIntakePistonStatus();
    	Robot.intake.extendIntakePressure();
    	Robot.intake.retractIntakePistons();

    	stopwatch.start();
		while(stopwatch.get() < (delayRetraction ? 0.3 : 0.05)){
    		
    	}
    	stopwatch.stop();
    	stopwatch.reset();
    	/*
    	if(cubeStalled){
    		Robot.intake.setIntakeMotorOutput(0);
    		stopwatch.start();
    		while(stopwatch.get() < 0.25) {
    			// Do nothing pause
    		}
    		stopwatch.stop();
    		stopwatch.reset();
    		stopwatch.start();
    		while(stopwatch.get() < 0.25)
            	Robot.intake.setIntakeMotorOutput(0.55, 0.5);
    		
    		stopwatch.stop();
    		stopwatch.reset();
    	}
    	//*/
    	if(RobotMap.WristState == 0)
            Robot.wrist.setSetpoint(RobotMap.wristRetractedAngle);
    	else {
    		//while(Robot.wrist.wristMotor.getOutputCurrent() < 25)
    			//Robot.wrist.setDirectOutput(0.75);
    		Robot.wrist.setDirectOutput(0);
    	}
    	
		Robot.intake.setIntakeMotorOutput(0);
    	/*
    	Thread T = new Thread(()->{
    		while(Robot.wrist.getAbsoluteAngle() > 120)
        		Robot.intake.setIntakeMotorOutput(0.5);

    		Robot.intake.setIntakeMotorOutput(0);
    	});
    	T.start();
    	//*/
    	UpdateWristSetpoint.autoSetpoint = 0;
    	UpdateWristSetpoint.intaking = false;
    	Arm.lock = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
