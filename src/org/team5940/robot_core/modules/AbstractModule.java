package org.team5940.robot_core.modules;

import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * This is an abstract, reference implementation of {@link Module}.
 * @author David Boles
 * @see Module
 * 
 */
public abstract class AbstractModule implements Module {
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
	 * Creates a new AbstractModule with given arguments.
	 * @param name The name of this Module.
	 * @param dependencies The dependencies of this Module other than logger.
	 * @param logger LoggerModule to initialize this with.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public AbstractModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger) throws IllegalArgumentException {
		logger.checkInitializationArgs(this, AbstractModule.class, new Object[]{name, dependencies, logger});
		dependencies.put(logger);
		this.name = name;
		this.dependencies = dependencies;
		this.logger = logger;
		this.logger.logInitialization(this, AbstractModule.class, new Object[]{name, dependencies, logger});
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
