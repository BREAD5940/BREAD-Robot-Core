package org.team5940.robot_core.modules.actuators.shifters;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * Interface for a shifter with any number of gears.
 * @author Noah Sturgeon
 *
 */
public interface ShifterModule extends OwnableModule, TestableModule {
	/**
	 * Sets the ShifterModule to the gear specified. Lower number means lower speed but more torque.
	 * @param gear The gear to set this to.
	 * @throws IllegealArgumentException if gear &gt;= getNumberOfGears() or gear &lt; 0.
	 * @throws ThreadUnauthorizedException If this is not accessible to the current thread.
	 */
	public void setShifterGear(int gear) throws IllegalArgumentException, ThreadUnauthorizedException;
	
	/**
	 * Gets the gear this is currently shifted to.
	 * @return The current gear this is shifted to.
	 */
	public int getShifterGear();
	
	/**
	 * Gets the number of gears this can be set to.
	 * @return An int representing this' number of gears.
	 */
	public int getNumberOfGears();
	
	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			//SHIFTING
			for(int i = 0; i < this.getNumberOfGears(); i++){
					
				this.setShifterGear(i);
				if(this.getShifterGear() != i) {
					this.setShifterGear(0);
					this.getModuleLogger().logTestFailed(this, "Wrong Gear (" + this.getShifterGear() + ")");
					return TestStatus.FAILED;
				}
				if(!comms.promptBoolean("Is " + this.getModuleName() + " in gear " + i + "?")) {
					this.setShifterGear(0);
					this.getModuleLogger().logTestFailed(this, "Not In Gear " + i);
					return TestStatus.FAILED;
				}
			}
			
			//RESET
			this.setShifterGear(0);
			this.getModuleLogger().logTestPassed(this);
			return TestStatus.PASSED;
		}catch(Exception e) {
			this.getModuleLogger().logTestErrored(this, e);
			return TestStatus.ERRORED;
		}
	
	}
	
	/**
	 * A single gear shifter that does nothing. This is useful for drivetrains that don't have a shifter.
	 */
	public static final ShifterModule INERT_SHIFTER = new ShifterModule() {

		@Override
		public boolean isOwnedBy(Thread t) {
			return true;
		}

		@Override
		public boolean acquireOwnershipFor(Thread t, boolean force) {
			return true;
		}

		@Override
		public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException {}

		@Override
		public String getModuleName() {
			return "inert_shifter";
		}

		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>();
		}

		@Override
		public LoggerModule getModuleLogger() {
			return LoggerModule.INERT_LOGGER;
		}

		@Override
		public void setShifterGear(int gear) throws IllegalArgumentException, ThreadUnauthorizedException {}

		@Override
		public int getShifterGear() {
			return 0;
		}

		@Override
		public int getNumberOfGears() {
			return 1;
		}
		
	};
}