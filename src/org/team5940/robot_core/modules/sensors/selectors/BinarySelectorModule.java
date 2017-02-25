package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.binary_input.BinaryInputModule;

/**
 * An implementation of {@link SelectorModule} that uses a {@link BinaryInputModule} to determine which of two states it's in (0 for false, 1 for true).
 * @author David Boles
 *
 */
public class BinarySelectorModule extends AbstractModule implements SelectorModule {

	/**
	 * Stores the {@link BinaryInputModule} this uses to determine its state.
	 */
	private final BinaryInputModule input;
	
	/**
	 * Initializes a new {@link BinarySelectorModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param input The {@link BinaryInputModule} that determines this' state.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public BinarySelectorModule(String name, LoggerModule logger, BinaryInputModule input)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(input), logger);
		this.logger.checkInitializationArg(this, BinarySelectorModule.class, input);
		this.input = input;
		this.logger.logInitialization(this, BinarySelectorModule.class, input);
	}

	@Override
	public int getNumberOfStates() {
		this.logger.logGot(this, "Number Of States", 2);
		return 2;
	}

	@Override
	public int getCurrentState() {
		int state = this.input.getBinaryInput() ? 1 : 0;
		this.logger.logGot(this, "Current State", state);
		return state;
	}

}
