package org.team5940.robot_core.modules.sensors.axes;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * An {@link AxisModule} that uses a GenericHID's axis.
 * @author David Boles
 *
 */
public class HIDAxisModule extends AbstractModule implements AxisModule {

	/**
	 * Stores this' GenericHID.
	 */
	private final GenericHID input;
	
	/**
	 * Stores the number of the axis to access.
	 */
	private final int axis;
	
	/**
	 * Stores whether to invert the axis.
	 */
	private final boolean invert;
	
	public HIDAxisModule(String name, LoggerModule logger, GenericHID device, int axis, boolean invert)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, HIDAxisModule.class, new Object[]{device, axis, invert});
		if(axis < 0) this.logger.failInitializationIllegal(this, HIDAxisModule.class, "Axis Below Zero", axis);
		this.input = device;
		this.axis = axis;
		this.invert = invert;
		this.logger.logInitialization(this, HIDAxisModule.class, new Object[]{device, axis, invert});
	}

	@Override
	public synchronized double getAxis() {
		double out = invert ? this.input.getRawAxis(this.axis) : (this.input.getRawAxis(this.axis) * -1);
		this.logger.logGot(this, "Axis", out);
		return out;
	}

}
