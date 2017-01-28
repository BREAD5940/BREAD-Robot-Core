package org.team5940.robot_core.modules.logging;

import java.util.Arrays;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;

/**
 * Extensible LoggerModule that constructs the strings to log for you.
 * @author David Boles
 *
 */
public abstract class GenericLoggerModule extends SimpleModule implements LoggerModule {

	private boolean verbose;
	
	public GenericLoggerModule(String name, ModuleHashTable<Module> subModules, LoggerModule logger, boolean verbose) throws IllegalArgumentException {
		super(name, subModules, logger);
		this.verbose = verbose;
		this.logger.log(this, "Created GenericLoggerModule", verbose);
	}
	
	@Override
	public void setVerbose(boolean verbose) {
		this.logger.vLog(this, "Setting Verbosity", verbose);
		this.verbose = verbose;
	}
	
	@Override
	public void initialize() {
		this.logger.log(this, "Initializing");
	}

	@Override
	public void shutDown() {
		this.logger.log(this, "Shutting Down");
	}
	
	/**
	 * Logs the Strings constructed by this.
	 * @param log The String to log.
	 */
	protected abstract void log(String log);
	
	/**
	 * Errors the Strings constructed by this.
	 * @param error The String to error.
	 */
	protected abstract void error(String error);
	
	/**
	 * Gets the current timestamp.
	 * @return The current timestamp.
	 */
	private String getTimestamp() {
		return "<" + System.currentTimeMillis() + ">";
	}
	
	/**
	 * Gets the module's stamp.
	 * @param module The module whose stamp you want.
	 * @return The module's stamp.
	 */
	private String getModuleStamp(Module module) {
		return "<" + module.getClass().getSimpleName() + ": " + module.getName() + ">";
	}
	
	/**
	 * Gets the object's stamp.
	 * @param object The object whose stamp you want.
	 * @return The object's stamp.
	 */
	private String getObjectStamp(Object object) {
		return "<" + object.toString() + ">";
	}
	
	/**
	 * Gets the array's stamp.
	 * @param array The array whose stamp you want.
	 * @return The array's stamp.
	 */
	private String getArrayStamp(Object[] array) {
		return "<" + Arrays.deepToString(array) + ">";
	}

	@Override
	public void log(Module module, String log) {
		this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(log));
		
	}

	@Override
	public void log(Module module, String title, Object content) {
		this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getObjectStamp(content));
		
	}

	@Override
	public void log(Module module, Object content) {
		this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(content));
		
	}

	@Override
	public void log(Module module, String title, Object[] content) {
		this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getArrayStamp(content));
		
	}

	@Override
	public void log(Module module, Object[] content) {
		this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getArrayStamp(content));
		
	}

	@Override
	public void vLog(Module module, String log) {
		if(this.verbose) this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(log));
		
	}

	@Override
	public void vLog(Module module, String title, Object content) {
		if(this.verbose) this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getObjectStamp(content));
		
	}

	@Override
	public void vLog(Module module, Object content) {
		if(this.verbose) this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(content));
		
	}

	@Override
	public void vLog(Module module, String title, Object[] content) {
		if(this.verbose) this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getArrayStamp(content));
		
	}

	@Override
	public void vLog(Module module, Object[] content) {
		if(this.verbose) this.log(this.getTimestamp() + this.getModuleStamp(module) + this.getArrayStamp(content));
		
	}

	@Override
	public void error(Module module, String log) {
		this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(log));
		
	}

	@Override
	public void error(Module module, String title, Object content) {
		this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getObjectStamp(content));
		
	}

	@Override
	public void error(Module module, Object content) {
		this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(content));
		
	}

	@Override
	public void error(Module module, String title, Object[] content) {
		this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getArrayStamp(content));
		
	}

	@Override
	public void error(Module module, Object[] content) {
		this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getArrayStamp(content));
		
	}

	@Override
	public void vError(Module module, String log) {
		if(this.verbose) this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(log));
		
	}

	@Override
	public void vError(Module module, String title, Object content) {
		if(this.verbose) this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getObjectStamp(content));
		
	}

	@Override
	public void vError(Module module, Object content) {
		if(this.verbose) this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(content));
		
	}

	@Override
	public void vError(Module module, String title, Object[] content) {
		if(this.verbose) this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getObjectStamp(title) + this.getArrayStamp(content));
		
	}

	@Override
	public void vError(Module module, Object[] content) {
		if(this.verbose) this.error(this.getTimestamp() + this.getModuleStamp(module) + this.getArrayStamp(content));
		
	}

}
