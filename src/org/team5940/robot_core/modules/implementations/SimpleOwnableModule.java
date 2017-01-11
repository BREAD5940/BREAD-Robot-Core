package org.team5940.robot_core.modules.implementations;

import org.team5940.robot_core.modules.interfaces.OwnableModule;

/**
 * This extension of Module provides the interface to lock access to a Module to a particular Thread. This is useful in preventing unintentional concurrent usage, particularly of actuators.
 * @author David Boles
 *
 */
public abstract class SimpleOwnableModule implements OwnableModule {
	/**
	 * Stores the currentOwner of the module (the owner being a thread)
	 */
	Thread currentOwner;
	String nameOfModule = "SimpleOwnableModule";
/**
 * Gets the name of the module
 */
	@Override
	public synchronized String getName() {
		return nameOfModule;
	}

	/**
	 * This method determines whether this is currently owned by Thread t. Should return true if t == null and this is not owned.
	 * @param t The Thread to check ownership for.
	 * @return true if t == the Thread that owns this (or t == null and this is not owned), false otherwise.
	 */
	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		
		if (currentOwner == t) {

			return true;
		} else if (currentOwner == null) {
			return true;
		} else if (t==null) {
			return true;
		}
		else {
			return false;
		}

	}

	/**
	 * Attempts to acquire ownership of this by t if:
	 * (no Thread currently owns this, or
	 * force == true), and
	 * t doesn't already own this.
	 * Returns the results of this.isOwnedBy(t).
	 * Note: t can be null if you are attempting to force ownership relinquishing.
	 * @param t The Thread to acquire ownership for.
	 * @param force Whether to force the ownership of this by t.
	 * @return true if t owns this, false otherwise.
	 */
	@Override
	public boolean aquireOwnershipFor(Thread t, boolean force) {
		
		if (currentOwner == null && currentOwner != t){
			currentOwner = t;
		} else if (force == true && currentOwner != t){
			currentOwner = t;
		}
		
		
		return this.isOwnedBy(t);
	}

	/**
	 * Attempts to relinquish ownership of this by t if t currently owns this.
	 * Returns the results of this.isOwnedBy(t).
	 * @param t The Thread to acquire ownership for.
	 * @return true if t owns this, false otherwise.
	 * @throws IllegalArgumentException If t == null. To force relinquishing for any Thread, call aquireOwnershipFor(null, true);
	 */
	@Override
	public boolean relinquishOwnershipFor(Thread t) {
		if (this.isOwnedBy(t)==true && t != null){
			currentOwner=null;
		}
		return this.isOwnedBy(t);
	}

}
