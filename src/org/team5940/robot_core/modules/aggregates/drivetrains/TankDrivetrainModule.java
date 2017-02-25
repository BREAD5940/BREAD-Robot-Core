package org.team5940.robot_core.modules.aggregates.drivetrains;

import org.team5940.robot_core.modules.actuators.shifters.ShifterModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * Represents a drivetrain with two fixed sets of wheels and optionally a shifter.
 * 
 * @author Noah Sturgeon + Dean Brown + artwork by Ethan Shedd.
 *
 */
public interface TankDrivetrainModule extends OwnableModule, ShifterModule, TestableModule {
	
	/**
	 * Updates the drivetrain with tank controls.
	 * @param left The speed you want for the left side. -1 to 1, inclusive for both.
	 * @param right The speed you want for the right side. -1 to 1, inclusive for both.
	 * @throws IllegalArgumentException If either argument is out of bounds.
	 * @throws ThreadUnauthorizedException If the current thread does not have access to this module.
	 */
 	public void updateTank(double left, double right) throws ThreadUnauthorizedException, IllegalArgumentException;
	
	/**
	 * Updates the drivetrain with arcade controls, multiplexing straight and turning.
	 * @param velocity The amount it should be moving forward or backward. 1 to -1 respectively, inclusive for both.
	 * @param yaw The amount it should be turning right or left. 1 to -1 respectively, inclusive for both.
	 * @throws IllegalArgumentException If either argument is out of bounds.
	 * @throws ThreadUnauthorizedException If the current thread does not have access to this module.
	 */
	public void updateArcade(double velocity, double yaw) throws ThreadUnauthorizedException, IllegalArgumentException;

	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			//SHIFTING
			TestStatus shiftingStatus = ShifterModule.super.runTest(comms);
			if(shiftingStatus != TestStatus.PASSED) return shiftingStatus;
			
			//FORWARD TANK
			this.updateTank(0.15,0.15);
			if(!comms.promptBoolean("(Tank) Is " + this.getModuleName() + " moving forward?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Forward");
				return TestStatus.FAILED;
			}
			
			//BACKWARD TANK
			this.updateTank(-0.15,-0.15);
			if(!comms.promptBoolean("(Tank) Is " + this.getModuleName() + " moving backward?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Backward");
				return TestStatus.FAILED;
			}
			
			//RIGHT TANK
			this.updateTank(0.15,-0.15);
			if(!comms.promptBoolean("(Tank) Is " + this.getModuleName() + " moving right?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Right");
				return TestStatus.FAILED;
			}
			
			//LEFT TANK
			this.updateTank(-0.15,0.15);
			if(!comms.promptBoolean("(Tank) Is " + this.getModuleName() + " moving left?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Left");
				return TestStatus.FAILED;
			}

			//FORWARD ARCADE
			this.updateArcade(0.15,0);
			if(!comms.promptBoolean("(Arcade) Is " + this.getModuleName() + " moving forward?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Forward");
				return TestStatus.FAILED;
			}
			
			//BACKWARD ARCADE
			this.updateArcade(-0.15,0);
			if(!comms.promptBoolean("(Arcade) Is " + this.getModuleName() + " moving backward?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Backward");
				return TestStatus.FAILED;
			}
			
			//RIGHT ARCADE
			this.updateArcade(0,0.15);
			if(!comms.promptBoolean("(Arcade) Is " + this.getModuleName() + " moving right?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Right");
				return TestStatus.FAILED;
			}
			
			//LEFT ARCADE
			this.updateArcade(0,-0.15);
			if(!comms.promptBoolean("(Arcade) Is " + this.getModuleName() + " moving left?")) {
				this.updateTank(0,0);
				this.getModuleLogger().logTestFailed(this, "Tank Not Moving Left");
				return TestStatus.FAILED;
			}
			
			//RESET
			this.updateTank(0,0);
			return TestStatus.PASSED;
		}catch(Exception e) {
			this.getModuleLogger().logTestErrored(this, e);
			return TestStatus.ERRORED;
		}
	}
}
