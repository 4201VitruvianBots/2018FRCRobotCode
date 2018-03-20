package org.usfirst.frc.team4201.robot.interfaces;

import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.networktables.NetworkTableInstance;

/**	This is a custom interface that allows us to place NetworkTable items into separate folders so that we can organize our Shuffleboard dashboard.
 * 	This is basically a copy/paste of the SmartDashboard functions, modified to accept an additional argument to specify where to place each piece of data.
 */
public class Shuffleboard {
	private static final NetworkTable table = NetworkTableInstance.getDefault().getTable("");
	
	/**
	   * Returns the boolean the key maps to. If the key does not exist or is of
	   *     different type, it will return the default value.
	   * @param tabName the Shuffleboard tab the key is under
	   * @param key the key to look up
	   * @param defaultValue the value to be returned if no value is found
	   * @return the value associated with the given key or the given default value
	   *     if there is no value associated with the key
	   */
	public static boolean getBoolean(String tabName, String key, boolean defaultValue) {
		return table.getSubTable(tabName).getEntry(key).getBoolean(defaultValue);
	}
	
	/**
	 * Returns the number the key maps to. If the key does not exist or is of
	 *     different type, it will return the default value.
	 * @param tabName the Shuffleboard tab the key is under
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static double getNumber(String tabName, String key, double defaultValue) {
		return table.getSubTable(tabName).getEntry(key).getDouble(defaultValue);
	}
	
	/**
	 * Returns the number array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
	 * @param tabName the Shuffleboard tab the key is under
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
   	 */
	public static double[] getNumberArray(String tabName, String key, double[] defaultValue) {
		return table.getSubTable(tabName).getEntry(key).getDoubleArray(defaultValue);
	}
	
	/**
	   * Returns the string the key maps to. If the key does not exist or is of
	   *     different type, it will return the default value.
	   * @param tabName the Shuffleboard tab the key is under
	   * @param key the key to look up
	   * @param defaultValue the value to be returned if no value is found
	   * @return the value associated with the given key or the given default value
	   *     if there is no value associated with the key
	   */
	public static String getString(String tabName, String key, String defaultValue) {
		return table.getSubTable(tabName).getEntry(key).getString(defaultValue);
	}
	
	/**
	   * Put a boolean under a Shuffleboard tab.
	   * @param tabName the Shuffleboard tab the key is under
	   * @param key the key to be assigned to
	   * @param value the value that will be assigned
	   * @return False if the table key already exists with a different type
	   */
	public static boolean putBoolean(String tabName, String key, boolean value){
		return table.getSubTable(tabName).getEntry(key).setBoolean(value);
	}
	
	/**
	 * Put a number in the table.
	 * @param tabName the Shuffleboard tab the key is under
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putNumber(String tabName, String key, double value){
		return table.getSubTable(tabName).getEntry(key).setDouble(value);
	}
	
	/**
	 * Returns the number array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
	 * @param tabName the Shuffleboard tab the key is under
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static boolean putNumberArray(String tabName, String key, double value[]){
		return table.getSubTable(tabName).getEntry(key).setDoubleArray(value);
	}
	
	/**
	   * Put a string in the table.
	   * @param tabName the Shuffleboard tab the key is under
	   * @param key the key to be assigned to
	   * @param value the value that will be assigned
	   * @return False if the table key already exists with a different type
	   */
	  public static boolean putString(String tabName, String key, String value) {
	    return table.getSubTable(tabName).getEntry(key).setString(value);
	  }
}
