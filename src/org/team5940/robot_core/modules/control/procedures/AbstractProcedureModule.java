package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

/**
 * An abstract, reference implementation of {@link ProcedureModule} that handles the details of exception catching, states, and ownership for you.
 * @author David Boles
 *
 */
public abstract class AbstractProcedureModule extends AbstractOwnableModule implements ProcedureModule {

	/**
	 * Stores whether this is ready to be started.
	 */
	private boolean ready;
	
	/**
	 * Stores whether updating is finished, either doProcedureUpdate() returned true or threw exception.
	 */
	private boolean finished = true;
	
	public AbstractProcedureModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger)
			throws IllegalArgumentException {
		super(name, dependencies, logger);
		this.ready = true;
		this.logger.logInitialization(this, AbstractProcedureModule.class);
	}

	/**
	 * Starts the procedure. Potentially called once after initialization and after a clean.
	 * @throws Exception Thrown if any fatal internal exception occurs.
	 */
	protected abstract void doProcedureStart() throws Exception;
	
	/**
	 * Updates the procedure. Called multiple times after the procedure is started, not once returns true or throws exception.
	 * @return True if this has completed updating, false otherwise.
	 * @throws Exception Thrown if any fatal internal exception occurs.
	 */
	protected abstract boolean doProcedureUpdate() throws Exception;
	
	/**
	 * Cleans the procedure, could be called at any point.
	 * @throws Exception Thrown if any fatal internal exception occurs.
	 */
	protected abstract void doProcedureClean() throws Exception;
	
	
	@Override
	public synchronized void startProcedure() throws ThreadUnauthorizedException, IllegalStateException {
		this.doAccessibilityCheck();
		if(!this.ready) throw new IllegalStateException("Not ready!");
		try {
			this.logger.log(this, "Starting");
			this.doProcedureStart();
			this.ready = false;
			this.finished = false;
		}catch(Exception e) {
			this.logger.error(this, "Exception Caught While Starting", e);
		}

	}

	@Override
	public synchronized boolean updateProcedure() throws ThreadUnauthorizedException, IllegalStateException {
		this.doAccessibilityCheck();
		if(this.ready) throw new IllegalStateException("Not started!");
		try {
			if(!finished)finished = this.doProcedureUpdate();
			this.logger.vLog(this, "Updated", finished);
			return finished;
		}catch(Exception e) {
			this.logger.error(this, "Exception Caught While Updating", e);
			finished = true;
			return true;
		}
	}

	@Override
	public synchronized void cleanProcedure() throws ThreadUnauthorizedException {
		this.doAccessibilityCheck();
		try {
			this.doProcedureClean();
		}catch (Exception e) {
			this.logger.error(this, "Exception Caught While Updating", e);
		}
		this.ready = true;
	}

	@Override
	public synchronized boolean isReadyToStart() {
		this.logger.logGot(this, "Is Ready To Start", this.ready);
		return this.ready;
	}

}
