package org.team5940.robot_core.modules.functionality;

import org.team5940.robot_core.modules.ownable.OwnableModule;

public interface MotorSetModule extends OwnableModule {
	/**
	 * Sets the speed of the motorset, between -1 and 1 inclusive.
	 * @throws IllegalArgumentException if speed is less than -1 or more than 1.
	 */
	public void setSpeed  (double speed) throws IllegalArgumentException;
	/**
	 * Gets the last speed that the motorset was set to. Note: not necessarily what the motroset was last externally set to.
	 * @return the motorset's speed
	 */
	public double getSetSpeed();
	
}
