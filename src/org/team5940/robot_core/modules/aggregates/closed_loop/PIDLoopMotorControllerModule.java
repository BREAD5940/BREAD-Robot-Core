package org.team5940.robot_core.modules.aggregates.closed_loop;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.actuators.linear.LinearPositionActuatorModule;
import org.team5940.robot_core.modules.actuators.linear.LinearVelocityActuatorModule;
import org.team5940.robot_core.modules.actuators.motor_sets.MotorSetModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.sensors.linear.LinearPositionSensorModule;
import org.team5940.robot_core.modules.sensors.linear.LinearVelocitySensorModule;

public class PIDLoopMotorControllerModule extends AbstractOwnableModule
		implements LinearPositionActuatorModule, LinearVelocityActuatorModule {

	private final double velocityP;
	private final double positionP;
	private final double velocityI;
	private final double positionI;
	private final double velocityD;
	private final double positionD;
	private final LinearPositionSensorModule positionSensor;
	private final LinearVelocitySensorModule velocitySensor;
	private final MotorSetModule motors;
	private final ControllerThread motorController;

	public PIDLoopMotorControllerModule(String name, LoggerModule logger, double velocityP, double positionP,
			double velocityI, double positionI, double velocityD, double positionD,
			LinearPositionSensorModule positionSensor, LinearVelocitySensorModule velocitySensor, MotorSetModule motors)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>().chainPut(positionSensor).chainPut(velocitySensor).chainPut(motors),
				logger);
		this.logger.checkInitializationArgs(this, PIDLoopMotorControllerModule.class,
				new Object[] { velocityP, positionP, velocityI, positionI, velocityD, positionD, positionSensor, velocitySensor, motors });
		this.velocityP = velocityP;
		this.positionP = positionP;
		this.velocityI = velocityI;
		this.positionI = positionI;
		this.velocityD = velocityD;
		this.positionD = positionD;
		this.positionSensor = positionSensor;
		this.velocitySensor = velocitySensor;
		this.motors = motors;
		motorController = new ControllerThread();
		motorController.start();
		this.logger.logInitialization(this, PIDLoopMotorControllerModule.class,
				new Object[] { velocityP, positionP, velocityI, positionI, velocityD, positionD, positionSensor, velocitySensor, motors });
	}

	@Override
	public void setLinearVelocity(double velocity) {
		motorController.setVelocity(velocity);
	}

	@Override
	public void setLinearPosition(double position) {
		motorController.setPosition(position);
	}

	private class ControllerThread extends Thread {

		private boolean isVelocitySetLast;
		private double setVelocity;
		private double setPosition;
		private double totalPositionError;
		private double totalVelocityError;
		private double previousPositionError;
		private double previousVelocityError;

		public ControllerThread() {
			this.isVelocitySetLast = true;
			this.setPosition = 0;
			this.setVelocity = 0;
			this.totalPositionError = 0;
			this.totalVelocityError = 0;
			this.previousPositionError = 0;
			this.previousVelocityError = 0;
		}

		@Override
		public void run() {
			double error;
			try {
				while (!this.isInterrupted()) {
					if (this.isVelocitySetLast) {
						error = this.setVelocity - velocitySensor.getLinearVelocity();
						this.totalVelocityError += error;
						double dError = error - previousVelocityError;
						double dValue = dError * velocityD;
						double iValue = this.totalVelocityError * velocityI;
						double speedIncrease = error / velocityP;
						double newSpeed = speedIncrease + iValue + dValue + motors.getSetMotorSpeed();
						motors.setMotorSpeed(Math.min(Math.max(newSpeed, -1), 1));
						this.previousVelocityError = error;
					} else {
						error = this.setPosition - positionSensor.getLinearPosition();
						this.totalPositionError += error;
						double dError = error - this.previousPositionError;
						double iValue = this.totalPositionError * positionI;
						double pValue = error / positionP;
						double dValue = dError * positionD;
						double newSpeed = iValue + pValue + dValue;
						motors.setMotorSpeed(Math.min(Math.max(newSpeed, -1), 1));
						this.previousPositionError = error;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void setPosition(double position) {
			this.setPosition = position;
			this.isVelocitySetLast = false;
		}

		public void setVelocity(double velocity) {
			this.setVelocity = velocity;
			this.isVelocitySetLast = true;
		}
	}
}