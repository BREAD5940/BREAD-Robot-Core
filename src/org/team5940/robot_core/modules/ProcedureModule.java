package org.team5940.robot_core.modules;

public abstract class ProcedureModule implements Module {

	// Variables in interfaces have to be final and static meaning currentState
	// would not work. I changed it to an abstract class to fix that. Tell me if
	// this does not work for what you are planning.

	ProcedureState currentState;

	public enum ProcedureState {
		RUNNING, NOT_STARTED, FINISHED, INTERRUPTED, ERROR
	}

	public boolean start() {
		if (!currentState.equals(ProcedureState.RUNNING)) {
			// TODO make it start it
			currentState = ProcedureState.RUNNING;
			return true;
		}
		return false;
	}

	public boolean interrupt() {
		if (currentState.equals(ProcedureState.RUNNING)) {
			// TODO interrupt it
			currentState = ProcedureState.INTERRUPTED;
			return true;
		}
		return false;
	}

	public ProcedureState getState() {
		return currentState;
	}
}
