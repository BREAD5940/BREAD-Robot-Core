package org.team5940.robot_core.modules.actuators.rotational;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the rotational position in radians of the extended
 * modules.
 * 
 * @author Michael Bentley
 */
public interface RotationalPositionActuatorModule extends OwnableModule {

	/**
	 * Sets the position of the RotationalPositionActuatorModule.
	 * 
	 * @param position
	 *            The position in radians to set it to.
	 */
	public void setRotationalPosition(double position);

}
