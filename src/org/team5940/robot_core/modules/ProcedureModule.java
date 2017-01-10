package org.team5940.robot_core.modules;

public interface ProcedureModule extends Module {
	
	/**
	 * The states the procedure can be in
	 */
	public enum ProcedureState {
		
		/**
		 * Procedure is running
		 */
		RUNNING, 
		
		/**
		 * Procedure has not been started yet
		 */
		NOT_STARTED, 
		
		/**
		 * Procedure is finished
		 */
		FINISHED, 
		
		/**
		 * Procedure was interrupted prematurely
		 */
		INTERRUPTED, 
		
		/**
		 * Procedure had an error
		 */
		ERROR
		
	}
	
	/**
	 * Starts the procedure
	 * @return if it was able to start it
	 */
	public boolean start();

	/**
	 * Stops the procedure prematurely
	 * @return if it was currently running
	 */
	public boolean stop();

	/**
	 * @return the current state
	 * @see ProcedureState
	 */
	public ProcedureState getState();

}
