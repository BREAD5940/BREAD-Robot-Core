package org.team5940.robot_core.modules.procedure;

import org.team5940.robot_core.modules.Module;

/**
 * A ProcedureModule contains control methods for an executable Thread. By definition of a ProcedureModule, each instance corresponds to one Thread and the ProcedureModule can be in one of the five {@link ProcedureState}s.
 * @author David Boles
 *
 */
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
		 * Procedure was interrupted prematurely and is not running.
		 */
		INTERRUPTED, 
		
		/**
		 * Procedure had an error and is not running.
		 */
		ERRORED
		
	}
	
	/**
	 * Starts the procedure if it was not already running. If procedure is newly starting, shouldn't return until the Procedure has entered the RUNNING state.
	 */
	public void start();

	/**
	 * Interrupts the procedure prematurely if it is currently running. If procedure is newly stopping, shouldn't return until the Procedure has entered the INTERRUPTED state.
	 */
	public void interrupt();

	/**
	 * Returns the current state of this Procedure.
	 * @return A ProcedureState representing the current state of this ProcedureModule.
	 * @see ProcedureState
	 */
	public ProcedureState getState();

}
