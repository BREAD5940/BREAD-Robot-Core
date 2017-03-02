package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocityModule;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

import com.ctre.CANTalon;

/**
 * An implementation of {@link RotationalPositionModule} and {@link RotationalVelocityModule}.
 * 
 * @author Amit Harlev + Noah Sturgeon + Alex Loeffler + David Boles
 */
public class CANTalonEncoderModule extends AbstractModule implements RotationalPositionModule, RotationalVelocityModule, TestableModule {//TODO hasn't been tested

	/** 
	 * The talon this encoder is on.
	 */
	private final CANTalon talon;
	
	/** 
	 * The ratio of encoder pulses to rotations.
	 */
	private final double pulsesToRotations;
	
	/** Initializes a new {@link CANTalonEncoderModule}.
	 * @param name This' name.
	 * @param logger The logger this is using.
	 * @param talon The talon the encoder is connected to.
	 * @param pulsesToRotation The pulses per rotation of the encoder (and gearbox if applicable).
	 */
	public CANTalonEncoderModule(String name, LoggerModule logger, CANTalon talon, double pulsesToRotation)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArgs(this, CANTalonEncoderModule.class, new Object[]{talon, pulsesToRotation});
		this.talon = talon;
		this.pulsesToRotations = pulsesToRotation;
		logger.logInitialization(this, CANTalonEncoderModule.class, new Object[]{talon, pulsesToRotation});
	}

	@Override
	public double getRotationalVelocity() {
		double out = this.talon.getEncVelocity() * this.pulsesToRotations;
		this.logger.logGot(this, "Rotational Velocity", out);
		return out;
	}

	@Override
	public double getRotationalPosition() {
		double out = talon.getEncPosition() * this.pulsesToRotations;
		this.logger.logGot(this, "Rotational Position", out);
		return out;
	}

	@Override
	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		TestStatus position = RotationalPositionModule.super.runTest(comms);
		if(position == TestStatus.PASSED)
				return RotationalVelocityModule.super.runTest(comms);
		return position;
	}

}
