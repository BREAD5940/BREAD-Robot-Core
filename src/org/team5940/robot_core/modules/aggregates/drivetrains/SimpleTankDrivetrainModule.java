package org.team5940.robot_core.modules.aggregates.drivetrains;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.actuators.motor_sets.MotorSetModule;
import org.team5940.robot_core.modules.actuators.shifters.ShifterModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

/**
 * A simple implementation of {@link TankDrivetrainModule}.
 * @author David Boles
 *
 */
public class SimpleTankDrivetrainModule extends AbstractOwnableModule implements TankDrivetrainModule {

	/**
	 * Stores this' left motors.
	 */
	private final MotorSetModule leftMotors;
	
	/**
	 * Stores this' right motors.
	 */
	private final MotorSetModule rightMotors;
	
	/**
	 * Stores this' shifter.
	 */
	private final ShifterModule shifter;
	
	/**
	 * Initializes a new SimpleTankDrivetrainModule.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param leftMotors The left motors of this drivetrain.
	 * @param rightMotors The right motors of this drivetrain.
	 * @param shifter The shifter of this drivetrain.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public SimpleTankDrivetrainModule(String name, LoggerModule logger, MotorSetModule leftMotors, MotorSetModule rightMotors, ShifterModule shifter)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(new Module[]{leftMotors, rightMotors, shifter}), logger);
		this.logger.checkInitializationArgs(this, SimpleTankDrivetrainModule.class, new Object[]{leftMotors, rightMotors, shifter});
		this.leftMotors = leftMotors;
		this.rightMotors = rightMotors;
		this.shifter = shifter;
		this.logger.logInitialization(this, SimpleTankDrivetrainModule.class, new Object[]{leftMotors, rightMotors, shifter});
	}
	
	/**
	 * Initializes a new SimpleTankDrivetrainModule with {@link ShifterModule#INERT_SHIFTER}.
	 * @see SimpleTankDrivetrainModule#SimpleTankDrivetrainModule(String, LoggerModule, MotorSetModule, MotorSetModule, ShifterModule)
	 */
	public SimpleTankDrivetrainModule(String name, LoggerModule logger, MotorSetModule leftMotors, MotorSetModule rightMotors) throws IllegalArgumentException {
		this(name, logger, leftMotors, rightMotors, ShifterModule.INERT_SHIFTER);
	}

	@Override
	public void updateTank(double left, double right) throws ThreadUnauthorizedException, IllegalArgumentException {
		if(left < -1 || left > 1 || right < -1 || right > 1 )
			this.logger.failSettingIllegal(this, "Tank Values", "Out Of Bounds", new Object[]{left, right});
		this.logger.logSettingPrimitiveArgs(this, "Tank Values", new Object[]{left, right});
		this.leftMotors.setMotorSpeed(left);
		this.rightMotors.setMotorSpeed(right);
	}

	@Override
	public void updateArcade(double velocity, double yaw) throws ThreadUnauthorizedException, IllegalArgumentException {
		if(velocity < -1 || velocity > 1 || yaw < -1 || yaw > 1 )
			this.logger.failSettingIllegal(this, "Tank Values", "Out Of Bounds", new Object[]{velocity, yaw});
		this.logger.logSettingPrimitiveArgs(this, "Tank Values", new Object[]{velocity, yaw});
		
		double absVelocity = Math.abs(velocity);
		double absYaw = Math.abs(yaw);
		double absTotal = absVelocity + absYaw;
		double scaledVelocity = velocity * (absVelocity/absTotal);
		double scaledYaw = yaw * (absYaw/absTotal);
		this.leftMotors.setMotorSpeed(scaledVelocity + scaledYaw);
		this.rightMotors.setMotorSpeed(scaledVelocity - scaledYaw);
	}

	@Override
	public void setShifterGear(int gear) throws IllegalArgumentException, ThreadUnauthorizedException {
		this.shifter.setShifterGear(gear);
	}

	@Override
	public int getShifterGear() {
		return this.shifter.getShifterGear();
	}

	@Override
	public int getNumberOfGears() {
		return this.shifter.getNumberOfGears();
	}

}
