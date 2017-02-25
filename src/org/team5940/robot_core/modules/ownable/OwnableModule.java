package org.team5940.robot_core.modules.ownable;

import org.team5940.robot_core.modules.Module;

/**
 * This extension of {@link Module} that provides an interface to lock access to a Module to a particular, executing (.isAlive() is true) Thread. This is useful to preventing unintentional concurrent usage, particularly of actuators. Any ownable extended dependencies of an OwnableModule also need to be acquired.
 * @author David Boles
 *
 */
public interface OwnableModule extends Module {
	
	/**
	 * This method determines whether this is currently owned by Thread t. Should return true if t == null and this is not owned. If the owner of this died, it is not owned.
	 * @param t The Thread to check ownership for.
	 * @return true if t == the Thread that owns this (or t == null and this is not owned), false otherwise.
	 */
	public boolean isOwnedBy(Thread t);
	
	/**
	 * Checks whether the current thread owns this.
	 * @return Whether the currently executing thread owns this.
	 * @see OwnableModule#isOwnedBy(Thread)
	 */
	public default boolean isOwnedByCurrent() {
		return this.isOwnedBy(Thread.currentThread());
	}
	
	/**
	 * Checks if this is not owned by returning the results of this.isOwnedBy(null).
	 * @return true if this is not owned, false otherwise.
	 * @see OwnableModule#isOwnedBy(Thread)
	 */
	public default boolean isNotOwned() {
		return this.isOwnedBy(null);
	}
	
	/**
	 * Determines whether or not t either owns this module or nothing does.
	 * @param t The Thread to check ownership for.
	 * @return this.isOwnedBy(t) or this.isNotOwned()
	 */
	public default boolean isAccessibleFrom(Thread t) {
		return this.isOwnedBy(t) || this.isNotOwned();
	}
	
	/**
	 * Checks this' accessibility from the current thread.
	 * @return Whether code in the currently executing thread can call methods in this.
	 * @see OwnableModule#isAccessibleFrom(Thread)
	 */
	public default boolean isAccessibleFromCurrent() {
		return this.isAccessibleFrom(Thread.currentThread());
	}
	
	/**
	 * Acquires ownership of this for t if:
	 * (no Thread currently owns this, or
	 * force == true), and
	 * t doesn't already own this.
	 * Returns the results of this.isOwnedBy(t).
	 * Note: t can be null if you are attempting to force ownership relinquishing.
	 * @param t The Thread to acquire ownership for.
	 * @param force Whether to force the ownership of this by t.
	 * @return true if t owns this, false otherwise.
	 * @see OwnableModule#isOwnedBy(Thread)
	 */
	public boolean acquireOwnershipFor(Thread t, boolean force);
	
	/**
	 * Acquires ownership of this for the currently executing thread (t) if:
	 * (no Thread currently owns this, or
	 * force == true), and
	 * t doesn't already own this.
	 * Returns the results of this.isOwnedBy(t).
	 * Note: t can be null if you are attempting to force ownership relinquishing.
	 * @param force Whether to force the ownership of this by t.
	 * @return true if t owns this, false otherwise.
	 * @see OwnableModule#acquireOwnershipFor(Thread, boolean)
	 * @see OwnableModule#isOwnedBy(Thread)
	 * @see OwnableModule#acquireOwnershipFor(Thread, boolean)
	 */
	public default boolean acquireOwnershipForCurrent(boolean force) {
		return this.acquireOwnershipFor(Thread.currentThread(), force);
	}
	
	/**
	 * Attempts to relinquish ownership of this by t if t currently owns this.
	 * @param t The Thread to acquire ownership for.
	 * @throws IllegalArgumentException If t == null. To force relinquishing for any Thread, call aquireOwnershipFor(null, true);
	 * @see OwnableModule#isOwnedBy(Thread)
	 */
	public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException;
	
	/**
	 * Attempts to relinquish ownership of this by the currently executing thread (t) if t currently owns this.
	 * @throws IllegalArgumentException If t == null. To force relinquishing for any Thread, call aquireOwnershipFor(null, true);
	 * @see OwnableModule#isOwnedBy(Thread)
	 * @see OwnableModule#relinquishOwnershipFor(Thread)
	 */
	public default void relinquishOwnershipForCurrent() throws IllegalArgumentException {
		this.relinquishOwnershipFor(Thread.currentThread());
	}
}
