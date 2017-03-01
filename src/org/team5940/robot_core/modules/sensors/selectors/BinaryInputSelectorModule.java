package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.binary_input.BinaryInputModule;

/**
 * A class for selecting different states with {@link BinaryInputModule}s. 
 * @author Alexander Loeffler
 *
 */
public class BinaryInputSelectorModule extends AbstractModule implements SelectorModule {

	/**
	 * Stores this' BinaryInputs.
	 */
	private BinaryInputModule[] inputs;
	
	
	/**
	 * Initializes a new BinaryInputSelectorModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param inputs The binary inputs to use to determine this' state.
	 * @throws IllegalArgumentException If any argument is null or inputs contains a null.
	 */
	public BinaryInputSelectorModule(String name, LoggerModule logger, BinaryInputModule [] inputs)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, BinaryInputSelectorModule.class, inputs);
		for(BinaryInputModule check : inputs){
			if(check==null)
				this.logger.failInitializationIllegal(this, BinaryInputSelectorModule.class, "Inputs Contains Null", inputs);
		}
		this.inputs=inputs;
		this.logger.logInitialization(this, BinaryInputSelectorModule.class, inputs);
	}

	@Override
	public synchronized int getNumberOfStates() {
		this.logger.logGot(this, "Number Of States", inputs.length);
		return inputs.length;
	}
	
	@Override
	public synchronized int getCurrentState() {
		for (int i = 0; i < inputs.length;i++) {
			if(this.inputs[i].getBinaryInput()) {
				this.logger.logGot(this, "Current State", i);
				return i;
			}
			
		}
		this.logger.logGot(this, "Current State", -1);
		return -1;
	}
	
}
