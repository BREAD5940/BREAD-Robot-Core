package org.team5940.robot_core.modules.functional.selectors;

import java.util.ArrayList;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.testable.TestRunnerModule;
import org.team5940.robot_core.modules.testable.TestableModule;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A class for selecting different states with DigitalInputs. 
 * @author Noah Sturgeon
 *
 */
public class DigitalInputSelectorModule extends SimpleModule implements SelectorModule, TestableModule{

	/**
	 * Stores an array list of DigitalInputs. Is called a "Selector" in other places of the code.
	 */
	private ArrayList<DigitalInput> inputs = new ArrayList<DigitalInput>();
	
	/**
	 * Stores an array of booleans. This tells you which parts of the Selector is inverted.
	 */
	private ArrayList<Boolean> inverts = new ArrayList<Boolean>();
	
	/**
	 * Initializes the DigitalInputSelectorModule.
	 * 
	 * Super docs:
	 * {@inheritDoc}
	 */
	public DigitalInputSelectorModule(String name, LoggerModule logger)
			throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.logger.log(this,"Created DigitalInputSelectorModule");
	}

	
	/**
	 * Gets the number of states in the module.
	 * @return The size of the DigitalInput array
	 */
	@Override
	public synchronized int getNumOfStates() {
		this.logger.log(this, "Returning Number Of States", inputs.size());
		return inputs.size();
	}
	
	/**
	 * Adds a DigitalInput and invert value to the array.
	 * @param input The DigitalInput you want to add to the constructor.
	 * @param invert Stores if the DigitalInput is inverted.
	 * @throws IllegalArgumentException if input == null.
	 */
	public synchronized DigitalInputSelectorModule addDigitalInput(DigitalInput input, boolean invert) throws IllegalArgumentException{
		if (input == null) {
			this.logger.vError(this, "Adding DigitalInput With Null", new Object[]{input, invert});
			throw new IllegalArgumentException("Values should not be null");
			
		}
		this.inputs.add(input);
		this.inverts.add(invert);
		this.logger.log(this, "Added a DigitalInput", new Object[]{input, invert});
		return this;
	}

	/**
	 * Gets the current state of the DigitalInput array. Only one of these should be true.
	 * @return the location in the array that is true. -1 if a state was not found.
	 */
	@Override
	public synchronized int currentState() {
		
		
		for (int i = 0; i < inputs.size();i++){
			if(this.inputs.get(i).get() ^ this.inverts.get(i)) return i;
			this.logger.log(this, "Getting a State", i);
			
		}
		this.logger.log(this, "Did not get a state");
		return -1;
	}
	
	@Override
	public synchronized void initialize() {
		this.logger.log(this, "Initializing");
		
	}

	@Override
	public synchronized void shutDown() {
		this.logger.log(this, "Shutting Down");
		
	}

	@Override
	public synchronized TestResult runTest(TestRunnerModule testRunner) throws IllegalArgumentException {
		if(testRunner == null) {
			this.logger.vError(this, "Test Run With Null testRunner");
			throw new IllegalArgumentException("testRunner null");
		}
		boolean testsGood = true;
		
		try {
			for(int i=0;i<getNumOfStates();i++){
				testRunner.promptText("Put the Selector to the position, " + i);
				while (!testRunner.promptAndGetReturnBoolean("Is The Selector In The Stated Postion? y/n."));
				if (this.currentState() != i){
					testsGood = false;
					this.logger.error(this, "Selector Test Failed Selector Position " + i, inputs);
				}
				
			}
			
		}catch (Exception e) {
			return TestResult.ERROR;
		}
		if(testsGood){ 
			logger.log(this, "Selector test was succesful"); 
			return TestResult.SUCCESSFUL; 
			}
		else { 
			logger.log(this, "Selector test was failed");	
			return TestResult.FAILED;
				}
		
		}
	}
