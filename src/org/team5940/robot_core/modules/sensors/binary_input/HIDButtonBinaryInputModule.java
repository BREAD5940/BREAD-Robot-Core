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
	 * Stores the inversion of the button.
	 */
	private final boolean inversion;
	
	/**
	 * Initializes a new {@link HIDButtonBinaryInputModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param device The GenericHID the button is on.
	 * @param button The raw number of the button on the device.
	 * @param inversion Whether to invert the button.
	 * @throws IllegalArgumentException Thrown if any argument is null or the button is less than 1.
	 */
	public HIDButtonBinaryInputModule(String name, LoggerModule logger, GenericHID device, int button, boolean inversion)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, HIDButtonBinaryInputModule.class, new Object[]{device, button, inversion});
		if(button < 1) this.logger.failInitializationIllegal(this, HIDButtonBinaryInputModule.class, "Button Below One", new Object[]{device, button, inversion});
		this.input = device;
		this.button = button;
		this.inversion = inversion;
		this.logger.logInitialization(this, HIDButtonBinaryInputModule.class, new Object[]{device, button, inversion});
	}
	
	/**
	 * Initializes a new, uninverted {@link HIDButtonBinaryInputModule}.
	 * @see HIDButtonBinaryInputModule#HIDButtonBinaryInputModule(String, LoggerModule, GenericHID, int, boolean)
	 */
	public HIDButtonBinaryInputModule(String name, LoggerModule logger, GenericHID device, int button)
			throws IllegalArgumentException {
		this(name, logger, device, button, false);
	}

	@Override
	public synchronized boolean getBinaryInput() {
		boolean out = this.input.getRawButton(this.button) ^ this.inversion;
		this.logger.logGot(this, "Binary Input", out);
		return out;
	}

}
