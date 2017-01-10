package org.team5940.robot_core.modules;

public interface ProcedureModule extends Module {
	
	/**
	 * The states the procedure can be in
	 */
	public enum ProcedureState {
		
		/**
		 * Procedure is running.
		 */
		RUNNING, 
		
		/**
		 * Procedure has not been started since initialization.
		 */
		NOT_STARTED, 
		
		/**
		 * Procedure has finished executing normally.
		 */
		FINISHED, 
		
		/**
		 * Procedure was interrupted prematurely.
		 */
		INTERRUPTED, 
		
		/**
		 * Procedure had an error and is not running.
		 */
		ERROR
		
	}
	
	/**
	 * Starts the procedure if it was not already running. If procedure is newly starting, shouldn't return until the Procedure has entered the RUNNING state.
	 */
	public void start();

	/**
	 * Interrupts the procedure prematurely if it is currently running. If procedure is newly stopping, shouldn't return until the Procedure has entered the INTERRUPTED state.
	 */
	public boolean interrupt();

	/**
	 * Returns the current state of this Procedure.
	 * @return A ProcedureState representing the current state of this ProcedureModule.
	 * @see ProcedureState
	 */
	public ProcedureState getState();

}
