package org.team5940.robot_core.modules.actuators.motor_sets;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

/**
 * Interface to set and get the "speed" for a set of motors.
 * @author Noah Sturgeon
 *
 */
public interface MotorSetModule extends OwnableModule {
	/**
	 * Sets the "speed" of the motors.
	 * @param speed The "speed" to set the motors to, between -1 and 1 inclusive.
	 * @throws IllegalArgumentException If speed is less than -1 or more than 1.
	 * @throws ThreadUnauthorizedException If this is not accessible to the current thread.
	 */
	public void setMotorSpeed(double speed) throws IllegalArgumentException, ThreadUnauthorizedException;
	
	/**
	 * Gets the last "speed" that the motors were set to.
	 * @return The last "speed" the motors were set to, between -1 and 1 inclusive.
	 */
	public double getSetMotorSpeed();
	
	/**
	 * A motor set that does nothing named "inert_motor_set". Always owned and set speed always 0.
	 */
	public static final MotorSetModule INERT_MOTOR_SET = new MotorSetModule() {
		
		@Override
		public String getModuleName() {
			return "inert_motor_set";
		}
		
		@Override
		public LoggerModule getModuleLogger() {
			return LoggerModule.INERT_LOGGER;
		}
		
		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>();
		}
		
		@Override
		public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException { }
		
		@Override
		public boolean isOwnedBy(Thread t) {
			return true;
		}
		
		@Override
		public boolean acquireOwnershipFor(Thread t, boolean force) {
			return true;
		}
		
		@Override
		public void setMotorSpeed(double speed) throws IllegalArgumentException, ThreadUnauthorizedException { }
		
		@Override
		public double getSetMotorSpeed() {
			return 0;
		}
	};
}
