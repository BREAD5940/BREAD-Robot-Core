package org.team5940.robot_core.modules.actuators.motor_sets;

import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

import com.ctre.CANTalon;

/**
 * An implementation of {@link MotorSetModule} that uses CANTalons. Any configuration or enabling, etc... of the TalonSRXs should be handled before initialization of this.
 * @author David Boles
 * @see MotorSetModule
 *
 */
public class CANTalonMotorSetModule extends AbstractOwnableModule implements MotorSetModule {

	/**
	 * Stores the talons this uses.
	 */
	private final CANTalon[] talons;
	
	/**
	 * Stores the last speed this was set to.
	 */
	private double setSpeed = 0;
	
	/**
	 * Initializes a new CANTalonMotorSetModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param talons The CANTalons to control.
	 * @throws IllegalArgumentException Thrown if any argument is null or talons contains a null.
	 */
	public CANTalonMotorSetModule(String name, LoggerModule logger, CANTalon[] talons) throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, CANTalonMotorSetModule.class, talons);
		for(CANTalon talon : talons)
			if(talon == null) this.logger.failInitializationIllegal(this, CANTalonMotorSetModule.class, "Null Talon", talons);
		this.talons = talons;
		this.logger.logInitialization(this, CANTalonMotorSetModule.class, talons);
	}
	
	/**
	 * Initializes a new CANTalonMotorSetModule with only one talon.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param talon The CANTalon to control.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 * @see CANTalonMotorSetModule#CANTalonMotorSetModule(String, LoggerModule, CANTalon[])
	 */
	public CANTalonMotorSetModule(String name, LoggerModule logger, CANTalon talon) throws IllegalArgumentException {
		this(name, logger, new CANTalon[]{talon});
	}

	@Override
	public synchronized void setMotorSpeed(double speed) throws IllegalArgumentException, ThreadUnauthorizedException {
		this.doAccessibilityCheck();
		if(speed > 1 || speed < -1) this.logger.failSettingIllegal(this, "Motor Speed", "Out Of Bounds", speed);
		this.logger.logSettingPrimitiveArgs(this, "Motor Speed", speed);
		for(CANTalon talon : this.talons)
			talon.set(speed);
		this.setSpeed = speed;
	}

	@Override
	public synchronized double getSetMotorSpeed() {
		this.logger.logGot(this, "Set Motor Speed", this.setSpeed);
		return this.setSpeed;
	}

}
