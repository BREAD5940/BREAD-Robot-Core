package org.team5940.robot_core.modules.implementations;

import org.team5940.robot_core.modules.interfaces.OwnableModule;

public abstract class SimpleOwnableModule implements OwnableModule {
	Thread t;//I would call this something more obvious.
	String nameOfModule = "SimpleOwnableModule";//Name should be determined by an argument in a constructor

	@Override
	public synchronized String getName() {
		return nameOfModule;
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		this.t = t;//What? Why are setting an object variable to the argument when checking
		if (this.t == t.currentThread()) {
			return true;
		} else if (this.t == null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean aquireOwnershipFor(Thread t, boolean force) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean relinquishOwnershipFor(Thread t) {
		// TODO Auto-generated method stub
		return false;
	}

}
