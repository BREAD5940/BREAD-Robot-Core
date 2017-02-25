package org.team5940.robot_core.modules.sensors.binary_input;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A {@link BinaryInputModule} that uses a DigitalInput.
 * @author David Boles
 *
 */
public class DigitalBinaryInputModule extends AbstractModule implements BinaryInputModule {

	/**
	 * Stores this' DigitalInput.
	 */
	private final DigitalInput input;
	
	/**
	 * Stores this' inversion.
	 */
	private final boolean invert;
	
	/**
	 * Initializes a new {@link DigitalBinaryInputModule}.
	 * @param name This' name.
	 * @param logger This' logger
	 * @param input The DigitalInput that this uses.
	 * @param invert Whether to invert the value from input.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public DigitalBinaryInputModule(String name, LoggerModule logger, DigitalInput input, boolean invert)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, DigitalBinaryInputModule.class, new Object[]{input, invert});
		this.input = input;
		this.invert = invert;
		this.logger.logInitialization(this, DigitalBinaryInputModule.class, new Object[]{input, invert});
	}

	@Override
	public synchronized boolean getBinaryInput() {
		boolean out = this.input.get() ^ invert;
		this.logger.logGot(this, "Binary Input", out);
		return out;
	}

}
