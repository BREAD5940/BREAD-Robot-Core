package org.team5940.robot_core.modules.implementations;

import org.team5940.robot_core.modules.interfaces.OwnableModule;

public abstract class SimpleOwnableModule implements OwnableModule {
	Thread t;
	Thread currentOwner;
	String nameOfModule = "SimpleOwnableModule";

	@Override
	public synchronized String getName() {
		// TODO Auto-generated method stub
		return nameOfModule;
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		// TODO Auto-generated method stub
		this.t = t;
		if (this.t == currentOwner) {
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
		this.t = t;
		if (currentOwner == null){
			this.t = currentOwner;
		} else if (force == true){
			this.t = currentOwner;
		}
		
		return this.isOwnedBy(t);
	}

	@Override
	public boolean relinquishOwnershipFor(Thread t) {
		// TODO Auto-generated method stub
		return false;
	}

}
