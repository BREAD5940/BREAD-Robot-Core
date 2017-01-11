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
	 * @param initModules The modules in this ModuleList. This can be null or empty if you do not want any.
	 */
	public ModuleHashTable(T[] initModules) {
		super();
		if(initModules != null) {
			for(int i = 0; i < initModules.length; i++) {
				T module = initModules[i];
				this.put(module.getName(), module);
			}
		}
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList where parent.isAssignableFrom(sub.getClass()) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing direct submodules that are assignable from parent.
	 * @throws IllegalArgumentException if parent is null.
	 */
	@SuppressWarnings("unchecked")
	public <M extends Module> ModuleHashTable<M> getDirectSubModulesAssignableTo(Class<M> parent) throws IllegalArgumentException {
		if(parent == null) throw new IllegalArgumentException("Parent type null...");
		ModuleHashTable<M> out = new ModuleHashTable<M>();
		
        T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.values().toArray(modules);
		
		for(int i = 0; i < modules.length; i++) {
			T module = modules[i];
			if(parent.isAssignableFrom(module.getClass())) out.put(module.getName(), (M) module);
		}
		
		return out;
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList, including submodules of Modules, where where parent.isAssignableFrom(sub.getClass()) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing all submodules that are assignable from parent.
	 * @throws IllegalArgumentException If parent is null.
	 */
	@SuppressWarnings("unchecked")
	public <M extends Module> ModuleHashTable<M> getAllSubModulesAssignableTo(Class<M> parent) throws IllegalArgumentException {
		if(parent == null) throw new IllegalArgumentException("Parent type null...");
		ModuleHashTable<M> out = new ModuleHashTable<M>();
		
		//Get modules in table
        T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.values().toArray(modules);
		
		//Iterate through modules
		for(int i = 0; i < modules.length; i++) {
			T module = modules[i];
			
			//Add module to out if assignable
			if(parent.isAssignableFrom(module.getClass())) out.put(module.getName(), (M) module);
			
			//Get module's submodules that are assignable
			ModuleHashTable<M> subModulesAssignable = module.getSubModules().getAllSubModulesAssignableTo(parent);
	        M[] subModules = (M[]) Array.newInstance(Module.class, 0);
			subModules = subModulesAssignable.values().toArray(subModules);
			
			//Iterate through submodules
			for(int j = 0; j < subModules.length; j++) {
				M subModule = subModules[j];
				out.put(subModule.getName(), subModule);
			}
		}
		
		return out;
	}
}
