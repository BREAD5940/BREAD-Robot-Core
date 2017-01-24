package org.team5940.robot_core.modules.procedure;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.procedure.ProcedureModule.ProcedureState;

public abstract class SimpleProcedureModule extends SimpleModule implements ProcedureModule {
	
	/**
	 * Stores the modules required by this SimpleProcedureModule.
	 */
	private ModuleHashTable<OwnableModule> requiredModules;
	
	/**
	 * Stores whether or not to force OwnableModule acquisition on Procedure start.
	 */
	boolean forceOwnership;
	
	/**
	 * Stores the standard delay in between the Procedure performing different actions.
	 */
	long millisDelay;
	
	/**
	 * Stores the current Thread of this ProcedureModule.
	 */
	private SimpleProcedureThread currentThread = new SimpleProcedureThread(this);
	
	/**
	 * Stores the current state of this ProcedureModule.
	 */
	ProcedureState state = ProcedureState.NOT_STARTED;
	
	/**
	 * Creates a new ProcedureModule with given arguments.
	 * @param name The name of this Module.
	 * @param subModules The submodules of this Module.
	 * @param requiredModules OwnableModule-s that must be acquired by this procedure.
	 * @param forceOwnership Whether or not to force OwnableModule acquisition on Procedure start.
	 * @param millisDelay The standard delay in between internal calls. Use 50 as a default...
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public SimpleProcedureModule(String name, ModuleHashTable<Module> subModules, ModuleHashTable<OwnableModule> requiredModules, boolean forceOwnership,  long millisDelay) throws IllegalArgumentException {
		super(name, subModules);
		if(requiredModules == null) throw new IllegalArgumentException("Argument null!");
		this.requiredModules = requiredModules;
		this.forceOwnership = forceOwnership;
		this.millisDelay = millisDelay;
	}
	
	/**
	 * Interrupts this, should get called if overridden.
	 * 
	 * Super docs:
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void shutDown() {
		this.interrupt();
	}

	@Override
	public synchronized void start() {
		if(this.getState() != ProcedureState.RUNNING) {
			this.currentThread = new SimpleProcedureThread(this);
			currentThread.start();
			while(this.getState() != ProcedureState.RUNNING);
		}
	}

	@Override
	public synchronized void interrupt() {
		if(this.getState() == ProcedureState.RUNNING) {
			this.currentThread.interruptReason = ProcedureState.INTERRUPTED;
			this.currentThread.interrupt();
			while(this.getState() != ProcedureState.INTERRUPTED);
		}
	}

	@Override
	public synchronized ProcedureState getState() {
		return this.state;
	}
	
	//OWNERSHIP STUFF
	/**
	 * Sees if this Procedure's Thread owns all of it's required OwnableModules. Does not check if Procedure has stopped for any reason.
	 * @return true if it does, false otherwise.
	 * @see OwnableModule#isOwnedBy(Thread)
	 */
	protected synchronized boolean ownsAllRequired() {
		boolean out = true;
		
		
		for(OwnableModule module : requiredModules.values()) {
			if(!module.isOwnedBy(this.currentThread)) out = false;
		}
		
		return out;
	}
	
	/**
	 * Sees if this nothing owns any of this' required OwnableModules. Does not check if Procedure has stopped for any reason.
	 * @return true if nothing does, false otherwise.
	 * @see OwnableModule#isNotOwned()
	 */
	protected synchronized boolean noneOwnsAllRequired() {
		boolean out = true;
		
		for(OwnableModule module : requiredModules.values()) {
			if(!module.isNotOwned()) return false;
		}

		return out;
	}
	
	/**
	 * Attempts to acquire ownership for all of this' required OwnableModules. Does not check if Procedure has stopped for any reason.
	 * @param force Whether to force acquisition.
	 * @return the result of this.ownsAllRequired() after attempting all acquisitions.
	 * @see OwnableModule#acquireOwnershipFor(Thread, boolean)
	 */
	public synchronized boolean acquireAllRequired(boolean force) {
		
		for(OwnableModule module : requiredModules.values()) {
			module.acquireOwnershipFor(this.currentThread, force);
		}
		
		return this.ownsAllRequired();
	}
	
	/**
	 * Attempts to relinquish ownership for all of this' required OwnableModules if this ProcedureModule has a Thread.
	 */
	public synchronized void relinquishAllRequired() {
		if(this.currentThread != null) {
			
			for(OwnableModule module : requiredModules.values()) {
				module.relinquishOwnershipFor(this.currentThread);
			}
		}
	}
	
	//METHODS FOR SUBS TO IMPLEMENT
	/**
	 * First method called after the Procedure starts and attempts to aquire ownership of required OwnableModules.
	 */
	protected abstract void procedureStart();
	
	/**
	 * Called repetitively from within the Procedure after starting while the thread has access to requirements, it hasn't finished, and it hasn't been interrupted.
	 * @throws Exception if any unhandled error occurs when updating.
	 */
	protected abstract void procedureUpdate() throws Exception;
	
	/**
	 * Determines if procedureUpdate() should stop being called.
	 * @return A boolean representing whether updates have completed normally.
	 */
	protected abstract boolean procedureUpdatesComplete();
	
	/**
	 * Called if this Procedure no longer owns all of it's required {@link OwnableModule}s (or they weren't owned and now are) while it was trying to update. If ownership is regained, the Procedure will continue updating, otherwise it will stop and go into the {@link ProcedureState#ERRORED} state.
	 */
	protected abstract void procedureOwnershipLost();
	
	/**
	 * Called if this Procedure was .interrupt()ed after .procedureUpdate() has completed execution but before .procedureClean() is called.
	 */
	protected abstract void procedureInterrupted();

	/**
	 * Last method called before the Procedure relinquishes ownership of required OwnableModules and terminates.
	 */
	protected abstract void procedureClean();
	
	
}

class SimpleProcedureThread extends Thread {
	
	protected final SimpleProcedureModule procedure;
	
	ProcedureState interruptReason = ProcedureState.RUNNING;
	
	public SimpleProcedureThread(SimpleProcedureModule procedure) {
		this.procedure = procedure;
	}
	
	/**
	 * Start point for Thread execution.
	 */
	@Override
	public void run() {
		this.procedure.state = ProcedureState.RUNNING;
		this.procedure.acquireAllRequired(this.procedure.forceOwnership);
		this.procedure.procedureStart();
		
		this.doDelay();
		
		while(!this.isInterrupted()) {
			if(!this.procedure.procedureUpdatesComplete()) {
				if(this.procedure.ownsAllRequired() || this.procedure.noneOwnsAllRequired()) {
					try {
						this.procedure.procedureUpdate();
					} catch (Exception e) {
						this.interruptReason = ProcedureState.ERRORED;
						this.interrupt();
					}
				}else {
					this.procedure.procedureOwnershipLost();
					if(!(this.procedure.ownsAllRequired() || this.procedure.noneOwnsAllRequired())) {
						this.interruptReason = ProcedureState.ERRORED;
						this.interrupt();
					}
				}
			}else {
				this.interruptReason = ProcedureState.FINISHED;
				this.interrupt();
			}
			this.doDelay();
		}
		
		this.procedure.procedureClean();
		this.procedure.relinquishAllRequired();
		this.procedure.state = this.interruptReason;
	}
	
	/**
	 * Delays the standard amount for this SimpleProcedureModule.
	 */
	private void doDelay() {
		try {
			Thread.sleep(this.procedure.millisDelay);
		} catch (InterruptedException e) { }
	}
}