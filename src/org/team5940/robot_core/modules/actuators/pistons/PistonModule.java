package org.team5940.robot_core.modules.actuators.pistons;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;
/**
 * Interface for controlling a two state piston.
 * @author Ryan Cen
 *
 */
public interface PistonModule extends OwnableModule, TestableModule {
	
	/**
	 * Sets the state of the piston.
	 * @param state The piston state as a boolean: true for extended, false for contracted.
	 * @throws ThreadUnauthorizedException If this is not accessible to the current thread.
	 */
	public void setPistonState(boolean state) throws ThreadUnauthorizedException;
	
	/**
	 * Gets the state of the piston.
	 * @return The piston state as a boolean: true for extended, false for contracted.
	 */
	public boolean getPistonState();
	
	@Override
	default TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
		try {
			//FORWARD
			this.setPistonState(true);
			if(this.getPistonState() != true) {
				this.setPistonState(false);
				this.getModuleLogger().logTestFailed(this, "Wrong Set Piston State: Retracted");
				return TestStatus.FAILED;
			}
			if(!comms.promptBoolean("Is " + this.getModuleName() + " extended?")) {
				this.setPistonState(false);
				this.getModuleLogger().logTestFailed(this, "Not Extended");
				return TestStatus.FAILED;
			}
			
			//BACKWARD
			this.setPistonState(false);
			if(this.getPistonState() != false) {
				this.setPistonState(false);
				this.getModuleLogger().logTestFailed(this, "Wrong Set Piston State: Extended");
				return TestStatus.FAILED;
			}
			if(!comms.promptBoolean("Is " + this.getModuleName() + " retracted?")) {
				this.setPistonState(false);
				this.getModuleLogger().logTestFailed(this, "Not Retracted");
				return TestStatus.FAILED;
			}
			
			//RESET
			this.setPistonState(false);
			this.getModuleLogger().logTestPassed(this);
			return TestStatus.PASSED;
		}catch(Exception e) {
			this.getModuleLogger().logTestErrored(this, e);
			return TestStatus.ERRORED;
		}
		
	}
	
	/**
	 * A piston that does nothing named "inert_piston". Always owned and state is always false ("contracted").
	 */
	public static final PistonModule INERT_PISTON = new PistonModule() {
		
		@Override
		public String getModuleName() {
			return "inert_piston";
		}
		
		@Override
		public LoggerModule getModuleLogger() {
			return LoggerModule.INERT_LOGGER;
		}
		
		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>();
		}
		
		@Override
		public void relinquishOwnershipFor(Thread t) throws IllegalArgumentException { }
		
		@Override
		public boolean isOwnedBy(Thread t) {
			return true;
		}
		
		@Override
		public boolean acquireOwnershipFor(Thread t, boolean force) {
			return false;
		}
		
		@Override
		public void setPistonState(boolean state) throws ThreadUnauthorizedException { }
		
		@Override
		public boolean getPistonState() {
			return false;
		}
	};
}