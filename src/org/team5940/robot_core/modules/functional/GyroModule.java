package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.Module;
/**
 * Interface for the Gryo with 1 axis.
 * @author Alexander Loeffler
 */
public interface GyroModule extends Module {
		/**
		 * Returns the current angle of the Gyro from initial position.
		 * @return Returns current angle in Radians. Bound 0&lt;=θ&lt;Τ (2π).
		 */
		public double getAngle();
		
		/**
		 * Returns the current rate of turn of the Gyro.
		 * @return Returns the current rate of turn in Radians per second.
		 */
		public double getRate();
		
		/**
		 * Calibrates the Gyro to map current position as zero.
		 */
		public void calibrate();
}
