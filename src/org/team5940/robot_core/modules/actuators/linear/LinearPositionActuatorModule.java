package org.team5940.robot_core.modules.actuators.linear;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the linear position in meters of the extended modules.
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
	public void setLinearPosition(double position);

}
