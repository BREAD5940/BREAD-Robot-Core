package org.team5940.robot_core.modules.functional.shifters;

import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.SimpleOwnableModule;
import org.team5940.robot_core.modules.testable.TestRunnerModule;
import org.team5940.robot_core.modules.testable.TestableModule.TestResult;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
* Implementation of ShifterModule to shift gears on the robot.
* @author Alexander Loeffler
*/

public class DoubleSolenoidShifterModule extends SimpleOwnableModule implements ShifterModule{
	
	private DoubleSolenoid solenoid;
	private short currentGear = 0;
	
	/**
	 * Creates a new DoubleSolenoidShifterModule object.
	 * @param name The name of the shifter you are creating.
	 * @param logger The log to save to.
	 * @param chan1 The integer value of high gear.
	 * @param chan2 The integer value of low gear.
	 */
	public DoubleSolenoidShifterModule(String name, LoggerModule logger, int canPort, int chan1, int chan2)
			throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.solenoid = new DoubleSolenoid(canPort, chan1, chan2);
		this.logger.log(this, "Created DoubleSolenoidShifterModule", new Object[]{canPort, chan1, chan2});
	}

	/**
	 * Initializes the shifter to gear 0.
	 * 
	 * Super docs:
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		this.logger.log(this, "Initializing");
		setShift(0);
	}

	@Override
	public void shutDown() {
		this.logger.log(this, "Shutting Down");
	}
	
	@Override
	public void setShift(int gear) throws IllegalArgumentException {
		if(gear == 0) {
			this.logger.vLog(this, "Setting Gear", gear);
			this.solenoid.set(DoubleSolenoid.Value.kForward);
			currentGear=0;
		}else if (gear == 1) {
			this.logger.vLog(this, "Setting Gear", gear);
			this.solenoid.set(DoubleSolenoid.Value.kReverse);
			currentGear=1;
		}else {
			this.logger.vError(this, "Gear Out Of Bounds", gear);
			throw new IllegalArgumentException("Gear out of bounds!");
		}
	}

	@Override
	public int getShift() {
		this.logger.vLog(this, "Getting Gear", this.currentGear);
		return this.currentGear;
	}

	@Override
	public int getNumberOfGears() {
		this.logger.vLog(this, "Getting Number Of Gears", 2);
		return 2;
	}
	/**
	 * A tester for the DoubleSolenoidShifterModule.
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
			setShift(0);
			while(testRunner.getNewReturn())testRunner.promptText("Did it shift to Low gear?(y/n)");
			if(testRunner.getReturnedText()=="y"){
				this.logger.vLog(this, "Low gear shift test Passed");

			}
			else{
				this.logger.vLog(this, "Low gear shift test FAILED");
				testsGood=false;
			}
			setShift(1);
			while(testRunner.getNewReturn())testRunner.promptText("Did it shift to High gear?(y/n)");
			if(testRunner.getReturnedText()=="y"){
				this.logger.vLog(this, "High gear shift test Passed");

			}
			else{
				this.logger.vLog(this, "High gear shift test FAILED");
				testsGood=false;
			}
		}catch (Exception e) {
			return TestResult.ERROR;
		}
		
		if(testsGood) return TestResult.SUCCESSFUL;
		else return TestResult.FAILED;
	}

}
