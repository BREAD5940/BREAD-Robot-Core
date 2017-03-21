package org.team5940.robot_core.modules.sensors.rotational;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * This interface defines a module that can measure a single axis of rotational
 * position. 0 position is undefined.
 * 
 * @author David Boles
 *
 */
public interface RotationalPositionSensorModule extends Module, TestableModule {
	/**
	 * Gets this' rotational position.
	 * 
	 * @return This' unbounded rotational position in radians as a double.
	 */
	public double getRotationalPosition();

	// TODO test the test
	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			boolean run = true;
			while(!Thread.interrupted() && run) {
				long startTime = System.currentTimeMillis();
				while(startTime + 10000 > System.currentTimeMillis()){
					comms.displayText("Current state of " + this.getModuleName() + ": " + this.getRotationalPosition());
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
