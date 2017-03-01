package org.team5940.robot_core.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.linear.LinearPositionModule;
import org.team5940.robot_core.modules.sensors.linear.LinearVelocityModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocityModule;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

import com.ctre.CANTalon;
//test
/**
 * An implementation of {@link LinearPositionModule},
 * {@link LinearVelocityModule}, {@link RotationalPositionModule},
 * {@link RotationalVelocityModule}, and {@link TestableModule} that uses
 * {@link AbstractModule}
 * 
 * @author Amit Harlev + Noah Sturgeon
 */

public class CANTalonEncoderModule extends AbstractModule implements RotationalPositionModule, RotationalVelocityModule, TestableModule {

	/** The talon this encoder is on.*/
	private final CANTalon talon;
	/** The ratio of encoder pulses to Rotations*/
	private final double pulsesToRotations;
	
	/** Initializes a new CANTalonEncoderModule 
	 * @param name the name of the module
	 * @param dependencies the modules this class is dependent on to function.
	 * @param logger the logger this class is using.
	 * 
	 */
	public CANTalonEncoderModule(String name, ModuleHashtable<Module> dependencies, LoggerModule logger, CANTalon talon, double pulsesToRotation)
			throws IllegalArgumentException {
		super(name, dependencies, logger);
		// TODO Auto-generated constructor stub
		this.logger.checkInitializationArg(this, CANTalonEncoderModule.class, logger);
		this.talon = talon;
		logger.logInitialization(this, CANTalonEncoderModule.class, talon);
		this.pulsesToRotations = pulsesToRotation;
		logger.logInitialization(this, CANTalonEncoderModule.class, pulsesToRotation);
	}

	@Override
	public double getRotationalVelocity() {
		/** returns the rotational velocity */
		return talon.getEncVelocity() * this.pulsesToRotations;
	}

	@Override
	public double getRotationalPosition() {
		/** returns the rotational position */
		return talon.getEncPosition();
	}

	@Override
	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return TestStatus.PASSED;
	}

}
