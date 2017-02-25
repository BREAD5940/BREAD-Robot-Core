package org.team5940.robot_core.modules.ownable;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * This is an abstract, reference implementation of {@link OwnableModule}.
 * @author David Boles
 * @see OwnableModule
 *
 */
public abstract class AbstractOwnableModule extends AbstractModule implements OwnableModule {

	/**
	 * Stores the current or dead previous owner of the module.
	 */
	private Thread currentOwner;
	
	/**
	 * A constructor that creates a SimpleOwnableModule.
	 * @param name This' name.
	 * @param dependencies This' dependencies.
	 * @param logger This' logger.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public AbstractOwnableModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger) throws IllegalArgumentException{
		super(name, dependencies, logger);
		this.logger.logInitialization(this, AbstractOwnableModule.class);
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		if(t == null) {
			if(this.currentOwner == null || !this.currentOwner.isAlive()) {
				this.logger.logGot(this, "Is Owned By", new Object[]{t, true});
				return true;
			}
			else {
				this.logger.logGot(this, "Is Owned By", new Object[]{t, false});
				return false;
			}
		}else if (t.isAlive()) {
			if(this.currentOwner == t) {
				this.logger.logGot(this, "Is Owned By", new Object[]{t, true});
				return true;
			}
			else {
				this.logger.logGot(this, "Is Owned By", new Object[]{t, false});
				return false;
			}
		}
		this.logger.logGot(this, "Is Owned By", new Object[]{t, false});
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

	/**
	 * Does the accessibility check that every active method of an implementation should do.
	 * @throws ThreadUnauthorizedException Thrown if this is not accessible from the current thread.
	 */
	protected void doAccessibilityCheck() throws ThreadUnauthorizedException {
		if(!this.isAccessibleFromCurrent()) {
			this.logger.vError(this, "Access Denied", new Object[] {Thread.currentThread(), this.currentOwner});
			throw new ThreadUnauthorizedException(this, Thread.currentThread(), this.currentOwner);
		}
			
	}
}
