package org.usfirst.frc.team4201.robot.interfaces;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AnalogPotentiometerSource implements PIDSource{
	PIDSourceType type = PIDSourceType.kDisplacement;
	AnalogInput analogInput;
	double unitLowerLimit;
	double unitUpperLimit;
	double unitOffset;
	double voltageUpperLimit;
	double voltageLowerLimit;
	
	
	public AnalogPotentiometerSource(AnalogInput analogPotentiometer, double unitLowerLimit, double unitUpperLimit, double unitOffset, double voltageLowerLimit, double voltageUpperLimit) {
		this.analogInput = analogPotentiometer;
		this.unitUpperLimit = unitUpperLimit;
		this.unitLowerLimit = unitLowerLimit;
		this.unitOffset = unitOffset;
		this.voltageUpperLimit = voltageUpperLimit;
		this.voltageLowerLimit = voltageLowerLimit;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		this.type = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public double pidGet() {
		if(getPIDSourceType() == PIDSourceType.kDisplacement)
			return (analogInput.getAverageVoltage() * ((unitUpperLimit - unitLowerLimit)/(voltageUpperLimit - voltageLowerLimit))) + unitOffset;
		else
			return 0;
	}
	
	public double getArmAngle() {
		double angleFromMast;
		//double sensorRange = getArmVertical() - getArmFrontLimit();		
    	double sensorRange = 3 - 1;		// What are these constants?
    	//double angleRange  = ArmPos.verticalCalAngle - ArmPos.frontCalAngle;
    	double angleRange  = 180 - 49;
    	
    	angleFromMast = (analogInput.getAverageVoltage() - 3) * (angleRange/sensorRange);
	
    	return angleFromMast;
		//double angleFromHorizon = Robot.mast.getMastAngle() + angleFromMast;
	    //return angleFromHorizon;
    }
	
	public double getWristAngle() {
		return -(180 - Robot.arm.getAngle() - 3.5);
    }

	
}
