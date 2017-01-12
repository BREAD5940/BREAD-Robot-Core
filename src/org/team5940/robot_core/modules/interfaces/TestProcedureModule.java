package org.team5940.robot_core.modules.interfaces;

import org.team5940.robot_core.modules.Module;

public interface TestProcedureModule extends Module {
	
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
	 * Returns the state that the test is in.
	 * @return A TestingState representing the current state of this TestProcedureModule.
	 */
	public TestingState getTestingState();
}
