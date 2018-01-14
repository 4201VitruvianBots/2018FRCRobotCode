package org.usfirst.frc.team4201.robot.interfaces;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CTREPIDSource implements PIDSource{
	PIDSourceType type = PIDSourceType.kDisplacement;
	BaseMotorController motorController;
	
	public CTREPIDSource(BaseMotorController motorController) {
		this.motorController = motorController;
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
		// TODO Auto-generated method stub
		if(type == PIDSourceType.kDisplacement)
			return motorController.getSelectedSensorPosition(0);
		else if(type == PIDSourceType.kRate)
			return motorController.getSelectedSensorVelocity(0);
		else
			return 0;
	}

	
}
