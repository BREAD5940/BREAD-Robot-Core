package org.team5940.robot_core.modules.actuators;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ownable.OwnableModule;

import edu.wpi.first.wpilibj.PIDInterface;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Allows the person to set the velocity of the extended modules.
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
	public void setVelocity(double velocity);

}