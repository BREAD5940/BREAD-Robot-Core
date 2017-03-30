package org.team5940.robot_core.modules.actuators.motor_sets;

import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

public interface TestableMotorSetModule extends MotorSetModule, TestableModule {
	@Override
	public default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			//FORWARD
			this.setMotorSpeed(0.15);
			if(this.getSetMotorSpeed() != 0.15d) {
				this.setMotorSpeed(0);
				this.getModuleLogger().logTestFailed(this, "Wrong Set Forward Speed (" + this.getSetMotorSpeed() + ")");
				return TestStatus.FAILED;
			}
			if(!comms.promptBoolean("Is " + this.getModuleName() + " moving forward?")) {
				this.setMotorSpeed(0);
				this.getModuleLogger().logTestFailed(this, "Not Moving Forward");
				return TestStatus.FAILED;
			}
			
			//BACKWARD
			this.setMotorSpeed(-0.15);
			if(this.getSetMotorSpeed() != -0.15d) {
				this.setMotorSpeed(0);
				this.getModuleLogger().logTestFailed(this, "Wrong Set Forward Speed (" + this.getSetMotorSpeed() + ")");
				return TestStatus.FAILED;
			}
			if(!comms.promptBoolean("Is " + this.getModuleName() + " moving backward?")) {
				this.setMotorSpeed(0);
				this.getModuleLogger().logTestFailed(this, "Not Moving Backward");
				return TestStatus.FAILED;
			}
			
			//RESET
			this.setMotorSpeed(0);
			this.getModuleLogger().logTestPassed(this);
			return TestStatus.PASSED;
		}catch(Exception e) {
			this.getModuleLogger().logTestErrored(this, e);
			return TestStatus.ERRORED;
		}
		
	}
}
