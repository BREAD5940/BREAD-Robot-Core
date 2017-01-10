package org.team5940.robot_core.modules.implementations;

import org.team5940.robot_core.modules.interfaces.OwnableModule;

public abstract class SimpleOwnableModule implements OwnableModule {
	Thread t;//What is this for? You shouldn't have to store another Thread...
	Thread currentOwner;
	String nameOfModule = "SimpleOwnableModule";

	@Override
	public synchronized String getName() {
		return nameOfModule;
	}

	@Override
	public synchronized boolean isOwnedBy(Thread t) {
		this.t = t;
		if (this.t == t.currentThread()) {

			return true;
		} else if (this.t == null) {// and no owner
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean aquireOwnershipFor(Thread t, boolean force) {
		this.t = t;
		if (currentOwner == null){
			this.t = currentOwner;
		} else if (force == true){ //you should also check that it doesn't currently own it.
			this.t = currentOwner;
		}
		
		return this.isOwnedBy(t);
	}

	@Override
	public boolean relinquishOwnershipFor(Thread t) {//No illegal argument Exception throw...
		if (this.isOwnedBy(t)==true){
			currentOwner=null;
		}
		return this.isOwnedBy(t);
	}

}
