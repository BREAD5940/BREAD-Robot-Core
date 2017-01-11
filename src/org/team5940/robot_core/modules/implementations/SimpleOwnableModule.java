package org.team5940.robot_core.modules.implementations;

import org.team5940.robot_core.modules.interfaces.OwnableModule;

public abstract class SimpleOwnableModule implements OwnableModule {
	
	Thread currentOwner;
	String nameOfModule = "SimpleOwnableModule";

	@Override
	public synchronized String getName() {
		return nameOfModule;
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		
		if (currentOwner == t) {

			return true;
		} else if (currentOwner == null) {
			return true;
		} else if (t==null) {
			return true;
		}
		else {
			return false;
		}

	}

	@Override
	public boolean aquireOwnershipFor(Thread t, boolean force) {
		
		if (currentOwner == null && currentOwner != t){
			currentOwner = t;
		} else if (force == true && currentOwner != t){
			currentOwner = t;
		}
		
		
		return this.isOwnedBy(t);
	}

	@Override
	public boolean relinquishOwnershipFor(Thread t) {
		if (this.isOwnedBy(t)==true && t != null){
			currentOwner=null;
		}
		return this.isOwnedBy(t);
	}

}
