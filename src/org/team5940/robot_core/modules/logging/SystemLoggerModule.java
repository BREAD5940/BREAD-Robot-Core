package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;

public class SystemLoggerModule extends GenericLoggerModule {

	public SystemLoggerModule(String name, ModuleHashTable<Module> subModules, LoggerModule logger, boolean verbose)
			throws IllegalArgumentException {
		super(name, subModules, logger, verbose);
		this.logger.log(this, "Created SystemLoggerModule");
	}

	@Override
	protected void log(String log) {
		System.out.println(log);
	}

	@Override
	protected void error(String error) {
		System.err.println(error);
	}

}
