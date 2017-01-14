package org.team5940.robot_core.modules;

/**
 * A module is the basic building block of robot code in BREAD's Robot Core. Anything that implements this interface should represent some part of the robot, whether a sensor, actuator, piece of code or even more complicated systems (e.g. the drivetrain). Actual functionality for a module should be given by interfaces that extend this interface.
 * @author David Boles
 *
 */
public interface Module {
	/**
	 * This is the name for the instance of the Module implementing this interface. Multiple instances of implementations of Module should never have the same name. Module names conventionally are only made of alphanumeric characters and underscores to separate groups of those (e.g. left_shifter).
	 * @return The name of the instance of this Module.
	 */
	public String getName();
	
	/**
	 * This should return a NEW ModuleList containing all of the direct submodules of this, named by the return of their .getName()s.
	 * @return A NEW ModuleList containing submodules, shouldn't be null even if there aren't any.
	 */
	public ModuleHashTable<? extends Module> getSubModules();
	
	/**
	 * Initializes the module if it is not already initialized.  Should generally be called after all modules have been created, but before procedures start executing.
	 */
	public void initialize ();
	
	/**
	 * Shuts down the module if it is not already shut down.  Should generally be called after procedures have finished executing.
	 */
	public void shutDown ();
	
}
