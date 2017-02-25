package org.team5940.robot_core.modules.logging;

import java.util.Collection;
import java.util.Map;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;


/**
 * This is a abstract, reference implementation of LoggerModule that constructs the Strings to log for you.
 * @author David Boles
 *
 */
public abstract class AbstractLoggerModule extends AbstractModule implements LoggerModule {

	/**
	 * Stores this' verbosity.
	 */
	private boolean verbose;
	
	/**
	 * Stores whether this is enabled.
	 */
	private boolean enabled;
	
	/**
	 * Initializes a new AbstractLoggerModule.
	 * @param name This' name.
	 * @param subModules This' submodules.
	 * @param logger This' logger.
	 * @param verbose Whether to initially be verbose or not.
	 * @param enabled Whether to initially be enabled or not.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public AbstractLoggerModule(String name, ModuleHashtable<Module> subModules, LoggerModule logger, boolean verbose, boolean enabled) throws IllegalArgumentException {
		super(name, subModules, logger);
		this.verbose = verbose;
		this.enabled = enabled;
		this.logger.logInitialization(this, AbstractLoggerModule.class, verbose);
	}
	
	@Override
	public synchronized void setVerbose(boolean verbose) {
		this.logger.logSettingPrimitiveArgs(this, "Verbose", verbose);
		this.verbose = verbose;
	}
	
	@Override
	public synchronized boolean isVerbose() {
		this.logger.logGot(this, "Is Verbose", this.verbose);
		return this.verbose;
	}
	
	@Override
	public synchronized void setEnabled(boolean enabled) {
		this.logger.logSettingPrimitiveArgs(this, "Enabled", enabled);
		this.enabled = enabled;
	}
	
	@Override
	public synchronized boolean isEnabled() {
		this.logger.logGot(this, "Is Enabled", this.enabled);
		return this.enabled;
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
	 * Constructs the string to log for an object.
	 * @param o The object that will be logged.
	 * @return The string that represents o.
	 */
	private String getStamp(Object o) {
		String out = "<";
		
		if(o==null) {
			out += "null";
			
		}else if(o instanceof Module) {
			Module c = (Module) o;
			LoggerModule l = c.getModuleLogger();
			boolean e = l.isEnabled();
			l.setEnabled(false);
			out += c.getModuleName();
			if(!c.getClass().isAnonymousClass()) out += ": " + c.getClass().getSimpleName();
			l.setEnabled(e);
			
		}else if(o instanceof ModuleHashtable) {
			for(Module i : ((ModuleHashtable<?>) o).values()) {
				out += this.getStamp(i);
			}
			
		}else if(o.getClass().isArray()) {
			for(Object i : (Object[]) o) {
				out += this.getStamp(i);
			}
			
		}else if(o instanceof Collection) {
			for(Object i : (Collection<?>) o) {
				out += this.getStamp(i);
			}
			
		}else if(o instanceof Map) {
			for(Object i : ((Map<?, ?>)o).keySet()) {
				out += this.getStamp(i) + ": " + this.getStamp(((Map<?, ?>)o).get(i));
			}
			
		}else if(o instanceof Throwable) {
			Throwable t = (Throwable) o;
			out += t.getClass().getSimpleName();
			if(t.getMessage() != null && !t.getMessage().equals("")) out += " - " + t.getMessage();
			out += ": " + getStamp(t.getStackTrace());
			
		}else out += o.toString();
		
		out += ">";
		this.logger.vLog(this, "Constructed Stamp", out);
		return out;
	}

	@Override
	public synchronized void log(Module module, String log) {
		if(this.enabled) this.log(this.getTimestamp() + this.getStamp(module) + this.getStamp(log));
		
	}

	@Override
	public synchronized void log(Module module, String title, Object content) {
		if(this.enabled) this.log(this.getTimestamp() + this.getStamp(module) + this.getStamp(title) + this.getStamp(content));
		
	}

	@Override
	public synchronized void vLog(Module module, String log) {
		if(this.enabled && this.verbose) this.log(this.getTimestamp() + this.getStamp(module) + this.getStamp(log));
		
	}

	@Override
	public synchronized void vLog(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.log(this.getTimestamp() + this.getStamp(module) + this.getStamp(title) + this.getStamp(content));
		
	}

	@Override
	public synchronized void error(Module module, String log) {
		if(this.enabled) this.error(this.getTimestamp() + this.getStamp(module) + this.getStamp(log));
		
	}

	@Override
	public synchronized void error(Module module, String title, Object content) {
		if(this.enabled) this.error(this.getTimestamp() + this.getStamp(module) + this.getStamp(title) + this.getStamp(content));
		
	}

	@Override
	public synchronized void vError(Module module, String log) {
		if(this.enabled && this.verbose) this.error(this.getTimestamp() + this.getStamp(module) + this.getStamp(log));
		
	}

	@Override
	public synchronized void vError(Module module, String title, Object content) {
		if(this.enabled && this.verbose) this.error(this.getTimestamp() + this.getStamp(module) + this.getStamp(title) + this.getStamp(content));
		
	}

}
