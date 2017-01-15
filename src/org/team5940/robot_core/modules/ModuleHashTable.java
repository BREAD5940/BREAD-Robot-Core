package org.team5940.robot_core.modules;

import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Extension of Hashtable that forces the keys to be their names and provides useful methods. Useful for storing multiple Module s.
 * @author David Boles
 *
 * @param <T> The type of the modules you want to store.
 */
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
	public synchronized <M extends Module> ModuleHashTable<M> getDirectSubModulesAssignableTo(Class<M> parent) throws IllegalArgumentException {
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
	public synchronized <M extends Module> ModuleHashTable<M> getAllSubModulesAssignableTo(Class<M> parent) throws IllegalArgumentException {
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
	
	/**
	 * Gets all top-level modules stored in this hash table.
	 * @return An array with the same type as this with the modules contained in this.
	 */
	public synchronized T[] getDirectSubModulesAsArray() {
		@SuppressWarnings("unchecked")
		T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.getDirectSubModulesAssignableTo(Module.class).values().toArray(modules);
		return modules;
	}
	
	/**
	 * Gets ALL modules stored in this hash table.
	 * @return An array with the same type as this with the modules and all submodules contained in this.
	 */
	public synchronized T[] getAllSubModulesAsArray() {
		@SuppressWarnings("unchecked")
		T[] modules = (T[]) Array.newInstance(Module.class, 0);
		modules = this.getAllSubModulesAssignableTo(Module.class).values().toArray(modules);
		return modules;
	}
	
	//put() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#put(Object, Object)
	 * @see ModuleHashTable#put(Module)
	 */
	@Override
	public synchronized T put(String key, T value) throws IllegalArgumentException {
		if(value.getName().equals(key))
			return super.put(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of put with the key as value.getName().
	 * @see Hashtable#put(Object, Object)
	 */
	public synchronized T put(T value) {
		return super.put(value.getName(), value);
	}
	
	//merge() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#merge(Object, Object, BiFunction)
	 * @see ModuleHashTable#merge(Module, BiFunction)
	 */
	@Override
	public synchronized T merge(String key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) throws IllegalArgumentException {
		if(value.getName().equals(key))
			return super.merge(key, value, remappingFunction);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of merge with the key as value.getName().
	 * @param remappingFunction if wrong key.
	 * @see Hashtable#merge(Object, Object, BiFunction)
	 */
	public synchronized T merge(T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) {
		return this.merge(value.getName(), value, remappingFunction);
	}
	
	//putAll() OVERRIDE
	/**
	 * Throws an IllegalArgumentException if the keys don't aren't the value's .getName()s. Otherwise uses super's implementation.
	 * @throws IllegalArgumentException
	 * @see Hashtable#putAll(Map)
	 */
	@Override
	public synchronized void putAll(Map<? extends String, ? extends T> t) throws IllegalArgumentException {
		AtomicBoolean keysGood = new AtomicBoolean(false);//Should work?
		t.forEach(new KeyCheckBiConsumer(keysGood));
		if(keysGood.get()) super.putAll(t);
		else throw new IllegalArgumentException("A key did not correspond to it's value's name!");
	}
	
	//putIfAbsent() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#putIfAbsent(Object, Object)
	 * @see ModuleHashTable#putIfAbsent(Module)
	 */
	@Override
	public synchronized T putIfAbsent(String key, T value) throws IllegalArgumentException {
		if(value.getName().equals(key))
			return super.putIfAbsent(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of putIfAbsent with the key as value.getName().
	 * @see Hashtable#putIfAbsent(Object, Object)
	 */
	public synchronized T putIfAbsent(T value) {
		return this.putIfAbsent(value.getName(), value);
	}
	
	//replace() OVERRIDEs AND REPLACEMENTs
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#replace(Object, Object)
	 * @see ModuleHashTable#replace(Module)
	 */
	@Override
	public synchronized T replace(String key, T value) throws IllegalArgumentException {
		if(value.getName().equals(key))
			return super.replace(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of replace with the key as value.getName().
	 * @see Hashtable#replace(Object, Object)
	 */
	public synchronized T replace(T value) {
		return this.replace(value.getName(), value);
	}
	
	/**
	 * Throws an IllegalArgumentException if either's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#replace(Object, Object, Object)
	 * @see ModuleHashTable#replace(Module, Module)
	 */
	@Override
	public synchronized boolean replace(String key, T oldValue, T newValue) throws IllegalArgumentException {
		if(oldValue.getName().equals(key) && newValue.getName().equals(key))
			return super.replace(key, oldValue, newValue);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of replace with the key as value.getName().
	 * @throws IllegalArgumentException if both Module-s don't have the same name.
	 * @see Hashtable#replace(Object, Object, Object)
	 */
	public synchronized boolean replace(T oldValue, T newValue) throws IllegalArgumentException {
		if(oldValue.getName().equals(newValue.getName()))
			return super.replace(newValue.getName(), oldValue, newValue);
		else throw new IllegalArgumentException("Names are not the same!");
	}
}

class KeyCheckBiConsumer implements BiConsumer<String, Module> {

	AtomicBoolean previousChecksGood;
	
	public KeyCheckBiConsumer(AtomicBoolean previousChecksGood) {
		this.previousChecksGood = previousChecksGood;
	}
	
	@Override
	public void accept(String t, Module u) {
		if(previousChecksGood.get() && !t.equals(u.getName())) this.previousChecksGood.set(false);
	}
	
}

