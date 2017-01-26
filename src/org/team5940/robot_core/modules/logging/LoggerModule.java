package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;

/**
 * This interface is for a Module that logs.
 * @author Noah Sturgeon
 *
 */
public interface LoggerModule extends Module {

	/**
	 * Logs the String log, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void log(Module module,String log) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,String title,Object content) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,Object content) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content, marking that it comes from Module module.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void log(Module module,Object[] content) throws IllegalArgumentException;
	
	/**
	 * Logs the String log as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen. 
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param log The log.
	 */
	
	public void error(Module module,String log) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,String title,Object content) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param title The title of the log.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,String title,Object[] content) throws IllegalArgumentException;
	
	/**
	 * Logs the Object content as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @throws IllegalArgumentException if any of the inputs == null.
	 * @param module The Module that is logging.
	 * @param content The content of the log. Will always be an object.
	 */
	
	public void error(Module module,Object content) throws IllegalArgumentException;

}
