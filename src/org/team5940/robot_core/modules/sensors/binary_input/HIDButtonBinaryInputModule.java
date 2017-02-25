package org.team5940.robot_core.modules.sensors.binary_input;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * A {@link BinaryInputModule} that uses a GenericHID's button.
 * @author David Boles
 *
 */
public class HIDButtonBinaryInputModule extends AbstractModule implements BinaryInputModule {

	/**
	 * Stores this' GenericHID.
	 */
	private final GenericHID input;
	
	/**
	 * Stores the number of the button to access.
	 */
	private final int button;
	
	/**
	 * Initializes a new {@link HIDButtonBinaryInputModule}.
	 * @param name
	 * @param logger
	 * @param device
	 * @param button
	 * @throws IllegalArgumentException
	 */
	public HIDButtonBinaryInputModule(String name, LoggerModule logger, GenericHID device, int button)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, HIDButtonBinaryInputModule.class, new Object[]{device, button});
		if(button < 1) this.logger.failInitializationIllegal(this, HIDButtonBinaryInputModule.class, "Button Below One", button);
		this.input = device;
		this.button = button;
		this.logger.logInitialization(this, HIDButtonBinaryInputModule.class, new Object[]{device, button});
	}

	@Override
	public synchronized boolean getBinaryInput() {
		boolean out = this.input.getRawButton(this.button);
		this.logger.logGot(this, "Binary Input", out);
		return out;
	}

}
