package org.team5940.robot_core.modules;


import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * A module is the basic building block of robot code in BREAD's Robot Core. Anything that implements this interface should represent some part of the robot, whether a sensor, actuator, piece of code or aggregations of those (e.g. the drivetrain). Actual functionality for a module should be given by interfaces that extend this interface.
 * @author David Boles
 *
 */
public interface Module {
	/**
	 * This is the name for the instance of the Module implementing this interface, it should never change for a given instance. Multiple modules should never have the same name however there is no enforcement mechanism. Module names conventionally are only made of alphanumeric characters and underscores to separate groups of those (e.g. left_shifter).
	 * @return this' name, should never be null.
	 */
	public String getModuleName();
	
	/**
	 * A module's dependencies are any other modules it directly relies on to do its function. This does not include any dependencies this' dependencies might have.
	 * @return A NEW ModuleList containing any modules this is dependent on. Empty if no dependencies, should never be null.
	 */
	public ModuleHashtable<Module> getModuleDependencies();
	
	/**
	 * Returns this' dependencies, their dependencies, and so on. Implementations should beware of infinite recursion!
	 * @return A NEW ModuleList containing any extended dependencies. Empty if no dependencies, should never be null.
	 */
	public default ModuleHashtable<Module> getExtendedModuleDependencies() {
		ModuleHashtable<Module> out = new ModuleHashtable<Module>(this.getModuleDependencies().values());
		for(Module dependency : this.getModuleDependencies().values())
			for(Module subDependency : dependency.getExtendedModuleDependencies().values())
				if(!out.containsKey(subDependency.getModuleName())) out.put(subDependency);
		this.getModuleLogger().vLog(this, "Extended Dependencies Gotten", out);
		return out;
	}
	
	/**
	 * DO NOT LOG IN IMPLEMENTATIONS OF THIS METHOD! Gets the current LoggerModule this is using to log. Necessary for silencing this' logger while collecting information for a log (otherwise it's possible to get circular logging) and accessing the logger in default implementations inside interfaces.
	 * @return The LoggerModule that the module is using, should never be null.
	 */
	public LoggerModule getModuleLogger();
}
