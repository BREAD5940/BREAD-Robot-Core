package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

/**
 * A procedure that continuously uses a {@link SelectorModule} to choose which other procedure to execute and intelligently acquires the one it's running and that one's extended dependencies.
 * @author David Boles
 * @see SingleShotSelectableProcedureModule
 *
 */
public class ContinuousSelectableProcedureModule extends AbstractProcedureModule {

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
	 * Stores the last updated procedure.
	 */
	private ProcedureModule lastUpdated;
	
	/**
	 * Initializes a new {@link ContinuousSelectableProcedureModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector The selector that determines what procedure to run.
	 * @param unselectedProcedure The procedure to run when nothing is selected.
	 * @param procedures The procedures to run depending on the selected state.
	 * @param forceDependencyAquisition Whether to force acquisition of the running procedure and its extended dependencies.
	 * @throws IllegalArgumentException Thrown if any argument is null, procedures contains a null procedure, or the number of selector states does not equal the number of procedures.
	 * 
	 */
	public ContinuousSelectableProcedureModule(String name, LoggerModule logger, SelectorModule selector, ProcedureModule unselectedProcedure, ProcedureModule[] procedures, boolean forceDependencyAquisition)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(procedures).chainPut(unselectedProcedure).chainPut(selector), logger);
		this.logger.checkInitializationArgs(this, ContinuousSelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
		if(procedures.length != selector.getNumberOfStates())
			this.logger.failInitializationIllegal(this, ContinuousSelectableProcedureModule.class, "Unequal Quantities", new Object[]{selector.getNumberOfStates(), procedures.length});
		for(ProcedureModule procedure : procedures)
			if(procedure == null)
				this.logger.failInitializationIllegal(this, ContinuousSelectableProcedureModule.class, "Null Procedure", procedure);
		this.selector = selector;
		this.unselectedProcedure = unselectedProcedure;
		this.procedures = procedures;
		this.forceDependencyAquisition = forceDependencyAquisition;
		this.logger.logInitialization(this, ContinuousSelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
	}

	@Override
	protected synchronized void doProcedureStart() throws Exception {
		this.lastUpdated = null;
	}

	@Override
	protected synchronized boolean doProcedureUpdate() throws Exception {
		ProcedureModule toUpdate = this.unselectedProcedure;
		int selection = this.selector.getCurrentState();
		if(selection != -1) toUpdate = this.procedures[selection];
		
		if(this.lastUpdated != toUpdate) {
			this.logger.log(this, "Changed Procedure", new Object[]{selection, toUpdate, this.lastUpdated});
			if(this.lastUpdated != null) this.lastUpdated.cleanProcedure();
			if(this.lastUpdated != null) this.releaseAll(this.lastUpdated);
			this.acquireAll(toUpdate);
			if(!toUpdate.isReadyToStart()) toUpdate.cleanProcedure();
			toUpdate.startProcedure();
		}
		
		toUpdate.updateProcedure();
		
		this.lastUpdated = toUpdate;
		return false;
	}

	@Override
	protected synchronized void doProcedureClean() {
		if(this.lastUpdated != null) {
			try {this.lastUpdated.cleanProcedure();}catch(Exception e) {this.logger.vError(this, "Exception Caught While Cleaning Last On Clean", this.lastUpdated);}
			this.releaseAll(this.lastUpdated);
		}
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
