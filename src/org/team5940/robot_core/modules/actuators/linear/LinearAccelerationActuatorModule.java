package org.team5940.robot_core.modules.actuators.linear;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the linear acceleration in meters of the extended modules.
 * 
 * @author Michael Bentley
 *
 */
public interface LinearAccelerationActuatorModule extends OwnableModule {

	/**
	 * Set the acceleration of the LinearAccelerationActuatorModule.
	 * 
	 * @param acceleration
	 *            The acceleration in m/s to be set to.
	 */
	public void setLinearAcceleration(double acceleration);

}
