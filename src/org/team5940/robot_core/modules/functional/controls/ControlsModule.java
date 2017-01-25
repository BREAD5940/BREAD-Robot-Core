package org.team5940.robot_core.modules.functional.controls;

import org.team5940.robot_core.modules.Module;

/**
 * Interface for generic access to joystick controls.
 * @author Alexander Loeffler & Sean Duarte
 *
 */
public interface ControlsModule extends Module {
	
	/**
	 * Gets current value of joystick axis based on the type of joystick you are using.
	 * @param axis String representation of the axis (ex. "up").
	 * @return current position of the axis as a double.
	 * @throws IllegalArgumentException If axisAvailable would return false.
	 */
	public double getAxis(String axis) throws IllegalArgumentException;
	
	/**
	 * Determines if the axis requested is available.
	 * @param axis The axis to check availability for.
	 * @return Whether or not the axis is available.
	 */
	public boolean axisAvailable(String axis);
	
	/**
	 * Gets current value of joystick button based on the type of joystick you are using.
	 * @param button String representation of the button (ex. "jump").
	 * @return current position of the button as a boolean.
	 * @throws IllegalArgumentException If buttonAvailable() would return false.
	 */
	public boolean getButton(String button) throws IllegalArgumentException;

	
	/**
	 * Determines if the button requested is available.
	 * @param button The button to check availability for.
	 * @return Whether or not the button is available.
	 */
	public boolean buttonAvailable(String button);
}
