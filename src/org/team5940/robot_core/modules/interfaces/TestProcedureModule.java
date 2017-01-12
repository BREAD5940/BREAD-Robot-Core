package org.team5940.robot_core.modules.interfaces;

import org.team5940.robot_core.modules.interfaces.TestableModule.TestingState;

public interface TestProcedureModule extends ProcedureModule {
	
	/**
	 * Returns the state that the test is in.
	 * @return A TestingState representing the current state of this TestProcedureModule.
	 */
	public TestingState getTestingState();
}
