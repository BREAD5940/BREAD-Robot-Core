package org.team5940.robot_core.modules.sensors.binary_input;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * An implementation of {@link BinaryInputModule} that changes its state after some kind of change in another {@link BinaryInputModule}.
 * @author David Boles
 *
 */
public class TogglingBinaryInputModule extends AbstractModule implements BinaryInputModule {

	//IF Private works update arm
	private boolean state;
	
	/**
	 * Initializes a new {@link TogglingBinaryInputModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param input The input from which to determine this' state.
	 * @param initialState The initial state this should be in.
	 * @param triggerType The type of trigger on which to toggle this' state. True for rising edge or false for falling.
	 * @param sampleRate How often (in Hz) to check for changes.
	 * @throws IllegalArgumentException Thrown if any argument is null or the sample rate is less than or equal to 0.
	 */
	public TogglingBinaryInputModule(String name, LoggerModule logger, BinaryInputModule input, boolean initialState, boolean triggerType, double sampleRate)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(input), logger);
		this.logger.checkInitializationArgs(this, TogglingBinaryInputModule.class, new Object[]{input, triggerType, sampleRate});
		if(sampleRate <= 0)
			this.logger.failInitializationIllegal(this, TogglingBinaryInputModule.class, "Sample Rate <= 0", new Object[]{input, triggerType, sampleRate});
		this.state = initialState;
		(new StateUpdaterThread(logger, this, input, triggerType, Math.round(1000/sampleRate))).start();
		this.logger.logInitialization(this, TogglingBinaryInputModule.class, new Object[]{input, triggerType, sampleRate});
	}

	@Override
	public synchronized boolean getBinaryInput() {
		this.logger.logGot(this, "Binary State", this.state);
		return this.state;
	}
	
	//TODO document
	private class StateUpdaterThread extends Thread {

		private final LoggerModule logger;
		private final TogglingBinaryInputModule toggled;
		private final BinaryInputModule toggler;
		private final boolean triggerType;
		private final long delay;
		private boolean lastState;
		
		public StateUpdaterThread(LoggerModule logger, TogglingBinaryInputModule toggled, BinaryInputModule toggler, boolean triggerType, long delay) {
			this.logger = logger;
			this.toggled = toggled;
			this.toggler = toggler;
			this.triggerType = triggerType;
			this.delay = delay;
			lastState = this.toggler.getBinaryInput();
		}
		
		@Override
		public void run() {
			try{
				while(!this.isInterrupted()) {
					boolean newState = this.toggler.getBinaryInput();
					if(newState != lastState && newState == this.triggerType) {
						boolean toggledState = !this.toggled.state;
						this.logger.vLog(this.toggled, "State Toggled", toggledState);
						this.toggled.state = toggledState;
					}
					this.lastState = newState;
					
					Thread.sleep(delay);
				}
			}catch (Exception e) {
				this.logger.error(this.toggled, "Toggling Update Thread Failed", e);
			}
		}
	}
}
