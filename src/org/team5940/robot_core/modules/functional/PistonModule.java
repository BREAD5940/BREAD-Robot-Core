package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.Module;
/**
 * Interface for controlling a piston.
 * @author Ryan Cen
 *
 */
public interface PistonModule extends Module {
	
	/**
	 * The states that the piston can be in.
	 */
	public enum PistonState {
		
		/**
		 * The piston is extended.
		 */
		EXTENDED,
		
		/**
		 * The piston is contracted.
		 */
		CONTRACTED
	}
	
	/**
	 * Gets the state of the piston.
	 * @return State of the piston as an enum.
	 */
	public PistonState getPistonState();
	
	/**
	 * Sets the state of the piston.
	 * @param state the {@link PistonState} to set it to.
	 */
	public void setPistonState(PistonState state);
}
