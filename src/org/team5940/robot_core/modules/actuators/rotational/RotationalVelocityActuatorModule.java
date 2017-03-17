package org.team5940.robot_core.modules.actuators.rotational;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the rotational velocity in radians of the extended modules.
 * 
 * @author Michael Bentley
 */
public interface RotationalVelocityActuatorModule extends OwnableModule {
	
	/**
	 * Sets the velocity of the RotationalVelocityActuatorModule.
	 * 
	 * @param velocity
	 *            The velocity in radians/s to set it to.
	 */
	public void setRotationalVelocity(double velocity);
	
}
