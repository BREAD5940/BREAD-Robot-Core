package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.ModuleHashtable;

/**
 * This is a simple logger that uses whatever the System's out and err are set to. They should show up in Riolog.
 * @author David Boles
 *
 */
public class SystemLoggerModule extends AbstractLoggerModule {
	
	/**
	 * Initializes a new SystemLoggerModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param verbose Whether to initially be verbose or not.
	 * @param enabled Whether to initially be enabled or not.
	 * @throws IllegalArgumentException Thrown if any argument is null
	 */
	public SystemLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger, verbose, enabled);
		this.logger.logInitialization(this, SystemLoggerModule.class, new Object[]{verbose, enabled});
	}
	
	/**
	 * Initializes a new SystemLoggerModule with an inert logger.
	 * @see SystemLoggerModule#SystemLoggerModule(String, LoggerModule, boolean, boolean)
	 */
	public SystemLoggerModule(String name, boolean verbose, boolean enabled)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, verbose, enabled);
	}
	
	/**
	 * Initializes a new SystemLoggerModule that's enabled and not verbose with an inert logger.
	 * @see SystemLoggerModule#SystemLoggerModule(String, LoggerModule, boolean, boolean)
	 */
	public SystemLoggerModule(String name)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, false, true);
	}

	@Override
	protected synchronized void log(String log) {
		System.out.println(log);
	}

	@Override
	protected synchronized void error(String error) {
		System.err.println(error);
	}

}
