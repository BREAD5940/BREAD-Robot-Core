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
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public SimpleModule(String name, ModuleHashTable<Module> subModules) throws IllegalArgumentException {
		if(name == null || subModules == null) throw new IllegalArgumentException("Argument null!");
		this.name = name;
		this.subModules = subModules;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

	@Override
	public ModuleHashTable<? extends Module> getSubModules() {
		ModuleHashTable<Module> out = new ModuleHashTable<Module>();
		out.putAll(this.subModules);
		return out;
	}
	
	@Override
	public void setLogger(LoggerModule logger) throws IllegalArgumentException {
		if (logger == null) throw new IllegalArgumentException("Logger is null!");
		this.logger = logger;
		
	}

}
