package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Test extends Subsystem {
DoubleSolenoid testShifters1 = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.testShifters1a, RobotMap.testShifters1b);
DoubleSolenoid testShifters2 = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.testShifters2a, RobotMap.testShifters2b);
DoubleSolenoid testShifters3 = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.testShifters3a, RobotMap.testShifters3b);
	
	public Test() {
		super();
		
	}
	
	public void extend() {
		testShifters1.set(Value.kForward);
		testShifters2.set(Value.kForward);
		testShifters3.set(Value.kForward);
	}
	
	public void retract() {
		testShifters1.set(Value.kReverse);
		testShifters2.set(Value.kReverse);
		testShifters3.set(Value.kReverse);
	}
	
	public boolean getShifterStatus(){
		return testShifters1.get() == Value.kForward ? true : false;
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Shifter Status", getShifterStatus());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
