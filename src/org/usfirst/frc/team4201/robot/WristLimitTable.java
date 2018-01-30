package org.usfirst.frc.team4201.robot;

public class WristLimitTable {
	double wristLimits[] = new double[90];
	
	public void initializeTable(){
		// Create a look-up table (LUT) to set up the wrist limits to avoid on-board calculations.
		// First half of table is calculated and manually entered.
		// Second half mirrors first half values
		//wristLimits[0] = ;
		
		
		for(int i = 0; i < wristLimits.length / 2; i++){
			wristLimits[89 - i] = wristLimits[i];
		}
	}
}
