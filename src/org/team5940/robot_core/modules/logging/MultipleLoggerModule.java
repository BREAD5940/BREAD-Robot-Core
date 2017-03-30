package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;

/**
 * An implementation of {@link LoggerModule} that passes all logs to any number of other loggers.
 * @author David Boles
 *
 */
public class MultipleLoggerModule extends AbstractModule implements LoggerModule {
	
	/**
	 * Stores this' verbosity.
	 */
	private boolean verbose;
	
	/**
	 * Stores whether this is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Stores the loggers that this passes logs to.
	 */
	private final ModuleHashtable<LoggerModule> loggers;

	/**
	 * Initializes a new {@link MultipleLoggerModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param verbose Whether to initially log verbose logs.
	 * @param enabled Whether to initially be enabled and log.
	 * @param loggers The loggers to pass logs to.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public MultipleLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, ModuleHashtable<LoggerModule> loggers)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(loggers.values()), logger);
		this.logger.checkInitializationArgs(this, MultipleLoggerModule.class, new Object[]{verbose, enabled, loggers});
		this.verbose = verbose;
		this.enabled = enabled;
		this.loggers = loggers;
		this.logger.logInitialization(this, MultipleLoggerModule.class, new Object[]{verbose, enabled, loggers});
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
		if(this.enabled)
			for(LoggerModule logger : this.loggers.values())
				logger.log(module, log);

	}

	@Override
	public void log(Module module, String title, Object content) {
		if(this.enabled)
			for(LoggerModule logger : this.loggers.values())
				logger.log(module, title, content);

	}

	@Override
	public void vLog(Module module, String log) {
		if(this.enabled && this.verbose)
			for(LoggerModule logger : this.loggers.values())
				logger.vLog(module, log);

	}

	@Override
	public void vLog(Module module, String title, Object content) {
		if(this.enabled && this.verbose)
			for(LoggerModule logger : this.loggers.values())
				logger.vLog(module, title, content);

	}

	@Override
	public void error(Module module, String log) {
		if(this.enabled)
			for(LoggerModule logger : this.loggers.values())
				logger.error(module, log);

	}

	@Override
	public void error(Module module, String title, Object content) {
		if(this.enabled)
			for(LoggerModule logger : this.loggers.values())
				logger.error(module, title, content);
	}

	@Override
	public void vError(Module module, String log) {
		if(this.enabled && this.verbose)
			for(LoggerModule logger : this.loggers.values())
				logger.vError(module, log);
	}

	@Override
	public void vError(Module module, String title, Object content) {
		if(this.enabled && this.verbose)
			for(LoggerModule logger : this.loggers.values())
				logger.vError(module, title, content);
	}

}
