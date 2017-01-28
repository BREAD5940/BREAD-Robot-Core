package org.team5940.robot_core.modules.robot;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.logging.NullLoggerModule;

import edu.wpi.first.wpilibj.SampleRobot;
/**
 * This is what gets run when the robot state changes.
 * @author Michael Bentley
 *
 */
public abstract class RobotModule extends SampleRobot implements Module {
	
	/**
	 * Stores the submodules of this Module.
	 */
	ModuleHashTable<Module> subModules = new ModuleHashTable<Module>();
	
	/**
	 * Stores the logger for this module.
	 */
	protected LoggerModule logger = new NullLoggerModule();
	
	@Override
	public String getName() {
		return "robot";
	}

	@Override
	public ModuleHashTable<? extends Module> getSubModules() {
		ModuleHashTable<Module> subModules = new ModuleHashTable<Module>();
		subModules.putAll(this.subModules);
		this.logger.vLog(this, "SubModules Accessed", subModules);
		return subModules;
	}
	
	/**
	 * Adds a submodule to RobotModule.
	 * @param subModule the subModule to add.
	 * @throws IllegalArgumentException If submodule null.
	 */
	protected void addSubModule(Module subModule) throws IllegalArgumentException{
		if(subModule == null) {
			this.logger.vError(this, "Adding Submodule With Null");
		}
		this.logger.log(this, "Adding SubModule", subModule);
		subModules.put(subModule);
	}
	
	
	@Override
	public void setLogger(LoggerModule logger) throws IllegalArgumentException {
		if (logger == null) {
			this.logger.vError(this, "Setting Logger With Null");
			throw new IllegalArgumentException("Logger is null!");
		}
		logger.vLog(this, "Logger Switched To", new Object[]{this.logger, logger});
		this.logger = logger;
	}
	
	/**
	 * Runs initialize() for RobotModule and all of its subModules when the robot is started.  
	 * Also initializes the subModules. 
	 * 
	 * Super Docs:
	 * {@inheritDoc}
	 */
	@Override
	protected void robotInit() {
		this.logger.log(this, "Creating SubModules");
		createSubmodules();
		this.logger.log(this, "Initializing");
		this.initialize();
		this.logger.log(this, "Initializing SubModules");
		for (Module subModule : subModules.getAllSubModules().values()) {
			subModule.initialize();
		}
	}
	
	/**
	 * Creates all of the submodules in the subModule ModuleHashTable
	 */
	protected abstract void createSubmodules();

	/**
	 * Runs shutDown() for RobotModule and all of its subModules when the robot is disabled.
	 * 
	 * Super Docs:
	 * {@inheritDoc}
	 */
	@Override
	protected void disabled() {
		this.logger.log(this, "Shutting Down SubModules");
		for (Module subModule : subModules.getAllSubModules().values()) {
			subModule.shutDown();
		}
		this.logger.log(this, "Shutting Down");
		this.shutDown();
	};
}
