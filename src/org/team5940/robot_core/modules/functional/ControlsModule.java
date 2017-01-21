package org.team5940.robot_core.modules.functional;

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
	 */
	public double getAxis(String axis);
	/**
	 * Gets current value of joystick button based on the type of joystick you are using.
	 * @param button String representation of the button (ex. "jump").
	 * @return current position of the button as a boolean.
	 */
	public boolean getButton(String button);
}
