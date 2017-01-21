package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.ownable.OwnableModule;
/**
 * Interface for the gearbox and shifting them.
 * @author Noah Sturgeon
 *
 */
public interface ShifterModule extends OwnableModule {
	/**
	 * Sets the ShifterModule to the gear specified. Lower number means lower speed.
	 * @param The gear to set this to.
	 * @throws IllegealArgumentException if gear >= getNumberOfGears() or gear < 0.
	 */
	public void setShift (int gear) throws IllegalArgumentException;
	
	/**
	 * Gets the gear this is set to.
	 * @return The current gear.
	 */
	public int getShift ();
	
	/**
	 * Gets the number of gears this can be set to.
	 * @return An int representing this' number of gears.
	 */
	public int getNumberOfGears ();
}
