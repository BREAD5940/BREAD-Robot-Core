package org.team5940.robot_core.modules.actuators.linear;

import org.team5940.robot_core.modules.ownable.OwnableModule;


/**
 * Allows the person to set the linear velocity in meters of the extended modules.
 * 
 * @author Michael Bentley
 */
public interface LinearVelocityActuatorModule extends OwnableModule {

	/**
	 * Sets the velocity of the LinearVelocityActuatorModule.
	 * 
	 * @param velocity
	 *            The velocity to set it to.
	 */
	public void setLinearVelocity(double velocity);

}