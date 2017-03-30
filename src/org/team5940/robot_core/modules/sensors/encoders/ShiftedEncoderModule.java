package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.actuators.shifters.ShifterModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocityModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * An "encoder" that multiplies outputs by a scale factor depending on the current gear of a shifter.
 * @author David Boles
 *
 */
public class ShiftedEncoderModule extends AbstractModule
		implements RotationalPositionModule, RotationalVelocityModule {

	//TODO docs
	private final ShifterModule shifter;
	private final double[] scalers;
	private final RotationalPositionModule positionSensor;
	private final RotationalVelocityModule velocitySensor;
	
	public ShiftedEncoderModule(String name, LoggerModule logger, ShifterModule shifter, double[] scalers, RotationalPositionModule positionSensor, RotationalVelocityModule velocitySensor)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>().chainPut(shifter).chainPut(positionSensor).chainPut(velocitySensor), logger);
		this.logger.checkInitializationArgs(this, ShiftedEncoderModule.class, new Object[]{shifter, scalers, positionSensor, velocitySensor});
		if(shifter.getNumberOfGears() != scalers.length)
			this.logger.failInitializationIllegal(this, ShiftedEncoderModule.class, "Unequal Quantities", new Object[]{shifter, scalers, positionSensor, velocitySensor});
		this.shifter = shifter;
		this.scalers = scalers;
		this.positionSensor = positionSensor;
		this.velocitySensor = velocitySensor;
		this.logger.logInitialization(this, ShiftedEncoderModule.class, new Object[]{shifter, scalers, positionSensor, velocitySensor});
	}

	@Override
	public double getRotationalVelocity() {
		double out = this.velocitySensor.getRotationalVelocity() * this.scalers[this.shifter.getShifterGear()];
		this.logger.logGot(this, "Rotational Velocity", out);
		return out;
	}

	@Override
	public double getRotationalPosition() {
		double out = this.positionSensor.getRotationalPosition() * this.scalers[this.shifter.getShifterGear()];
		this.logger.logGot(this, "Rotational Position", out);
		return out;
	}

	@Override
	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		TestStatus status = RotationalPositionModule.super.runTest(comms);
		if(status == TestStatus.PASSED) return RotationalPositionModule.super.runTest(comms);
		return status;
	}

}
