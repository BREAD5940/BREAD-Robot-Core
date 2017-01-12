package org.team5940.robot_core.modules.interfaces;

import org.team5940.robot_core.modules.Module;

public interface TestableModule extends Module {
	
	/**
	 * Runs the test and returns true if the test runs.
	 * @return A true or false statement on if the test runs or not.
	 */
	public boolean runTest();
	
}
