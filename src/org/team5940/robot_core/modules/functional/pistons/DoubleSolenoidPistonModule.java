package org.team5940.robot_core.modules.functional.pistons;

import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.SimpleOwnableModule;
import org.team5940.robot_core.modules.testable.TestRunnerModule;
import org.team5940.robot_core.modules.testable.TestableModule.TestResult;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
* Implementation of PistonModule to extend/retract pistons on the robot.
* @author Amit Harlev
*/

public class DoubleSolenoidPistonModule extends SimpleOwnableModule implements PistonModule {
	
	private DoubleSolenoid solenoid;
	private boolean currentState;
	
	/**
	 * Creates a new DoubleSolenoidPistonModule object.
	 * @param name The name of the piston you are creating.
	 * @param logger The log to save to.
	 * @param chan1 The integer value of the first channel.
	 * @param chan2 The integer value of the second channel.
	 */
	
	public DoubleSolenoidPistonModule(String name, LoggerModule logger, int canPort, int chan1, int chan2)
			throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.logger.log(this, "Creating DoubleSolenoidPistonModule", new Object[]{canPort, chan1, chan2});
		this.solenoid = new DoubleSolenoid(canPort, chan1, chan2);
	}

	/**
	 * Initializes the piston to retracted (State False).
	 * 
	 * Super docs:
	 * {@inheritDoc}
	 */
	
	@Override
	public void initialize() {
		this.logger.log(this, "Initializing");
		setPistonState(false);
	}

	@Override
	public void shutDown() {
		this.logger.log(this, "Shutting Down");
	}

	@Override
	public boolean getPistonState() {
		this.logger.vLog(this, "Getting State", this.currentState);
		return this.currentState;
	}

	@Override
	public void setPistonState(boolean state) {
		
		if(state) {
			this.logger.vLog(this, "Extending Piston", state);
			this.solenoid.set(DoubleSolenoid.Value.kForward);
			this.currentState = true; 
		}else {
			this.logger.vLog(this, "Retract Piston", state);
			this.solenoid.set(DoubleSolenoid.Value.kReverse);
			this.currentState = false;
		}
	}
	
	/**
	 * A tester for the DoubleSolenoidPistonModule.
	 * @param testRunner The test runner for running the test.
	 * @return The test result.
	 * @throws IllegalArgumentException Throwing the IllegalArgumentException.
	 */
	
	public TestResult runTest(TestRunnerModule testRunner) throws IllegalArgumentException{
		if(testRunner == null) {
			this.logger.vError(this, "Test Run With Null testRunner");
			throw new IllegalArgumentException("testRunner null");
		}
		boolean testsGood = true;
		
		try {
			setPistonState(true);
			while(testRunner.getNewReturn())testRunner.promptText("Did it extend the piston?(y/n)");
			if(testRunner.getReturnedText()=="y"){
				this.logger.vLog(this, "Extend piston test Passed");

			}
			else{
				this.logger.vLog(this, "Extend piston test FAILED");
				testsGood=false;
			}
			setPistonState(false);
			while(testRunner.getNewReturn())testRunner.promptText("Did it retract the piston?(y/n)");
			if(testRunner.getReturnedText()=="y"){
				this.logger.vLog(this, "Retract piston test Passed");

			}
			else{
				this.logger.vLog(this, "Retract piston test FAILED");
				testsGood=false;
			}
		}catch (Exception e) {
			return TestResult.ERROR;
		}
		
		if(testsGood) return TestResult.SUCCESSFUL;
		else return TestResult.FAILED;
	}


}
