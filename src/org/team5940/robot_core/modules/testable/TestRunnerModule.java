package org.team5940.robot_core.modules.testable;

import org.team5940.robot_core.modules.procedure.ProcedureModule;

public interface TestRunnerModule extends ProcedureModule {
	
	/**
	 * Prompts the tester with text.
	 * @throws IllegalArgumentException if text is null
	 */
	public void promptText(String text) throws IllegalArgumentException;
	
	/**
	 * Reads the reply of the tester and returns it as a string.
	 */
	public String getReturnedText();
	
	/**
	 * Tells you if there is new text returned since last prompt.
	 * @return True if new text is returned, false if there is none.
	 */
	public boolean getNewReturn();
	
	/**
	 * Delays until getNewReturn() returns true.
	 * @return true if new return, false if timed out.
	 */
	public default void delayUntilNewReturn() {
		while(!getNewReturn()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { }
		}
	}
}