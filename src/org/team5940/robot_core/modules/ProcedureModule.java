package org.team5940.robot_core.modules;

public interface ProcedureModule extends Module {

	public enum ProcedureState {
		RUNNING, NOT_STARTED, FINISHED, INTERRUPTED, ERROR
	}

	public boolean start();

	public boolean interrupt();

	public ProcedureState getState();

}
