package org.team5940.robot_core.modules.ownable;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;

/**
 * This extension of Module provides the interface to lock access to a Module to
 * a particular Thread. This is useful in preventing unintentional concurrent
 * usage, particularly of actuators.
 * 
 * @author David Boles
 *
 */
public abstract class SimpleOwnableModule extends SimpleModule implements OwnableModule {

	/**
	 * Stores the currentOwner of the module (the owner being a thread)
	 */
	private Thread currentOwner;
	/**
	 * A constructor that creates a SimpleOwnableModule.
	 * @param nameOfModule
	 */
	public SimpleOwnableModule(String nameOfModule, ModuleHashTable<Module> subModules) {
		super(nameOfModule, subModules);
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		if(t == null) {
			if(this.currentOwner == null || !this.currentOwner.isAlive()) return true;
			else return false;
		}else if (t.isAlive()) {
			if(this.currentOwner == t) return true;
			else return false;
		}else return false;
	}

	@Override
	public synchronized boolean acquireOwnershipFor(Thread t, boolean force) {
		if((this.isNotOwned() || force) && !this.isOwnedBy(t)) {
			this.currentOwner = t;
		}

		return this.isOwnedBy(t);
	}

	@Override
	public synchronized void relinquishOwnershipFor(Thread t) throws IllegalArgumentException {
		if (t==null){
			throw new IllegalArgumentException("Cannot relinquish ownership for a null owner.");
		}
		if (this.isOwnedBy(t)) {
			currentOwner = null;
		}
	}

}
