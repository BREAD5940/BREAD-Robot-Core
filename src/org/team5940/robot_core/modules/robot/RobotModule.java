package org.team5940.robot_core.modules.robot;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.logging.LoggerModule;

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
	protected LoggerModule logger;
	
	@Override
	public String getName() {
		return "robot";
	}

	@Override
	public ModuleHashTable<? extends Module> getSubModules() {
		ModuleHashTable<Module> subModules = new ModuleHashTable<Module>();
		subModules.putAll(this.subModules);
		return subModules;
	}
	
	/**
	 * Adds a submodule to RobotModule.
	 * @param subModule the subModule to add.
	 */
	protected void addSubModule(Module subModule) {
		subModules.put(subModule);
	}
	
	
	@Override
	public void setLogger(LoggerModule logger) throws IllegalArgumentException {
		if (logger == null) throw new IllegalArgumentException("Logger is null!");
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
		createSubmodules();
		this.initialize();
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
		for (Module subModule : subModules.getAllSubModules().values()) {
			subModule.shutDown();
		}
		this.shutDown();
	};
}
