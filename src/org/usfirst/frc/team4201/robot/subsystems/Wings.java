package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wings extends Subsystem{
	
<<<<<<< HEAD:src/org/usfirst/frc/team4201/robot/subsystems/Platforms.java
	DoubleSolenoid platformDeploy = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.WingUp, RobotMap.WingDown);
	
	public Platforms(){		
		super("Platforms");
	}
	public boolean getWingStatus() {
		return platformDeploy.get() == Value.kForward ? true : false;
=======
	public Wings(){		
>>>>>>> dbc1e4abe08be7ef38a031e38e9283c537e18467:src/org/usfirst/frc/team4201/robot/subsystems/Wings.java
	}
	
	public void deployWings() {
		platformDeploy.set(Value.kForward);
	}
	
	public void retractWings() {
		platformDeploy.set(Value.kReverse);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
