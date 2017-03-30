package org.team5940.robot_core.modules.actuators.rotational;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Allows the person to set the rotational acceleration in radians/s of the
 * extended modules.
 * 
 * @author Michael Bentley
 */
public interface RotationalAccelerationActuatorModule extends OwnableModule {

	/**
	 * Sets the acceleration of the RotationalAccelerationActuatorModule.
	 * 
	 * @param acceleration
	 *            The acceleration in radians/s to set it to.
	 */
	public void setRotationalAcceleration(double acceleration);

}