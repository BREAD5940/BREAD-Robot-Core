package org.team5940.robot_core.modules;

import org.team5940.robot_core.modules.logging.LoggerModule;

public abstract class SimpleModule implements Module {
	/**
	 * Stores the name of this Module.
	 */
	private final String name;
	
	/**
	 * Stores the submodules of this Module.
	 */
	private final ModuleHashTable<Module> subModules;
	
	/**
	 * Stores the logger for this module.
	 */
	protected LoggerModule logger;
	
	/**
	 * Creates a new SimpleModule with given arguments.
	 * @param name The name of this Module.
	 * @param subModules The submodules of this Module.
	 * @param logger LoggerModule to initialize this with.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public SimpleModule(String name, ModuleHashTable<Module> subModules, LoggerModule logger) throws IllegalArgumentException {
		if(name == null || subModules == null || logger == null) {
			logger.vError(this, "SimpleModule Created With Null", new Object[]{name, subModules, logger});
			throw new IllegalArgumentException("Argument null!");
		}
		this.name = name;
		this.subModules = subModules;
		this.logger = logger;
		logger.log(this, "Created SimpleModule", new Object[]{name, subModules, logger});
	}
	
	@Override
	public String getName() {
		this.logger.vLog(this, "Name Accessed", this.name);
		return this.name;
	}
	

	@Override
	public ModuleHashTable<? extends Module> getSubModules() {
		ModuleHashTable<Module> out = new ModuleHashTable<Module>();
		out.putAll(this.subModules);
		this.logger.vLog(this, "SubModules Accessed", out);
		return out;
	}
	
	@Override
	public void setLogger(LoggerModule logger) throws IllegalArgumentException {
		if (logger == null) {
			this.logger.vError(this, "Logger Set With Null", logger);
			throw new IllegalArgumentException("Logger is null!");
		}
		logger.vLog(this, "Logger Switched To", new Object[]{this.logger, logger});
		this.logger = logger;
	}

}
