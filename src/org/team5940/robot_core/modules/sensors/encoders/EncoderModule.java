package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionSensorModule;
import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocitySensorModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

public interface EncoderModule extends RotationalPositionSensorModule, RotationalVelocitySensorModule {

	@Override
	public default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		TestStatus position = RotationalPositionSensorModule.super.runTest(comms);
		if(position == TestStatus.PASSED)
				return RotationalVelocitySensorModule.super.runTest(comms);
		return position;
	}
}
