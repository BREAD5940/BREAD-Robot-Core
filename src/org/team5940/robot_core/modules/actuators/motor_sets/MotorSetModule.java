package org.team5940.robot_core.modules.actuators.motor_sets;

import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

/**
 * Interface to set and get the "speed" for a set of motors.
 * @author Noah Sturgeon
 *
 */
public interface MotorSetModule extends OwnableModule {
	/**
	 * Sets the "speed" of the motors.
	 * @param speed The "speed" to set the motors to, between -1 and 1 inclusive.
	 * @throws IllegalArgumentException If speed is less than -1 or more than 1.
	 * @throws ThreadUnauthorizedException If this is not accessible to the current thread.
	 */
	public void setMotorSpeed(double speed) throws IllegalArgumentException, ThreadUnauthorizedException;
	
	/**
	 * Gets the last "speed" that the motors were set to.
	 * @return The last "speed" the motors were set to, between -1 and 1 inclusive.
	 */
	public double getSetMotorSpeed();
	
}
