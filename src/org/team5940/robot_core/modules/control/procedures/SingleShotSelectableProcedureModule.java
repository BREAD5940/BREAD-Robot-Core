package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

/**
 * A procedure that on start uses a {@link SelectorModule} to choose which other procedure to execute and intelligently acquires the one it's running and that one's extended dependencies.
 * @author David Boles
 * @see ContinuousSelectableProcedureModule
 *
 */
public class SingleShotSelectableProcedureModule extends AbstractProcedureModule {

	/**
	 * Stores this' selector.
	 */
	private final SelectorModule selector;
	
	/**
	 * Stores the procedure for selector state -1.
	 */
	private final ProcedureModule unselectedProcedure;
	
	/**
	 * Stores the procedures for selector states 0 through (n-1).
	 */
	private final ProcedureModule[] procedures;
	
	/**
	 * Stores whether this should force procedure dependency acquisition.
	 */
	private final boolean forceDependencyAquisition;
	
	/**
	 * Stores the current running procedure.
	 */
	private ProcedureModule running;
	
	/**
	 * Initializes a new {@link SingleShotSelectableProcedureModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector The selector that determines what procedure to run.
	 * @param unselectedProcedure The procedure to run when nothing is selected.
	 * @param procedures The procedures to run depending on the selected state.
	 * @param forceDependencyAquisition Whether to force acquisition of the running procedure and its extended dependencies.
	 * @throws IllegalArgumentException Thrown if any argument is null, procedures contains a null procedure, or the number of selector states does not equal the number of procedures.
	 */
	public SingleShotSelectableProcedureModule(String name, LoggerModule logger, SelectorModule selector, ProcedureModule unselectedProcedure, ProcedureModule[] procedures, boolean forceDependencyAquisition)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(procedures).chainPut(unselectedProcedure).chainPut(selector), logger);
		this.logger.checkInitializationArgs(this, SingleShotSelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
		if(procedures.length != selector.getNumberOfStates())
			this.logger.failInitializationIllegal(this, SingleShotSelectableProcedureModule.class, "Unequal Quantities", new Object[]{selector.getNumberOfStates(), procedures.length});
		for(ProcedureModule procedure : procedures)
			if(procedure == null)
				this.logger.failInitializationIllegal(this, SingleShotSelectableProcedureModule.class, "Null Procedure", procedure);
		this.selector = selector;
		this.unselectedProcedure = unselectedProcedure;
		this.procedures = procedures;
		this.forceDependencyAquisition = forceDependencyAquisition;
		this.logger.logInitialization(this, SingleShotSelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
	}

	@Override
	protected synchronized void doProcedureStart() throws Exception {
		ProcedureModule toRun = this.unselectedProcedure;
		int selection = this.selector.getCurrentState();
		if(selection != -1) toRun = this.procedures[selection];
		this.acquireAll(this.running);
		this.running = toRun;
		this.running.startProcedure();
		
	}

	@Override
	protected synchronized boolean doProcedureUpdate() throws Exception {
		return this.running.updateProcedure();
	}

	@Override
	protected synchronized void doProcedureClean() {
		try {
			this.running.cleanProcedure();
		} catch (ThreadUnauthorizedException e) {
			this.logger.error(this, "Failed Cleaning Running Procedure", this.running);
		}
		this.releaseAll(this.running);
		this.running = null;
	}

	/**
	 * Acquires p and all of p's extended dependencies for the current Thread.
	 * @param p The ProcedureModule to acquire dependencies for.
	 */
	private synchronized void acquireAll(ProcedureModule p) {
		this.logger.vLog(this, "Acquiring Dependencies", p);
		p.acquireOwnershipForCurrent(this.forceDependencyAquisition);
		for(OwnableModule m : p.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
			m.acquireOwnershipForCurrent(this.forceDependencyAquisition);
	}
	
	/**
	 * Releases p and all of p's extended dependencies for the current Thread.
	 * @param p The ProcedureModule to release dependencies for.
	 */
	private synchronized void releaseAll(ProcedureModule p) {
		this.logger.vLog(this, "Releasing Dependencies", p);
		p.relinquishOwnershipForCurrent();
		for(OwnableModule m : p.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
			m.relinquishOwnershipForCurrent();
	}

}
