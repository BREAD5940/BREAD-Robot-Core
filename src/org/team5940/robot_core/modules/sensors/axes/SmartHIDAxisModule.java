package org.team5940.robot_core.modules.sensors.axes;

import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * A more configurable {@link HIDAxisModule}.
 * @author David Boles
 *
 */
public class SmartHIDAxisModule extends HIDAxisModule {
	
	/**
	 * Stores this' dead zone.
	 */
	private final double deadZone;
	
	/**
	 * Stores the exponent to raise the axis to.
	 */
	private final double exponent;
	
	/**
	 * Initializes a new {@link SmartHIDAxisModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param device The GenericHID to access this axis from.
	 * @param axis The axis to access from device.
	 * @param invert Whether to invert axis retrieved from device.
	 * @param deadZone The +- distance from (0,0) to ignore. Bounded between -1 and 1 inclusive.
	 * @param exponent The exponent to raise the input to for non-linear control.
	 * @throws IllegalArgumentException Thrown if any argument is null or deadZone is out of bounds.
	 */
	public SmartHIDAxisModule(String name, LoggerModule logger, GenericHID device, int axis, boolean invert, double deadZone, double exponent)
			throws IllegalArgumentException {
		super(name, logger, device, axis, invert);
		if(deadZone > 1 || deadZone < -1)
			this.logger.failInitializationIllegal(this, SmartHIDAxisModule.class, "Out Of Bounds Dead Zone", deadZone);
		this.deadZone = deadZone;
		this.exponent = exponent;
		this.logger.logInitialization(this, SmartHIDAxisModule.class, new Object[]{deadZone, exponent});
	}

	@Override
	public synchronized double getAxis() {
		boolean enabled = logger.isEnabled();
		logger.setEnabled(false);
		double out = super.getAxis();
		logger.setEnabled(enabled);
		
		if(Math.abs(out) <= this.deadZone) out = 0;
		
		double pos = 1;
		if(out < 1) pos = -1;
		out = pos * Math.pow(Math.abs(out), this.exponent);
		
		logger.logGot(this, "Axis", out);
		return out;
	}
}
