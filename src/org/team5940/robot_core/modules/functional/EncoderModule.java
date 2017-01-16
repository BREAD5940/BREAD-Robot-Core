package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.Module;

public interface EncoderModule extends Module {
	
	/**
	 * Returns the amount of rotations since the encoder module's initialization.
	 * @return Rotations as a double in radians.
	 */
	public double getRotation();
	
	/**
	 * Returns the velocity.
	 * @return Velocity as a double in radians per second.
	 */
	public double getVelocity();
	
	/**
	 * Returns the acceleration.
	 * @return Acceleration as a double in radians per second per second.
	 */
	public double getAcceleration();

}
