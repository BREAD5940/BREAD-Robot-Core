package org.team5940.robot_core.modules.testing.communication;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.testing.TestableModule;

/**
 * This interface provides the input-output mechanism for tests in {@link TestableModule}s.
 * @author David Boles
 *
 */
public interface TestCommunicationModule extends Module {
	
	/**
	 * Displays a String to the test runner.
	 * @param text The String to display.
	 */
	public void displayText(String text);
	
	/**
	 * Prompts the user and hangs until a boolean response is received.
	 * @param prompt The prompt to display to the user.
	 * @return The boolean returned by the user.
	 * @throws InterruptedException Thrown if the thread is interrupted while waiting for a response.
	 */
	public boolean promptBoolean(String prompt) throws InterruptedException;
	
	/**
	 * Prompts the user and hangs until a String response is received.
	 * @param prompt The prompt to display to the user.
	 * @return The String returned by the user.
	 * @throws InterruptedException Thrown if the thread is interrupted while waiting for a response.
	 */
	public String promptText(String prompt) throws InterruptedException;
}
