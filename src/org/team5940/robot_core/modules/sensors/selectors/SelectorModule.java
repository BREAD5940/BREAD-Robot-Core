package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * A module that can select between multiple states.
 * @author Noah Sturgeon
 *
 */
public interface SelectorModule extends Module, TestableModule {

	/**
	 * Gets the number of states this selector has.
	 * @return The number of states.
	 */
	public int getNumberOfStates();
	
	/**
	 * Gets the current state of this selector.
	 * @return The current state of this. Between 0 inclusive and getNumberOfStates() exclusive or -1 if no state.
	 * @see SelectorModule#getNumberOfStates()
	 */
	public int getCurrentState();
	
	//TODO test the test
	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			boolean run = true;
			while(!Thread.interrupted() && run) {
				long startTime = System.currentTimeMillis();
				while(startTime + 10000 > System.currentTimeMillis()){
					comms.displayText("Current state of " + this.getModuleName() + ": " + this.getCurrentState());
				}
				if(comms.promptBoolean("Is " + this.getModuleName() + " working correctly?")) {
					//RESET
					this.getModuleLogger().logTestPassed(this);
					return TestStatus.PASSED;
				}
				if(!comms.promptBoolean("Do you want to check again?")) run = false;
			}
			//FAIL
			this.getModuleLogger().logTestFailed(this, "Not Working Correctly");
			return TestStatus.FAILED;
		}catch(Exception e) {
			this.getModuleLogger().logTestErrored(this, e);
			return TestStatus.ERRORED;
		}
	
	}
	
}
