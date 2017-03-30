package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

/**
 * An implementation of {@link LoggerModule} that passes logs to a logger determined by a {@link SelectorModule}.
 * @author David Boles
 *
 */
public class SelectableLoggerModule extends AbstractModule implements LoggerModule {
	
	/**
	 * Stores this' verbosity.
	 */
	private boolean verbose;
	
	/**
	 * Stores whether this is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Stores this' selector.
	 */
	private final SelectorModule selector;
	
	/**
	 * Stores the procedure for selector state -1.
	 */
	private final LoggerModule unselectedLogger;
	
	/**
	 * Stores the procedures for selector states 0 through (n-1).
	 */
	private final LoggerModule[] loggers;
	
	/**
	 * Initializes a new {@link SelectableLoggerModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector The selector that determines what logger to use.
	 * @param unselectedLogger The logger to use when nothing is selected.
	 * @param loggers The logger to use depending on the selected state.
	 * @throws IllegalArgumentException Thrown if any argument is null, loggers contains a null logger, or the number of selector states does not equal the number of loggers.
	 */
	public SelectableLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, SelectorModule selector, LoggerModule unselectedLogger, LoggerModule[] loggers)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(loggers).chainPut(unselectedLogger).chainPut(selector), logger);
		this.logger.checkInitializationArgs(this, SelectableLoggerModule.class, new Object[]{verbose, enabled, selector, unselectedLogger, loggers});
		if(loggers.length != selector.getNumberOfStates())
			this.logger.failInitializationIllegal(this, SelectableLoggerModule.class, "Unequal Quantities", new Object[]{verbose, enabled, selector.getNumberOfStates(), loggers.length});
		for(LoggerModule loggerTest : loggers)
			if(loggerTest == null)
				this.logger.failInitializationIllegal(this, SelectableLoggerModule.class, "Null Procedure", new Object[]{verbose, enabled, selector.getNumberOfStates(), loggers.length});
		this.verbose = verbose;
		this.enabled = enabled;
		this.selector = selector;
		this.unselectedLogger = unselectedLogger;
		this.loggers = loggers;
		this.logger.logInitialization(this, SelectableLoggerModule.class, new Object[]{verbose, enabled, selector, unselectedLogger, loggers});
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
		if(this.enabled) this.getSelectedLogger().log(module, log);

	}

	@Override
	public void log(Module module, String title, Object content) {
		if(this.enabled) this.getSelectedLogger().log(module, title, content);

	}

	@Override
	public void vLog(Module module, String log) {
		if(this.enabled && this.verbose) this.getSelectedLogger().vLog(module, log);

	}

	@Override
	public void vLog(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.getSelectedLogger().vLog(module, title, content);

	}

	@Override
	public void error(Module module, String log) {
		if(this.enabled) this.getSelectedLogger().error(module, log);

	}

	@Override
	public void error(Module module, String title, Object content) {
		if(this.enabled) this.getSelectedLogger().error(module, title, content);
	}

	@Override
	public void vError(Module module, String log) {
		if(this.enabled && this.verbose) this.getSelectedLogger().vError(module, log);
	}

	@Override
	public void vError(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.getSelectedLogger().vError(module, title, content);
	}

	/**
	 * Gets the currently selected logger.
	 * @return The currently selected logger.
	 */
	private LoggerModule getSelectedLogger() {
		LoggerModule out = this.unselectedLogger;
		int selected = this.selector.getCurrentState();
		if(selected != -1) out = this.loggers[selected];
		return out;
	}
}
