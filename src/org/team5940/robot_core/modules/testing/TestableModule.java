package org.team5940.robot_core.modules.testing;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * A module that contains a method to test itself. Any code running a test will need to acquire this and any extended dependencies.
 * @author David Boles
 *
 */
public interface TestableModule extends Module {
	/**
	 * The possible results of a test.
	 */
	public static enum TestStatus {
		/**
		 * The test passed, the module is working properly!
		 */
		PASSED,
		/**
		 * The test failed, the module is not working properly.
		 */
		FAILED,
		/**
		 * The test errored in some way. This is likely due to acquisition issues.
		 */
		ERRORED
	}
	
	/**
	 * Creates and returns a new {@link Test} to test the module.
	 * @param comms The {@link TestCommunicationModule} the test should use.
	 * @return A new {@link Test} to test this module.
	 * @throws IllegalArgumentException Thrown if testInterface is null.
	 */
	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException;
}
