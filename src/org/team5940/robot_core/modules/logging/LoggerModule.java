package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;

/**
 * This interface is for a Module that logs.
 * @author Noah Sturgeon
 *
 */
public interface LoggerModule extends Module {

	/**
	 * Sets the verbosity of the LoggerModule.
	 * @param verbose True if you want verbose output, false otherwise.
	 */
	public void setVerbose(boolean verbose);
	
	//STANDARD LOGGING
	/**
	 * Logs the String log, marking that it comes from Module module.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void log(Module module,String log);
	
	/**
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,String title,Object content);
	
	/**
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,Object content);
	
	/**
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,String title,Object[] content);
	
	/**
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,Object[] content);
	
	//VERBOSE LOGGING
	/**
	 * (Version for verbose logging: really detailed logs that you don't usually need)
	 * Logs the String log, marking that it comes from Module module.
	 * @throws IllegalArgumentExecption if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void vLog(Module module,String log);
	
	/**
	 * (Version for verbose logging: really detailed logs that you don't usually need)
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @throws IllegalArgumentExecption if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vLog(Module module,String title,Object content);
	
	/**
	 * (Version for verbose logging: really detailed logs that you don't usually need)
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentExecption if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vLog(Module module,Object content);
	
	/**
	 * (Version for verbose logging: really detailed logs that you don't usually need)
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @throws IllegalArgumentExecption if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vLog(Module module,String title,Object[] content);
	
	/**
	 * (Version for verbose logging: really detailed logs that you don't usually need)
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentExecption if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vLog(Module module,Object[] content);
	
	//STANDARD ERRORING
	/**
	 * Logs the String log as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen. 
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void error(Module module,String log);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,String title,Object content);
	
	/**
	 * Logs the Object content as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,Object content);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,String title,Object[] content);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,Object[] content);
	
	//VERBOSE ERRORING
	/**
	 * Logs the String log as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen. 
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void vError(Module module,String log);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vError(Module module,String title,Object content);
	
	/**
	 * Logs the Object content as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vError(Module module,Object content);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vError(Module module,String title,Object[] content);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void vError(Module module,Object[] content);

}
