package org.team5940.robot_core.modules.ownable;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.logging.LoggerModule;

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
	public SimpleOwnableModule(String nameOfModule, ModuleHashTable<Module> subModules, LoggerModule logger) {
		super(nameOfModule, subModules, logger);
		this.logger.log(this, "Created SimpleOwnableModule");
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		if(t == null) {
			if(this.currentOwner == null || !this.currentOwner.isAlive()) {
				this.logger.vLog(this, "Owned By", t);
				return true;
			}
			else {
				this.logger.vLog(this, "Not Owned By", t);
				return false;
			}
		}else if (t.isAlive()) {
			if(this.currentOwner == t) {
				this.logger.vLog(this, "Owned By", t);
				return true;
			}
			else {
				this.logger.vLog(this, "Not Owned By", t);
				return false;
			}
		}
		this.logger.vLog(this, "Not Owned By", t);
		return false;
	}

	@Override
	public synchronized boolean acquireOwnershipFor(Thread t, boolean force) {
		if((this.isNotOwned() || force) && !this.isOwnedBy(t)) {
			this.logger.vLog(this, "Aquiring Ownership For", new Object[]{t, force});
			this.currentOwner = t;
		}

		boolean out = this.isOwnedBy(t);
		this.logger.vLog(this, "Now Owned By", new Object[]{t, out});
		return out;
	}

	@Override
	public synchronized void relinquishOwnershipFor(Thread t) throws IllegalArgumentException {
		if (t==null){
			this.logger.vError(this, "Relinquishing Ownership For Null");
			throw new IllegalArgumentException("Cannot relinquish ownership for a null owner.");
		}
		if (this.isOwnedBy(t)) {
			this.logger.vLog(this, "Relinquishing Ownership", t);
			currentOwner = null;
		}
	}

}
