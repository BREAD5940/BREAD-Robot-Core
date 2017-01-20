package org.team5940.robot_core.modules.robot;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;

import edu.wpi.first.wpilibj.SampleRobot;


public abstract class RobotModule extends SampleRobot implements Module {
	
	/**
	 * Stores the submodules of this Module.
	 */
	ModuleHashTable<Module> subModules = new ModuleHashTable<Module>();
	
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
}
