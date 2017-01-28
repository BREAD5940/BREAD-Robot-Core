package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;

/**
 * This LoggerModule does nothing. You have to have one of these at the base of your robot logging framework for loggers to be able to log.
 * @author David Boles
 *
 */
public class NullLoggerModule implements LoggerModule {
	/**
	 * Stores the name of this Module.
	 */
	private final String name;
	
	/**
	 * Stores the submodules of this Module.
	 */
	private final ModuleHashTable<Module> subModules;
	
	/**
	 * Creates a new SimpleModule with given arguments.
	 * @param name The name of this Module.
	 * @param subModules The submodules of this Module.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public NullLoggerModule(String name, ModuleHashTable<Module> subModules) throws IllegalArgumentException {
		if(name == null || subModules == null) {
			throw new IllegalArgumentException("Argument null!");
		}
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
	public void initialize() {}

	@Override
	public void shutDown() {}

	@Override
	public void setLogger(LoggerModule logger) throws IllegalArgumentException {}

	@Override
	public void log(Module module, String log) {}

	@Override
	public void log(Module module, String title, Object content) {}

	@Override
	public void log(Module module, Object content) {}

	@Override
	public void log(Module module, Object[] content) {}

	@Override
	public void vLog(Module module, String log) {}

	@Override
	public void vLog(Module module, String title, Object content) {}

	@Override
	public void vLog(Module module, Object content) {}

	@Override
	public void vLog(Module module, Object[] content) {}

	@Override
	public void error(Module module, String log) {}

	@Override
	public void error(Module module, String title, Object content) {}

	@Override
	public void error(Module module, String title, Object[] content) {}

	@Override
	public void error(Module module, Object content) {}

	@Override
	public void vError(Module module, String log) {}

	@Override
	public void vError(Module module, String title, Object content) {}

	@Override
	public void vError(Module module, String title, Object[] content) {}

	@Override
	public void vError(Module module, Object content) {}

	@Override
	public void log(Module module, String title, Object[] content) {}

	@Override
	public void vLog(Module module, String title, Object[] content) {}

	@Override
	public void error(Module module, Object[] content) {}

	@Override
	public void vError(Module module, Object[] content) {}

	@Override
	public void setVerbose(boolean verbose) {}

}
