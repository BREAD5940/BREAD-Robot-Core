package org.team5940.robot_core.modules.sensors.selectors;

import java.util.ArrayList;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A class for selecting different states with DigitalInputs. 
 * @author Noah Sturgeon
 *
 */
public class DigitalInputSelectorModule extends AbstractModule implements SelectorModule {

	/**
	 * Stores this' DigitalInputs.
	 */
	private ArrayList<DigitalInput> inputs = new ArrayList<DigitalInput>();
	
	/**
	 * Stores the inversions for this' DigitalInputs.
	 */
	private ArrayList<Boolean> inverts = new ArrayList<Boolean>();
	
	/**
	 * Initializes a new DigitalInputSelectorModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @throws IllegalArgumentException If any argument is null.
	 */
	public DigitalInputSelectorModule(String name, LoggerModule logger)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.logInitialization(this, DigitalInputSelectorModule.class);
	}

	@Override
	public synchronized int getNumberOfStates() {
		this.logger.logGot(this, "Number Of States", inputs.size());
		return inputs.size();
	}
	
	/**
	 * Adds a DigitalInput and invert value to this.
	 * @param input The DigitalInput you want to add.
	 * @param invert If the DigitalInput is active low.
	 * @return This for chaining.
	 * @throws IllegalArgumentException If input == null.
	 */
	public synchronized DigitalInputSelectorModule addDigitalInput(DigitalInput input, boolean invert) throws IllegalArgumentException{
		if (input == null) {
			this.logger.vError(this, "Adding DigitalInput With Null", new Object[]{input, invert});
			throw new IllegalArgumentException("Null argument!");
		}

		this.logger.log(this, "Adding DigitalInput", new Object[]{input, invert});
		this.inputs.add(input);
		this.inverts.add(invert);
		return this;
	}
	
	@Override
	public synchronized int getCurrentState() {
		for (int i = 0; i < inputs.size();i++) {
			if(this.inputs.get(i).get() ^ this.inverts.get(i)) {
				this.logger.logGot(this, "Current State", i);
				return i;
			}
			
		}
		this.logger.logGot(this, "Current State", -1);
		return -1;
	}
	
}
