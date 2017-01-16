package org.team5940.robot_core.modules.testable;

import org.team5940.robot_core.modules.Module;

/**
 * This extension of Module provides the interface to test modules and know the test's state. 
 * @author Ryan
 */
public interface TestableModule extends Module {
	
	/**
	 * States that the test can be in.
	 */
	public enum TestResult {
		 
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
	 * Runs the test and returns the result when complete.
	 * @return A TestResult representing the result of the test.
	 */
	public TestResult runTest(TestRunnerModule testRunner);
	
	
}
