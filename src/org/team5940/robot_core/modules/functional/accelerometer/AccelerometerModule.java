package org.team5940.robot_core.modules.functional.accelerometer;

import org.team5940.robot_core.modules.Module;
/**
 * Interface for the three axis Accelerometer.
 * @author Alex
 *
 */
public interface AccelerometerModule extends Module {
		/**
		 * Returns the current acceleration in the X axis of the Accelerometer.
		 * @return Returns current angle in m/s^2.
		 */
		public double getX();
		/**
		 * Returns the current acceleration in the Y axis of the Accelerometer.
		 * @return Returns current angle in m/s^2.
		 */
		public double getY();
		/**
		 * Returns the current acceleration in the Z axis of the Accelerometer.
		 * @return Returns current angle in m/s^2.
		 */
		public double getZ();
		
		
}
