package org.team5940.robot_core.modules.sensors.binary_input;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * A module that senses some kind of binary input (e.g. buttons, limit switches, infrared reflectance sensors, etc...).
 * @author David Boles
 *
 */
public interface BinaryInputModule extends Module, TestableModule {
	/**
	 * Gets the binary input.
	 * @return This' input as a boolean.
	 */
	public boolean getBinaryInput();
	
	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			boolean run = true;
			while(!Thread.interrupted() && run) {
				long startTime = System.currentTimeMillis();
				while(startTime + 10000 > System.currentTimeMillis()){
					comms.displayText("Current state of " + this.getModuleName() + ": " + this.getBinaryInput());
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
