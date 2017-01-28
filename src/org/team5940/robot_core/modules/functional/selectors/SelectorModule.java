package org.team5940.robot_core.modules.functional.selectors;

import org.team5940.robot_core.modules.Module;
/**
 * A Module that can select between multiple states.
 * @author Noah Sturgeon
 *
 */
public interface SelectorModule extends Module {

	/**
	 * Gets the number of states this module has.
	 * @return The number of states.
	 */
	public int getNumOfStates ();
	
	/**
	 * Gets the current state of this module.
	 * @return The current state of the module. Between 0 inclusive and getNumOfStates inclusive. Or -1 if no selected states were found.
	 */
	public int currentState ();
}
