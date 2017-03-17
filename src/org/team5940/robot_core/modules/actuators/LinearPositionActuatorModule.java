package org.team5940.robot_core.modules.actuators;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the position of the extended modules.
 * 
 * @author Michael Bentley
 */
public interface LinearPositionActuatorModule extends OwnableModule {

	/**
	 * Sets the position of the LinearPositionActuatorModule.
	 * 
	 * @param position
	 *            The position to adjust this to.
	 */
	public void setPosition(double position);

}
