package org.team5940.robot_core.modules.functional.pistons;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ownable.OwnableModule;
/**
 * Interface for controlling a piston.
 * @author Ryan Cen
 *
 */
public interface PistonModule extends OwnableModule {
	
	/**
	 * Gets the state of the piston.
	 * @return State of the piston as a boolean.
	 */
	public boolean getPistonState();
	
	/**
	 * Sets the state of the piston.
	 * @param state The piston state using boolean, True is extended, False is contracted.
	 */
	public void setPistonState(boolean state);
}
