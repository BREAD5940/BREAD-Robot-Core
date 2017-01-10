package org.team5940.robot_core.modules.interfaces;

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
}
