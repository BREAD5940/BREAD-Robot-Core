package org.team5940.robot_core.modules.actuators.motor_sets;

import org.team5940.robot_core.modules.logging.LoggerModule;

import com.ctre.CANTalon;

/**
 * A {@link CANTalonMotorSetModule} that includes a simple test to run it forwards and backwards at 0.15.
 * @author David Boles
 *
 */
public class TestableCANTalonMotorSetModule extends CANTalonMotorSetModule implements TestableMotorSetModule {
	
	/**
	 * Initializes a new TestableCANTalonMotorSetModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param talons The CANTalons to control.
	 * @throws IllegalArgumentException Thrown if any argument is null or talons contains a null.
	 */
	public TestableCANTalonMotorSetModule(String name, LoggerModule logger, CANTalon[] talons)
			throws IllegalArgumentException {
		super(name, logger, talons);
		this.logger.logInitialization(this, TestableCANTalonMotorSetModule.class);
	}

	/**
	 * Initializes a new TestableCANTalonMotorSetModule with only one talon.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param talon The CANTalon to control.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 * @see TestableCANTalonMotorSetModule#TestableCANTalonMotorSetModule(String, LoggerModule, CANTalon[])
	 */
	public TestableCANTalonMotorSetModule(String name, LoggerModule logger, CANTalon talon)
			throws IllegalArgumentException {
		super(name, logger, talon);
		this.logger.logInitialization(this, TestableCANTalonMotorSetModule.class);
	}

}
