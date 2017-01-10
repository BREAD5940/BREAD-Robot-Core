package org.team5940.robot_core.modules;

import java.lang.reflect.Array;
import java.util.Hashtable;

public class ModuleHashTable<T extends Module> extends Hashtable<String, T> {//TODO add generics so can specify extention of Module
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new, empty ModuleList.
	 * @param initModules The modules in this ModuleList.
	 */
	public ModuleHashTable() {
		super();
	}
	
	/**
	 * Creates a new ModuleList with initModules and the keys them.getName().
	 * @param initModules The modules in this ModuleList.
	 */
	public ModuleHashTable(T[] initModules) {
		super();
		for(int i = 0; i < initModules.length; i++) {
			T module = initModules[i];
			this.put(module.getName(), module);
		}
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing direct submodules that are assignable from parent.
	 */
	public ModuleHashTable<T> getDirectSubModulesAssignableFrom(Class<? extends Module> parent) {
		ModuleHashTable<T> out = new ModuleHashTable<T>();
		
		@SuppressWarnings("unchecked")
        T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.values().toArray(modules);
		
		for(int i = 0; i < modules.length; i++) {
			T module = modules[i];
			this.put(module.getName(), module);
		}
		
		return out;
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList, including submodules of Modules, where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing all submodules that are assignable from parent.
	 */
	public ModuleHashTable<T> getAllSubModulesAssignableFrom(Class<? extends Module> parent) {
		ModuleHashTable<T> out = new ModuleHashTable<T>();
		
		@SuppressWarnings("unchecked")
        T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.values().toArray(modules);
		
		for(int i = 0; i < modules.length; i++) {
			T module = modules[i];
			this.put(module.getName(), module);
			//TODO add submodules
			
		}
		
		return out;
	}
}
