package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

/**
 * A {@link ProcedureModule} is a small, iterative snippet of code. A {@link ProcedureModule} should only be run once at a time by a particular thread, either concurrently to other code by acquiring this and it's dependencies and then calling the start, update and clean methods manually or by using the run methods.
 * @author David Boles
 *
 */
public interface ProcedureModule extends OwnableModule, Runnable {
	
	/**
	 * Runs this gently (force = false) using {@link ProcedureModule#run(boolean)}.
	 * @see ProcedureModule#run(boolean)
	 */
	@Override
	default void run() {
		this.run(false);
	}
	
	/**
	 * Runs this with a 50ms delay.
	 * @see ProcedureModule#run(boolean, long)
	 */
	default void run(boolean forceAcquisition) {
		this.run(forceAcquisition, 50);
	}
	
	/**
	 * Acquires this and this' extended dependencies, then runs the procedure to completion or premature, external clean. Catches any exceptions internally.
	 * @param forceAcquisition Whether to force acquisition of this and this' extended dependencies.
	 * @param delay The number of milliseconds to wait between calls to updateProcedure() while running.
	 */
	default void run(boolean forceAcquisition, long delay) {
		this.getModuleLogger().log(this, "Running", new Object[]{forceAcquisition, delay});
		
		if(delay < 0) {
			delay = 0;
			this.getModuleLogger().vLog(this, "Negative Delay, Using 0", delay);
		}
		
		try {
			this.getModuleLogger().vLog(this, "Acquiring This", this.acquireOwnershipForCurrent(forceAcquisition));
			for(OwnableModule dep : this.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
				this.getModuleLogger().vLog(this, "Acquiring Extended Dependency", new Object[]{dep, dep.acquireOwnershipForCurrent(forceAcquisition)});
			
			this.getModuleLogger().vLog(this, "Starting");
			this.startProcedure();
			
			boolean done = false;
			while(!this.isReadyToStart() && !done) {
				done = this.updateProcedure();
				this.getModuleLogger().vLog(this, "Updated", done);
				
				Thread.sleep(delay);
			}
		}catch(Exception e) {
			this.getModuleLogger().error(this, "Exception Caught While Running", e);
		}
		
		this.getModuleLogger().log(this, "Cleaning Run");
		try{
			this.cleanProcedure();
		}catch(Exception e) {
			this.getModuleLogger().error(this, "Exception Caught While Cleaning", e);
		}
	}
	
	/**
	 * Starts the procedure. This should catch any exceptions internally and then remain ready if fatal.
	 * @throws ThreadUnauthorizedException Thrown if this is not accessible to the current thread.
	 * @throws IllegalStateException Thrown if this is not ready to be started.
	 * @see ProcedureModule#isReadyToStart()
	 */
	public void startProcedure() throws ThreadUnauthorizedException, IllegalStateException;
	
	/**
	 * Updates the procedure if it has been started. This should catch any exceptions internally and then return true if fatal. It may be called after a previous call returned true but before this has been cleaned.
	 * @return True if the procedure has finished updating (or errored, any other reason it's done), false otherwise.
	 * @throws ThreadUnauthorizedException Thrown if this is not accessible to the current thread.
	 * @throws IllegalStateException Thrown if this has not been started.
	 */
	public boolean updateProcedure() throws ThreadUnauthorizedException, IllegalStateException;
	
	/**
	 * Cleans the procedure, this resets the procedure so that it is ready to be started. Should be called if the procedure has finished updating or you want to terminate it prematurely. May be called even if this is already ready to be started.
	 * @throws ThreadUnauthorizedException Thrown if this is not accessible to the current thread.
	 */
	public void cleanProcedure() throws ThreadUnauthorizedException;
	
	/**
	 * Gets whether this can be started. 
	 * @return True if this can be started (newly initialized or cleaned), false otherwise.
	 */
	public boolean isReadyToStart();
	
	/**
	 * This procedure, named "inert_procedure", should be used whenever you want a procedure that doesn't do anything and finishes immediately.
	 */
	public static final ProcedureModule INERT_PROCEDURE = new ProcedureModule() {
		
		@Override
		public String getModuleName() {
			return "inert_procedure";
		}
		
		@Override
		public LoggerModule getModuleLogger() {
			return LoggerModule.INERT_LOGGER;
		}
		
		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>(LoggerModule.INERT_LOGGER);
		}
		
		@Override
		public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException {}
		
		@Override
		public boolean isOwnedBy(Thread t) {
			return true;
		}
		
		@Override
		public boolean acquireOwnershipFor(Thread t, boolean force) {
			return true;
		}
		
		@Override
		public boolean updateProcedure() throws ThreadUnauthorizedException, IllegalStateException {
			return true;
		}
		
		@Override
		public void startProcedure() throws ThreadUnauthorizedException, IllegalStateException {}
		
		@Override
		public void cleanProcedure() throws ThreadUnauthorizedException {}
		
		@Override
		public boolean isReadyToStart() {
			return true;
		}
	};
}
