package org.team5940.robot_core.modules;

import java.util.Hashtable;
import java.util.function.Consumer;

public class ModuleHashTable extends Hashtable<String, Module> {//TODO add generics so can specify extention of Module
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
	public ModuleHashTable(Module[] initModules) {
		super();
		for(int i = 0; i < initModules.length; i++) {
			Module module = initModules[i];
			this.put(module.getName(), module);
		}
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing direct submodules that are assignable from parent.
	 */
	public ModuleHashTable getDirectSubModulesAssignableFrom(Class<? extends Module> parent) {
		ModuleHashTable out = new ModuleHashTable();
		
		this.values().forEach(new Consumer<Module>() {

			@Override
			public void accept(Module t) {
				if(t.getClass().isAssignableFrom(parent)) out.put(t.getName(), t);
			}
			
		});
		
		return out;
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList, including submodules of Modules, where .getClass().isAssignableFrom(parent) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing all submodules that are assignable from parent.
	 */
	public ModuleHashTable getAllSubModulesAssignableFrom(Class<? extends Module> parent) {
		ModuleHashTable out = new ModuleHashTable();
		
		this.values().forEach(new Consumer<Module>() {

			@Override
			public void accept(Module t) {
				if(t.getClass().isAssignableFrom(parent)) out.put(t.getName(), t);
				ModuleHashTable subsAssignable = t.getSubModules().getAllSubModulesAssignableFrom(parent);
				subsAssignable.values().forEach(new Consumer<Module>() {

					@Override
					public void accept(Module t) {
						out.put(t.getName(), t);
					}
					
				});
			}
			
		});
		
		return out;
	}
}