package org.team5940.robot_core.modules.interfaces;

import org.team5940.robot_core.modules.Module;

public interface TestableModule extends Module {
	
	/**
	 * Runs the test. Prints that the test is running.
	 */
	public boolean runTest();
	
}
