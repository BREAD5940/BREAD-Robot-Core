package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

/**
 * A procedure that continuously uses a {@link SelectorModule} to choose which other procedure to execute and intelligently acquires the one it's running and that one's extended dependencies.
 * @author David Boles
 *
 */
public class SelectableProcedureModule extends AbstractProcedureModule {//TODO make single shot

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
	 * Initializes a new {@link SelectableProcedureModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector The selector that determines what procedure to run.
	 * @param unselectedProcedure The procedure to run when nothing is selected.
	 * @param procedures The procedures to run depending on the selected state.
	 * @param forceDependencyAquisition Whether to force acquisition of the running procedure and its extended dependencies.
	 * @throws IllegalArgumentException Thrown if any argument is null, procedures contains a null procedure, or the number of selector states does not equal the number of procedures.
	 * 
	 */
	public SelectableProcedureModule(String name, LoggerModule logger, SelectorModule selector, ProcedureModule unselectedProcedure, ProcedureModule[] procedures, boolean forceDependencyAquisition)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(procedures).chainPut(unselectedProcedure).chainPut(selector), logger);
		this.logger.checkInitializationArgs(this, SelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
		if(procedures.length != selector.getNumberOfStates()) this.logger.failInitializationIllegal(this, SelectableProcedureModule.class, "Unequal Quantities", new Object[]{selector.getNumberOfStates(), procedures.length});
		for(ProcedureModule procedure : procedures)
			if(procedure == null) this.logger.failInitializationIllegal(this, SelectableProcedureModule.class, "Null Procedure", procedure);
		this.selector = selector;
		this.unselectedProcedure = unselectedProcedure;
		this.procedures = procedures;
		this.forceDependencyAquisition = forceDependencyAquisition;
		this.logger.logInitialization(this, SelectableProcedureModule.class, new Object[]{selector, unselectedProcedure, procedures, forceDependencyAquisition});
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
			if(this.lastUpdated != null) this.releaseExtended(this.lastUpdated);
			this.acquireExtended(toUpdate);
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
			this.releaseExtended(this.lastUpdated);
		}
	}

	/**
	 * Acquires all of p's extended dependencies for the current Thread.
	 * @param p The ProcedureModule to acquire dependencies for.
	 */
	private void acquireExtended(ProcedureModule p) {
		this.logger.vLog(this, "Acquiring Dependencies", p);
		for(OwnableModule m : p.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
			m.acquireOwnershipForCurrent(this.forceDependencyAquisition);
	}
	
	/**
	 * Releases all of p's extended dependencies for the current Thread.
	 * @param p The ProcedureModule to release dependencies for.
	 */
	private void releaseExtended(ProcedureModule p) {
		this.logger.vLog(this, "Releasing Dependencies", p);
		for(OwnableModule m : p.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
			m.relinquishOwnershipForCurrent();
	}
}
