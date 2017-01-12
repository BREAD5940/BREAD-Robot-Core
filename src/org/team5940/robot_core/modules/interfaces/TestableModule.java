package org.team5940.robot_core.modules.interfaces;

import org.team5940.robot_core.modules.Module;

public interface TestableModule extends Module {
	
	/**
	 * States that the test can be in.
	 */
	public enum TestingState {
		 
		/**
		 * The test is running.
		 */
		RUNNING,
		
		/**
		 * The test is successful.
		 */
		
		SUCCESSFUL,
		
		/**
		 * The test failed.
		 */
		FAILED,
		
		/**
		 * There is in error in the test.
		 */
		ERROR
	}
	
	/**
	 * Runs the test and returns true if the test runs.
	 * @return A true or false statement on if the test runs or not.
	 */
	public boolean runTest();
	
}
