package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Platforms extends Subsystem{
    
    //DoubleSolenoid leftPlatform = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.leftArmForward, RobotMap.leftArmReverse);
    DoubleSolenoid rightPlatform = new DoubleSolenoid(RobotMap.PCMTwo, RobotMap.rightArmForward, RobotMap.rightArmReverse);
    
    
    public Platforms(){        
        super("Platforms");
    }
    
    public boolean getPlatformStatus() {
        return false;
    	//return leftPlatform.get() == Value.kForward ? true : false;
    }
    
    public void deployPlatforms() {
        //leftPlatform.set(Value.kForward);
        rightPlatform.set(Value.kForward);
    } 
    
    public void retractPlatforms() {
        //leftPlatform.set(Value.kReverse);
        rightPlatform.set(Value.kReverse);
    }
    
    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
}
