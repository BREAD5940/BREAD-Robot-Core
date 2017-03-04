package org.team5940.robot_core.modules.sensors.linear.accelerometers;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.linear.LinearAccelerationModule;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * Provides access to an axis of a Roborio's built-in accelerometer.
 * @author David Boles
 *
 */
public class BuiltInAccelerometerModule extends AbstractModule implements LinearAccelerationModule {

	/**
	 * Stores the axis that this gets.
	 */
	private int axis;
	
	/**
	 * Stores the scale factor for the output of the axis.
	 */
	private double scaleFactor;
	
	/**
	 * Stores the accelerometer.
	 */
	private BuiltInAccelerometer accelerometer;
	
	/**
	 * Initializes a new BuiltInAccelerometerModule.
	 * @param name This' name.
	 * @param dependencies This' dependencies.
	 * @param logger This' logger.
	 * @param axis The axis to access from the built-in accelerometer (X-0, Y-1, Z-2).
	 * @param scaleFactor The scale factor to multiply the output by.
	 * @throws IllegalArgumentException If any argument is null or 0>axis>2.
	 */
	public BuiltInAccelerometerModule(String name, LoggerModule logger, int axis, int scaleFactor)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		if(axis < 0 || axis > 2) {
			this.logger.failInitializationIllegal(this, BuiltInAccelerometerModule.class, "Out Of Bounds Axis", axis);
		}
		this.axis = axis;
		this.scaleFactor = scaleFactor;
		this.accelerometer = new BuiltInAccelerometer();
		this.logger.logInitialization(this, BuiltInAccelerometerModule.class, new Object[]{axis, scaleFactor});
	}

	@Override
	public double getLinearAcceleration() {
		double out = 0;
		if(this.axis == 0) out = this.scaleFactor * this.accelerometer.getX();
		if(this.axis == 1) out = this.scaleFactor * this.accelerometer.getY();
		if(this.axis == 2) out = this.scaleFactor * this.accelerometer.getZ();
		this.logger.logGot(this, "Linear Acceleration", out);
		return out;
	}

}
