package org.team5940.robot_core.modules;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * This is the recommended method for storing multiple Module-s. They (the values) are accessible by their names (the keys). All applicable Hashtable methods are overriden to ensure that the name of the module is its key.
 * @author David Boles
 *
 * @param <T> The type of the modules you want to store.
 */
public class ModuleHashtable<T extends Module> extends Hashtable<String, T> {
	private static final long serialVersionUID = 2L;

	/**
	 * Creates a new, empty ModuleHashTable.
	 */
	public ModuleHashtable() {
		super();
	}
	
	public ModuleHashtable(T initModule) throws IllegalArgumentException {
		super();
		if(initModule == null) throw new IllegalArgumentException("Initialized with null argument!");
		this.put(initModule);
	}
	
	/**
	 * Creates a new ModuleList with initModules and the keys them.getName().
	 * @param initModules The modules to put in this ModuleList.
	 * @throws IllegalArgumentException If initModules is null.
	 */
	public ModuleHashtable(T[] initModules) throws IllegalArgumentException {
		super();
		if(initModules == null) throw new IllegalArgumentException("Initialized with null argument!");
		for(T module : initModules) {
			if(module == null) throw new IllegalArgumentException("Initialized with null contained!");
			this.put(module);
		}
	}
	
	/**
	 * Creates a new ModuleList with initModules and the keys them.getName().
	 * @param initModules The modules to put in this ModuleList.
	 * @throws IllegalArgumentException If initModules is null.
	 */
	public ModuleHashtable(Collection<? extends T> initModules) throws IllegalArgumentException {
		super();
		if(initModules == null) throw new IllegalArgumentException("Initialized with null argument!");
		for(T module : initModules) {
			if(module == null) throw new IllegalArgumentException("Initialized with null contained!");
			this.put(module);
		}
	}
	
	/**
	 * Gets all of the Modules contained in this ModuleList where parent.isAssignableFrom(sub.getClass()) returns true.
	 * @param parent The class that you want Modules to be assignable from. 
	 * @return A ModuleList containing direct submodules that are assignable from parent.
	 * @throws IllegalArgumentException if parent is null.
	 */
	@SuppressWarnings("unchecked")
	public synchronized <M extends Module> ModuleHashtable<M> getAssignableTo(Class<M> parent) throws IllegalArgumentException {
		if(parent == null) throw new IllegalArgumentException("Parent type null...");
		ModuleHashtable<M> out = new ModuleHashtable<M>();
		
		for(T module : this.values()) {
			if(parent.isAssignableFrom(module.getClass())) out.put(module.getModuleName(), (M) module);
		}
		
		return out;
	}
	
	//put() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#put(Object, Object)
	 * @see ModuleHashtable#put(Module)
	 */
	@Override
	public synchronized T put(String key, T value) throws IllegalArgumentException {
		if(value.getModuleName().equals(key))
			return super.put(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of put with the key as value.getName().
	 * @see Hashtable#put(Object, Object)
	 */
	public synchronized T put(T value) {
		return super.put(value.getModuleName(), value);
	}
	
	/**
	 * Uses {@link ModuleHashtable#put(Module)} but returns this so that you can chain it.
	 * @param value The module to put in this.
	 * @return this for chaining.
	 */
	public synchronized ModuleHashtable<T> chainPut(T value) {
		this.put(value);
		return this;
	}
	
	//merge() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#merge(Object, Object, BiFunction)
	 * @see ModuleHashtable#merge(Module, BiFunction)
	 */
	@Override
	public synchronized T merge(String key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) throws IllegalArgumentException {
		if(value.getModuleName().equals(key))
			return super.merge(key, value, remappingFunction);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of merge with the key as value.getName().
	 * @param remappingFunction if wrong key.
	 * @see Hashtable#merge(Object, Object, BiFunction)
	 */
	public synchronized T merge(T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) {
		return this.merge(value.getModuleName(), value, remappingFunction);
	}
	
	//putAll() OVERRIDE
	/**
	 * Throws an IllegalArgumentException if the keys don't aren't the value's .getName()s. Otherwise uses super's implementation.
	 * @throws IllegalArgumentException If the keys don't match up.
	 * @see Hashtable#putAll(Map)
	 */
	@Override
	public synchronized void putAll(Map<? extends String, ? extends T> t) throws IllegalArgumentException {
		for(Map.Entry<? extends String, ? extends T> entry : t.entrySet()) {
			if(!entry.getValue().getModuleName().equals(entry.getKey())) throw new IllegalArgumentException("A key did not correspond to it's value's name!");
		}
		super.putAll(t);
	}
	
	/**
	 * Uses {@link ModuleHashtable#putAll(Map)} but returns this so that you can chain it.
	 * @param t The modules to put in this.
	 * @return this for chaining.
	 * @throws IllegalArgumentException If the keys don't match up.
	 */
	public synchronized ModuleHashtable<T> chainPutAll(Map<? extends String, ? extends T> t) throws IllegalArgumentException {
		this.putAll(t);
		return this;
	}
	
	//putIfAbsent() OVERRIDE AND REPLACEMENT
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#putIfAbsent(Object, Object)
	 * @see ModuleHashtable#putIfAbsent(Module)
	 */
	@Override
	public synchronized T putIfAbsent(String key, T value) throws IllegalArgumentException {
		if(value.getModuleName().equals(key))
			return super.putIfAbsent(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of putIfAbsent with the key as value.getName().
	 * @see Hashtable#putIfAbsent(Object, Object)
	 */
	public synchronized T putIfAbsent(T value) {
		return this.putIfAbsent(value.getModuleName(), value);
	}
	
	//replace() OVERRIDEs AND REPLACEMENTs
	/**
	 * Throws an IllegalArgumentException if value's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#replace(Object, Object)
	 * @see ModuleHashtable#replace(Module)
	 */
	@Override
	public synchronized T replace(String key, T value) throws IllegalArgumentException {
		if(value.getModuleName().equals(key))
			return super.replace(key, value);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of replace with the key as value.getName().
	 * @see Hashtable#replace(Object, Object)
	 */
	public synchronized T replace(T value) {
		return this.replace(value.getModuleName(), value);
	}
	
	/**
	 * Throws an IllegalArgumentException if either's .getName() doesn't .equal(key). Otherwise uses super's implementation.
	 * @throws IllegalArgumentException if wrong key.
	 * @see Hashtable#replace(Object, Object, Object)
	 * @see ModuleHashtable#replace(Module, Module)
	 */
	@Override
	public synchronized boolean replace(String key, T oldValue, T newValue) throws IllegalArgumentException {
		if(oldValue.getModuleName().equals(key) && newValue.getModuleName().equals(key))
			return super.replace(key, oldValue, newValue);
		else throw new IllegalArgumentException("Key is not Module's name!");
	}
	
	/**
	 * Uses this' implementation of replace with the key as value.getName().
	 * @throws IllegalArgumentException if both Module-s don't have the same name.
	 * @see Hashtable#replace(Object, Object, Object)
	 */
	public synchronized boolean replace(T oldValue, T newValue) throws IllegalArgumentException {
		if(oldValue.getModuleName().equals(newValue.getModuleName()))
			return super.replace(newValue.getModuleName(), oldValue, newValue);
		else throw new IllegalArgumentException("Names are not the same!");
	}
}

