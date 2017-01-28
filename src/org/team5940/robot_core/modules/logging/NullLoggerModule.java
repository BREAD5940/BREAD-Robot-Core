package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;

/**
 * This LoggerModule does nothing. You have to have one of these at the base of your robot logging framework for loggers to be able to log.
 * @author David Boles
 *
 */
public class NullLoggerModule implements LoggerModule {
	
	@Override
	public String getName() {
		return "null_logger";
	}
	

	@Override
	public ModuleHashTable<? extends Module> getSubModules() {
		return new ModuleHashTable<Module>();
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
