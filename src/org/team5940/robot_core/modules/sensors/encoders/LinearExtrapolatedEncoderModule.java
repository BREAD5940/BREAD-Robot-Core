package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.linear.LinearPositionSensorModule;
import org.team5940.robot_core.modules.sensors.linear.LinearVelocitySensorModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionSensorModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocitySensorModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * Extrapolates the linear movement of the outside of a wheel from it's rotation.
 * @author David Boles
 *
 */
public class LinearExtrapolatedEncoderModule extends AbstractModule implements LinearPositionSensorModule, LinearVelocitySensorModule {

	//TODO docs
	private final RotationalPositionSensorModule positionSensor;
	private final RotationalVelocitySensorModule velocitySensor;
	private final double wheelRadius;
	
	public LinearExtrapolatedEncoderModule(String name, LoggerModule logger, RotationalPositionSensorModule positionSensor, RotationalVelocitySensorModule velocitySensor, double wheelRadius)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>().chainPut(positionSensor).chainPut(velocitySensor), logger);
		this.logger.checkInitializationArgs(this, LinearExtrapolatedEncoderModule.class, new Object[]{positionSensor, velocitySensor, wheelRadius});
		this.positionSensor = positionSensor;
		this.velocitySensor = velocitySensor;
		this.wheelRadius = wheelRadius;
		this.logger.logInitialization(this, LinearExtrapolatedEncoderModule.class, new Object[]{positionSensor, velocitySensor, wheelRadius});
	}

	@Override
	public double getLinearVelocity() {
		double out = this.velocitySensor.getRotationalVelocity() * this.wheelRadius;
		this.logger.logGot(this, "Linear Velocity", out);
		return out;
	}

	@Override
	public double getLinearPosition() {
		double out = this.positionSensor.getRotationalPosition() * this.wheelRadius;
		this.logger.logGot(this, "Linear Position", out);
		return out;
	}

	@Override
	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		TestStatus status = LinearPositionSensorModule.super.runTest(comms);
		if(status == TestStatus.PASSED) return LinearPositionSensorModule.super.runTest(comms);
		return status;
	}

}
