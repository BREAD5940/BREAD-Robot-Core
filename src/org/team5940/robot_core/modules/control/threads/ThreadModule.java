package org.team5940.robot_core.modules.control.threads;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.control.procedures.ProcedureModule;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * An extension of Thread that implements Module. You can either initialize it with a {@link ProcedureModule} or extend it and override run().
 * @author David Boles
 *
 */
public class ThreadModule extends Thread implements Module {
	/**
	 * Stores this' name.
	 * @see Module#getModuleName()
	 */
	private final String name;
	
	/**
	 * Stores this' dependencies.
	 * @see Module#getModuleDependencies()
	 * @see Module#getExtendedModuleDependencies()
	 */
	private final ModuleHashtable<Module> dependencies;
	
	/**
	 * Stores this' logger.
	 * @see Module#getModuleLogger()
	 * @see Module#setModuleLogger(LoggerModule)
	 */
	protected LoggerModule logger;
	
	/**
	 * Initializes a new ThreadModule with given arguments.
	 * @param name The name of this ThreadModule.
	 * @param dependencies The dependencies of this ThreadModule other than logger.
	 * @param logger LoggerModule to initialize this with.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public ThreadModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger) throws IllegalArgumentException {
		super(name);
		logger.checkInitializationArgs(this, ThreadModule.class, new Object[]{name, dependencies, logger});
		dependencies.put(logger);
		this.name = name;
		this.dependencies = dependencies;
		this.logger = logger;
		this.logger.logInitialization(this, ThreadModule.class, new Object[]{name, dependencies, logger});
	}
	
	/**
	 * Initializes a new ThreadModule that will (if run() isn't overridden) run a procedure.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param procedure The procedure to run.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public ThreadModule(String name, LoggerModule logger, ProcedureModule procedure) throws IllegalArgumentException {
		super(procedure);
		logger.checkInitializationArgs(this, ThreadModule.class, new Object[]{name, logger, procedure});
		this.name = name;
		this.dependencies = new ModuleHashtable<>();
		dependencies.put(logger);
		dependencies.put(procedure);
		this.logger = logger;
		this.logger.logInitialization(this, ThreadModule.class, new Object[]{name, logger, procedure});
		
	}
	
	@Override
	public String getModuleName() {
		this.logger.logGot(this, "Module Name", this.name);
		return this.name;
	}
	

	@Override
	public ModuleHashtable<Module> getModuleDependencies() {
		ModuleHashtable<Module> out = new ModuleHashtable<Module>();
		out.putAll(this.dependencies);
		this.logger.logGot(this, "Dependencies", out);
		return out;
	}

	@Override
	public LoggerModule getModuleLogger() {
		return this.logger;
	}

}
