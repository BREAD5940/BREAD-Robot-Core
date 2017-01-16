package org.team5940.robot_core.modules.ownable;

import org.team5940.robot_core.modules.Module;

/**
 * This extension of Module provides the interface to lock access to a Module to a particular Thread (please keep in mind, the Thread must be alive...). This is useful in preventing unintentional concurrent usage, particularly of actuators. Any submodules of an OwnableModule that implement OwnableModule should also be acquired at the same time as they might be necessary for the proper functioning of a Module.
 * @author David Boles
 *
 */
public interface OwnableModule extends Module {
	/**
	 * This method determines whether this is currently owned by Thread t. Should return true if t == null and this is not owned or the owner died.
	 * @param t The Thread to check ownership for.
	 * @return true if t == the Thread that owns this and t has not died (or t == null and this is not owned or is owned by a dead Thread), false otherwise.
	 */
	public boolean isOwnedBy(Thread t);
	
	/**
	 * Checks if this is not owned by returning the results of this.isOwnedBy(null).
	 * @return true if this is not owned or the owner died, false otherwise.
	 */
	public default boolean isNotOwned() {
		return this.isOwnedBy(null);
	}
	
	/**
	 * Attempts to acquire ownership of this by t if:
	 * (no Thread currently owns this or it died, or
	 * force == true), and
	 * t doesn't already own this.
	 * Returns the results of this.isOwnedBy(t).
	 * Note: t can be null if you are attempting to force ownership relinquishing.
	 * @param t The Thread to acquire ownership for.
	 * @param force Whether to force the ownership of this by t.
	 * @return true if t owns this, false otherwise.
	 */
	public boolean acquireOwnershipFor(Thread t, boolean force);
	
	/**
	 * Attempts to relinquish ownership of this by t if t currently owns this and hasn't died.
	 * @param t The Thread to acquire ownership for.
	 * @throws IllegalArgumentException If t == null. To force relinquishing for any Thread, call aquireOwnershipFor(null, true);
	 */
	public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException;
}
