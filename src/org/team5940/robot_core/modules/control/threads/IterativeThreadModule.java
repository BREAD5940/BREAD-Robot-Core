package org.team5940.robot_core.modules.control.threads;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * A reference, abstract implementation of ThreadModule that runs updates until interruption.
 * @author David Boles
 *
 */
public abstract class IterativeThreadModule extends ThreadModule {
	
	/**
	 * Initializes a new UpdatingThreadModule.
	 * @param name This' name.
	 * @param dependencies This' dependencies.
	 * @param logger This' logger.
	 * @param enabled Whether or not to default to enabling updating.
	 * @throws IllegalArgumentException If any argument is null.
	 */
	public IterativeThreadModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger)
			throws IllegalArgumentException {
		super(name, dependencies, logger);
		this.logger.logInitialization(this, IterativeThreadModule.class);
	}
	
	/**
	 * Called on initial thread start.
	 * @throws Exception Thrown if any fatal exception occurs.
	 */
	protected abstract void startThread() throws Exception;
	
	/**
	 * Called on thread update.
	 * @throws Exception Thrown if any fatal exception occurs.
	 */
	protected abstract void updateThread() throws Exception;
	
	/**
	 * Called after thread is interrupted or any other abstract method throws an exception.
	 */
	protected abstract void cleanThread();
	
	@Override
	public final void run() {
		try {
			this.logger.log(this, "Starting");
			this.startThread();
			
			while(!this.isInterrupted()) {
				this.logger.vLog(this, "Updating");
				this.updateThread();
				
				Thread.sleep(50);
			}
		}catch(Exception e) {this.logger.log(this, "Exception Occured In Start or Update", e);}
		this.logger.log(this, "Cleaning");
		this.cleanThread();
	}
	
}