package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;

/**
 * An implementation of {@link LoggerModule} that simply passes logging calls to up to four other loggers.
 * @author Amit Harlev
 *
 */
public class AggregateLoggerModule extends AbstractModule implements LoggerModule {
	
	/**
	 * Stores this' verbosity.
	 */
	private boolean verbose;
	
	/**
	 * Stores whether this is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Stores the logger this uses for standard logs.
	 */
	private final LoggerModule standardLogger;
	
	/**
	 * Stores the logger this uses for verbose logs.
	 */
	private final LoggerModule verboseLogger;
	
	/**
	 * Stores the logger this uses for standard errors.
	 */
	private final LoggerModule standardErrorLogger;
	
	/**
	 * Stores the logger this uses for verbose errors.
	 */
	private final LoggerModule verboseErrorLogger;
	
	/**
	 * Initializes a new {@link AggregateLoggerModule}.
	 * @param name This' name.
	 * @param dependencies This' dependencies.
	 * @param logger This' logger.
	 * @param verbose Whether to initially be verbose.
	 * @param enabled Whether to initially be enabled.
	 * @param standardLogLogger The logger to use for standard logs.
	 * @param verboseLogLogger The logger to use for verbose logs.
	 * @param standardErrorLogger The logger to use for standard errors.
	 * @param verboseErrorLogger The logger to use for verbose errors.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public AggregateLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, LoggerModule standardLogLogger, LoggerModule verboseLogLogger, LoggerModule standardErrorLogger, LoggerModule verboseErrorLogger)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(new Module[]{standardLogLogger, verboseLogLogger, standardLogLogger, verboseErrorLogger}), logger);
		this.logger.checkInitializationArgs(this, AggregateLoggerModule.class, new Object[]{verbose, enabled, standardLogLogger, verboseLogLogger, standardLogLogger, verboseErrorLogger});
		this.verbose = verbose;
		this.enabled = enabled;
		this.standardLogger = standardLogLogger;
		this.verboseLogger = verboseLogLogger;
		this.standardErrorLogger = standardErrorLogger;
		this.verboseErrorLogger = verboseErrorLogger;
		this.logger.logInitialization(this, AggregateLoggerModule.class, new Object[]{verbose, enabled, standardLogLogger, verboseLogLogger, standardLogLogger, verboseErrorLogger});
	}
	
	/**
	 * Initializes a new {@link AggregateLoggerModule} with only logging and erroring loggers.
	 * @see AggregateLoggerModule#AggregateLoggerModule(String, LoggerModule, boolean, boolean, LoggerModule, LoggerModule, LoggerModule, LoggerModule)
	 */
	public AggregateLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, LoggerModule logLogger, LoggerModule errorLogger)
			throws IllegalArgumentException {
		this(name, logger, verbose, enabled, logLogger, logLogger, errorLogger, errorLogger);
	}
	
	/**
	 * Initializes a new {@link AggregateLoggerModule} with only logging and erroring loggers and an inert logger for its own logging.
	 * @see AggregateLoggerModule#AggregateLoggerModule(String, LoggerModule, boolean, boolean, LoggerModule, LoggerModule, LoggerModule, LoggerModule)
	 */
	public AggregateLoggerModule(String name, boolean verbose, boolean enabled, LoggerModule logLogger, LoggerModule errorLogger)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, verbose, enabled, logLogger, logLogger, errorLogger, errorLogger);
	}
	
	/**
	 * Initializes a new {@link AggregateLoggerModule} that's enabled and not verbose with only logging and erroring loggers and an inert logger for its own logging.
	 * @see AggregateLoggerModule#AggregateLoggerModule(String, LoggerModule, boolean, boolean, LoggerModule, LoggerModule, LoggerModule, LoggerModule)
	 */
	public AggregateLoggerModule(String name, LoggerModule logLogger, LoggerModule errorLogger)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, false, true, logLogger, logLogger, errorLogger, errorLogger);
	}
	

	@Override
	public boolean isVerbose() {
		this.logger.logGot(this, "Is Verbose", this.verbose);
		return this.verbose;
	}

	@Override
	public void setVerbose(boolean verbose) {
		this.logger.logSettingPrimitiveArgs(this, "Verbose", verbose);
		this.verbose = verbose;

	}

	@Override
	public boolean isEnabled() {
		this.logger.logGot(this, "Is Enabled", this.enabled);
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.logger.logSettingPrimitiveArgs(this, "Enabled", enabled);
		this.enabled = enabled;

	}

	@Override
	public void log(Module module, String log) {
		if(this.enabled) this.standardLogger.log(module, log);

	}

	@Override
	public void log(Module module, String title, Object content) {
		if(this.enabled) this.standardLogger.log(module, title, content);

	}

	@Override
	public void vLog(Module module, String log) {
		if(this.enabled && this.verbose) this.verboseLogger.vLog(module, log);

	}

	@Override
	public void vLog(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.verboseLogger.vLog(module, title, content);

	}

	@Override
	public void error(Module module, String log) {
		if(this.enabled) this.standardErrorLogger.error(module, log);

	}

	@Override
	public void error(Module module, String title, Object content) {
		if(this.enabled) this.standardErrorLogger.error(module, title, content);
	}

	@Override
	public void vError(Module module, String log) {
		if(this.enabled && this.verbose) this.verboseErrorLogger.vError(module, log);
	}

	@Override
	public void vError(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.verboseErrorLogger.vError(module, title, content);
	}

}
