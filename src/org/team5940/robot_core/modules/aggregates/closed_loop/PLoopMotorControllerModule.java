package org.team5940.robot_core.modules.aggregates.closed_loop;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.actuators.linear.LinearPositionActuatorModule;
import org.team5940.robot_core.modules.actuators.linear.LinearVelocityActuatorModule;
import org.team5940.robot_core.modules.actuators.motor_sets.MotorSetModule;
import org.team5940.robot_core.modules.actuators.rotational.RotationalPositionActuatorModule;
import org.team5940.robot_core.modules.actuators.rotational.RotationalVelocityActuatorModule;
import org.team5940.robot_core.modules.aggregates.drivetrains.TankDrivetrainModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.sensors.encoders.EncoderModule;
import org.team5940.robot_core.modules.sensors.linear.LinearPositionSensorModule;
import org.team5940.robot_core.modules.sensors.linear.LinearVelocitySensorModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PLoopMotorControllerModule extends AbstractOwnableModule
		implements RotationalPositionActuatorModule, RotationalVelocityActuatorModule {

	private final double p;
	private final LinearPositionSensorModule positionSensor;
	private final LinearVelocitySensorModule velocitySensor;
	private final MotorSetModule motors;
	private final ControllerThread motorController;

	public PLoopMotorControllerModule(String name, LoggerModule logger, double p, LinearPositionSensorModule positionSensor,
			LinearVelocitySensorModule velocitySensor, MotorSetModule motors) throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>().chainPut(positionSensor).chainPut(velocitySensor).chainPut(motors), logger);
		this.logger.checkInitializationArgs(this, PLoopMotorControllerModule.class,
				new Object[] { p, positionSensor, velocitySensor, motors });
		this.p = p;
		this.positionSensor = positionSensor;
		this.velocitySensor = velocitySensor;
		this.motors = motors;
		motorController = new ControllerThread();
		motorController.start();
		this.logger.logInitialization(this, PLoopMotorControllerModule.class, new Object[] { p, positionSensor, velocitySensor, motors });
	}

	@Override
	public void setRotationalVelocity(double velocity) {
		motorController.setVelocity(velocity);
	}

	@Override
	public void setRotationalPosition(double position) {
		motorController.setPosition(position);
	}

	private class ControllerThread extends Thread {

		private boolean isVelocitySetLast;
		private double setVelocity;
		private double setPosition;

		public ControllerThread() {
			this.isVelocitySetLast = false;
			this.setPosition = 0;
			this.setVelocity = 0;
		}

		@Override
		public void run() {
			double error;
			try {
				while (!this.isInterrupted()) {
					if (this.isVelocitySetLast) {
						error = this.setVelocity - velocitySensor.getLinearVelocity();
						double speedIncrease = error / p;
						double newSpeed = speedIncrease + motors.getSetMotorSpeed();
						motors.setMotorSpeed(newSpeed);
					} else {
						error = this.setPosition - positionSensor.getLinearPosition();
						double speed = error / p;
						motors.setMotorSpeed(speed);
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
