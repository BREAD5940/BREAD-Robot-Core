package org.team5940.robot_core.modules;

import java.util.AbstractList;
import java.util.Hashtable;

public class ModuleHashTable<T extends Module> extends Hashtable<String, T> {//TODO add generics so can specify extention of Module
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ModuleList with modules and the keys them.getName().
	 * @param modules The modules in this ModuleList.
	 */
	public ModuleHashTable(AbstractList<T> modules) {
		//TODO
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing direct submodules that are assignable from parent.
	 */
	public ModuleHashTable<T> getDirectSubModulesAssignableFrom(Class<? extends Module> parent) {
		return null;//TODO do it
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList, including submodules of Modules, where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing all submodules that are assignable from parent.
	 */
	public ModuleHashTable<T> getAllSubModulesAssignableFrom(Class<? extends Module> parent) {
		return null;//TODO do it
	}
}
