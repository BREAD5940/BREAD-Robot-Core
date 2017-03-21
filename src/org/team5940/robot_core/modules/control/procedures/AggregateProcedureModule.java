package org.team5940.robot_core.modules.control.procedures;

import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * An implementation of {@link ProcedureModule} that runs multiple other procedures.
 * @author David Boles
 *
 */
public class AggregateProcedureModule extends AbstractProcedureModule {

	/**
	 * Stores the procedures to run.
	 */
	private final ModuleHashtable<ProcedureModule> procedures;
	
	/**
	 * Stores whether to force acquisition of procedures and dependencies.
	 */
	private final boolean forceAcquisition;
	
	/**
	 * Initializes a new {@link AggregateProcedureModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param procedures The procedures to run.
	 * @param forceAcquisition Whether to force acquisition of procedures and dependencies.
	 * @throws IllegalArgumentException
	 */
	public AggregateProcedureModule(String name, LoggerModule logger, ModuleHashtable<ProcedureModule> procedures, boolean forceAcquisition)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(procedures.values()), logger);
		this.logger.checkInitializationArgs(this, AggregateProcedureModule.class, new Object[]{procedures, forceAcquisition});
		this.procedures = procedures;
		this.forceAcquisition = forceAcquisition;
		this.logger.logInitialization(this, AggregateProcedureModule.class, new Object[]{procedures, forceAcquisition});
	}

	@Override
	protected void doProcedureStart() throws Exception {
		for(ProcedureModule p : this.procedures.values())
			this.acquireAll(p);
		for(ProcedureModule p : this.procedures.values())
			p.startProcedure();
	}

	@Override
	protected boolean doProcedureUpdate() throws Exception {
		boolean allDone = true;
		for(ProcedureModule p : this.procedures.values())
			allDone = p.updateProcedure() && allDone;
		return allDone;
	}

	@Override
	protected void doProcedureClean() throws Exception {
		for(ProcedureModule p : this.procedures.values())
			p.cleanProcedure();
		for(ProcedureModule p : this.procedures.values())
			this.releaseAll(p);
	}

	/**
	 * Acquires p and all of p's extended dependencies for the current Thread.
	 * @param p The ProcedureModule to acquire dependencies for.
	 */
	private synchronized void acquireAll(ProcedureModule p) {
		this.logger.vLog(this, "Acquiring Dependencies", p);
		p.acquireOwnershipForCurrent(this.forceAcquisition);
		for(OwnableModule m : p.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class).values())
			m.acquireOwnershipForCurrent(this.forceAcquisition);
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
