package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.linear.LinearPositionModule;
import org.team5940.robot_core.modules.sensors.linear.LinearVelocityModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocityModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * Extrapolates the linear movement of the outside of a wheel from it's rotation.
 * @author David Boles
 *
 */
public class LinearExtrapolatedEncoderModule extends AbstractModule implements LinearPositionModule, LinearVelocityModule {

	//TODO docs
	private final RotationalPositionModule positionSensor;
	private final RotationalVelocityModule velocitySensor;
	private final double wheelRadius;
	
	public LinearExtrapolatedEncoderModule(String name, LoggerModule logger, RotationalPositionModule positionSensor, RotationalVelocityModule velocitySensor, double wheelRadius)
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
		TestStatus status = LinearPositionModule.super.runTest(comms);
		if(status == TestStatus.PASSED) return LinearPositionModule.super.runTest(comms);
		return status;
	}

}
