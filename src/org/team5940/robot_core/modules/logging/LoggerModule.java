package org.team5940.robot_core.modules.logging;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * This interface is for a Module that logs.
 * @author Noah Sturgeon
 *
 */
public interface LoggerModule extends Module {

	//CONTROL
	/**
	 * Gets the verbosity of the LoggerModule.
	 * @return True if it is verbose, false otherwise.
	 * @see LoggerModule#setVerbose(boolean)
	 */
	public boolean isVerbose();
	
	/**
	 * Sets the verbosity of the LoggerModule.
	 * @param verbose True if you want verbose output, false otherwise.
	 * @see LoggerModule#isVerbose()
	 */
	public void setVerbose(boolean verbose);
	
	/**
	 * Gets whether or not the logger is enabled.
	 * @return True if you want this to output logs, false otherwise.
	 * @see LoggerModule#setEnabled(boolean)
	 */
	public boolean isEnabled();
	
	/**
	 * Sets whether or not the logger is enabled, this should simply silence the output. Useful for collecting information about a Module to log without risking recursion.
	 * @param enabled True if this should output logs, false otherwise.
	 * @see LoggerModule#isEnabled()
	 */
	public void setEnabled(boolean enabled);
	
	//STANDARD LOGGING
	/**
	 * Logs the String log, marking that it comes from Module module.
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param log The log. (Shouldn't but could be null)
	 * @see LoggerModule#log(Module, String, Object)
	 */
	public void log(Module module, String log);
	
	/**
	 * Logs the Object content with a title of String title, marking that it comes from Module module.
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param title The title of the log. (Shouldn't but could be null)
	 * @param content The content of the log. (Shouldn't but could be null)
	 * @see LoggerModule#log(Module, String)
	 */
	public void log(Module module, String title, Object content);
	
	//VERBOSE LOGGING
	/**
	 * Logs the String log, marking that it comes from Module module. (Verbose version, output of these logs can be silenced with {@link LoggerModule#setVerbose(boolean)})
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param log The log. (Shouldn't but could be null)
	 * @see LoggerModule#vLog(Module, String, Object)
	 */
	public void vLog(Module module, String log);
	
	/**
	 * Logs the Object content with a title of String title, marking that it comes from Module module. (Verbose version, output of these logs can be silenced with {@link LoggerModule#setVerbose(boolean)})
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param title The title of the log. (Shouldn't but could be null)
	 * @param content The content of the log. (Shouldn't but could be null)
	 * @see LoggerModule#vLog(Module, String)
	 */
	public void vLog(Module module, String title, Object content);
	
	//STANDARD ERRORING
	/**
	 * Logs the String log as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param log The log. (Shouldn't but could be null)
	 * @see LoggerModule#error(Module, String, Object)
	 */
	public void error(Module module, String log);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen.
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param title The title of the log. (Shouldn't but could be null)
	 * @param content The content of the log. (Shouldn't but could be null)
	 * @see LoggerModule#error(Module, String)
	 */
	public void error(Module module, String title, Object content);
	
	//VERBOSE ERRORING
	/**
	 * Logs the String log as an error, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen. (Verbose version, output of these logs can be silenced with {@link LoggerModule#setVerbose(boolean)})
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param log The log. (Shouldn't but could be null)
	 * @see LoggerModule#vError(Module, String, Object)
	 */
	public void vError(Module module, String log);
	
	/**
	 * Logs the Object content as an error, with the title as String title, marking that it comes from Module module. This is used to distinguish whether a logged event is not supposed to happen. (Verbose version, output of these logs can be silenced with {@link LoggerModule#setVerbose(boolean)})
	 * @param module The Module that is logging. (Shouldn't but could be null)
	 * @param title The title of the log. (Shouldn't but could be null)
	 * @param content The content of the log. (Shouldn't but could be null)
	 * @see LoggerModule#vError(Module, String)
	 */
	public void vError(Module module, String title, Object content);
	
	//INITIALIZATION LOGGING
	/**
	 * Used for when an implementation of {@link Module} is initialized. Should be placed at the end of the constructor.
	 * @param module The module that was initialized.
	 * @param constructorClass The class of the module that was initialized.
	 * @see LoggerModule#logInitialization(Module, Class, Object)
	 */
	public default void logInitialization(Module module, Class<? extends Module> constructorClass) {
		this.log(module, "Initialized " + constructorClass.getSimpleName());
	}
	
	/**
	 * Used for when an implementation of {@link Module} is initialized. Should be placed at the end of the constructor.
	 * @param module The module that was initialized.
	 * @param constructorClass The class of the module that was initialized.
	 * @param args The arguments that the module was initialized with.
	 * @see LoggerModule#logInitialization(Module, Class)
	 */
	public default void logInitialization(Module module, Class<? extends Module> constructorClass, Object args) {
		this.log(module, "Initialized " + constructorClass.getSimpleName(), args);
	}
	
	//INITIALIZATION CHECKING
	/**
	 * Used to check whether an implementation of {@link Module} is being initialized with a null argument. If so, logs an error and throws an exception for you. Should be placed at the beginning of the constructor (but after super constructor calls if applicable).
	 * @param module The module that is initializing.
	 * @param constructorClass The class of the module that is initializing.
	 * @param arg The argument that the module is initializing with. 
	 * @throws IllegalArgumentException Thrown if arg is null.
	 * @see LoggerModule#checkInitializationArgs(Module, Class, Object[])
	 */
	public default void checkInitializationArg(Module module, Class<? extends Module> constructorClass, Object arg) throws IllegalArgumentException {
		if(arg == null) {
			this.error(module, constructorClass.getSimpleName() + " Initialized With Null", arg);
			throw new IllegalArgumentException("Null argument!");
		}
	}
	
	/**
	 * Used to check whether an implementation of {@link Module} is being initialized with null arguments. If so, logs an error with them and throws an exception for you. Should be placed at the beginning of the constructor (but after super constructor calls if applicable).
	 * @param module The module that is initializing.
	 * @param constructorClass The class of the module that is initializing.
	 * @param args The arguments that the module is initializing with. 
	 * @throws IllegalArgumentException Thrown if any object in args is null.
	 * @see LoggerModule#checkInitializationArg(Module, Class, Object)
	 */
	public default void checkInitializationArgs(Module module, Class<? extends Module> constructorClass, Object[] args) throws IllegalArgumentException {
		for(Object o : args)
			if(o == null) {
				this.error(module, constructorClass.getSimpleName() + " Initialized With Null", args);
				throw new IllegalArgumentException("Null argument!");
			}
	}

	/**
	 * Used to log about and throw an {@link IllegalArgumentException} for an illegal {@link Module} initialization.
	 * @param module The module that was initializing.
	 * @param constructorClass The class of the module that was initializing.
	 * @param reason The reason initialization failed.
	 * @param content Any relevant content.
	 * @throws IllegalArgumentException Thrown with reason as its message.
	 */
	public default void failInitializationIllegal(Module module, Class<? extends Module> constructorClass, String reason, Object content) throws IllegalArgumentException {
		this.error(module, constructorClass.getSimpleName() + " Initialized With " + reason, content);
		throw new IllegalArgumentException(reason);
	}
	
	
	//GOT LOGGING
	/**
	 * Used to log a call to a get or is method in an implementation of {@link Module}. Should be placed directly before the return.
	 * @param module The module that is logging.
	 * @param target The name of the thing that was gotten (e.g. "Current Gear").
	 * @param content The value that was gotten.
	 */
	public default void logGot(Module module, String target, Object content) {
		this.vLog(module, "Got " + target, content);
	}
	
	//SETTING CHECKING AND LOGGING
	/**
	 * Used to log about a call to a set method that only has (a) primitive argument(s) in an implementation of {@link Module}. Should be placed at the beginning of the method.
	 * @param module The module that is logging.
	 * @param target The name of the thing that is being set (e.g. "Current Gear").
	 * @param arg The primitive argument(s) of the set method.
	 * @see LoggerModule#checkAndLogSettingArg(Module, String, Object)
	 * @see LoggerModule#checkAndLogSettingArgs(Module, String, Object[])
	 */
	public default void logSettingPrimitiveArgs(Module module, String target, Object arg) {
		this.vLog(module, "Setting " + target, arg);
	}
	
	/**
	 * Used to check the argument of and log about a call to a set method in an implementation of {@link Module}. Should be placed at the beginning of the method.
	 * @param module The module that is logging.
	 * @param target The name of the thing that is being set (e.g. "Current Gear").
	 * @param arg The argument of the set method.
	 * @throws IllegalArgumentException Thrown if arg is null.
	 * @see LoggerModule#logSettingPrimitiveArgs(Module, String, Object)
	 * @see LoggerModule#checkAndLogSettingArgs(Module, String, Object[])
	 */
	public default void checkAndLogSettingArg(Module module, String target, Object arg) throws IllegalArgumentException {
		if(arg == null) {
			this.vError(module, "Setting " + target + " With Null", arg);
			throw new IllegalArgumentException("Null argument!");
		}
		
		this.vLog(module, "Setting " + target, arg);
	}
	
	/**
	 * Used to check the arguments of and log about a call to a set method in an implementation of {@link Module}. Should be placed at the beginning of the method.
	 * @param module The module that is logging.
	 * @param target The name of the thing that is being set (e.g. "Current Gear").
	 * @param args The arguments of the set method.
	 * @throws IllegalArgumentException Thrown if any object in args is null.
	 * @see LoggerModule#logSettingPrimitiveArgs(Module, String, Object)
	 * @see LoggerModule#checkAndLogSettingArg(Module, String, Object)
	 */
	public default void checkAndLogSettingArgs(Module module, String target, Object[] args) throws IllegalArgumentException {
		for(Object o : args)
			if(o == null) {
				this.vError(module, "Setting " + target + " With Null", args);
				throw new IllegalArgumentException("Null argument!");
			}
		
		this.vLog(module, "Setting " + target, args);
	}
	
	/**
	 * Used to log about and throw an {@link IllegalArgumentException} for an illegal call to a set method.
	 * @param module The module that is erroring.
	 * @param target The name of the thing that was being set (e.g. "Current Gear").
	 * @param reason The reason setting failed.
	 * @param content Any relevant content.
	 * @throws IllegalArgumentException Thrown with reason as its message.
	 */
	public default void failSettingIllegal(Module module, String target, String reason, Object content) throws IllegalArgumentException {
		this.vError(module, "Setting " + target + " With " + reason, content);
		throw new IllegalArgumentException(reason);
	}
	
	//TESTING
	/**
	 * Used to log the start of a test in an implementation {@link TestableModule}.
	 * @param module The module that is testing.
	 * @param comms The {@link TestCommunicationModule} that the test is being run with.
	 * @throws IllegalArgumentException Thrown if comms is null.
	 */
	public default void checkAndLogTestStart(Module module, TestCommunicationModule comms) throws IllegalArgumentException {
		if(comms == null) {
			this.error(this, "Running Test With Null");
			throw new IllegalArgumentException("Test communication null!");
		}
		this.log(this, "Starting Test", comms);
	}
	
	/**
	 * Used to log the completion of a test that passed in an implementation {@link TestableModule}.
	 * @param module The module that was testing.
	 */
	public default void logTestPassed(Module module) {
		this.log(module, "Test Passed");
	}
	
	/**
	 * Used to log the completion of a test that failed in an implementation {@link TestableModule}.
	 * @param module The module that was testing.
	 * @param cause The reason the test failed.
	 * @see LoggerModule#logTestFailed(Module, String, Object)
	 */
	public default void logTestFailed(Module module, String cause) {
		this.log(module, "Test Failed: " + cause);
	}
	
	/**
	 * Used to log the completion of a test that failed in an implementation {@link TestableModule}.
	 * @param module The module that was testing.
	 * @param cause The reason the test failed.
	 * @param content Any content related to why the test failed.
	 * @see LoggerModule#logTestFailed(Module, String)
	 */
	public default void logTestFailed(Module module, String cause, Object content) {
		this.log(module, "Test Failed: " + cause, content);
	}
	
	/**
	 * Used to log the completion of a test that failed in an implementation {@link TestableModule}.
	 * @param module The module that was testing.
	 * @param content Whatever caused the error, usually an exception.
	 */
	public default void logTestErrored(Module module, Object content) {
		this.log(module, "Test Errored", content);
	}
	
	//INERT LOGGER
	/**
	 * This logger, named "inert_logger", should be used whenever you want a logger that doesn't do anything. It also serves as the basis of the logging framework, otherwise you wouldn't be able to initialize loggers that log internally.
	 */
	public static final LoggerModule INERT_LOGGER = new LoggerModule() {
		
		@Override
		public String getModuleName() {
			return "inert_logger";
		}
		
		@Override
		public LoggerModule getModuleLogger() {
			return this;
		}
		
		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>();
		}
		
		@Override
		public void vLog(Module module, String title, Object content) {}
		
		@Override
		public void vLog(Module module, String log) {}
		
		@Override
		public void vError(Module module, String title, Object content) {}
		
		@Override
		public void vError(Module module, String log) {}
		
		@Override
		public void setVerbose(boolean verbose) {}
		
		@Override
		public void setEnabled(boolean enabled) {}
		
		@Override
		public void log(Module module, String title, Object content) {}
		
		@Override
		public void log(Module module, String log) {}
		
		@Override
		public boolean isVerbose() {
			return false;
		}
		
		@Override
		public boolean isEnabled() {
			return false;
		}
		
		@Override
		public void error(Module module, String title, Object content) {}
		
		@Override
		public void error(Module module, String log) {}
	};
}
