package model;

import java.util.*;

/**
 * IMyMap is an interface implementation of the Map class.
 * It will provide the necessary methods for any Map classes
 * to include in their implementation.<p>
 * 
 * This interface also includes the Entry inner class.
 * 
 * @author Andrew
 *
 * @param <K>
 * @param <V>
 */
public interface IMyMap<K,V> {

	/** Remove all of the entries from this map */
	public void clear();
	
	/** Return true if the specified key is in the map */
	public boolean containsKey(K key);

	/** Return true if this map contains the specified value */
	public boolean containsValue(V value);
	
	/** Return a set of entries in the map */
	public Set<Entry<K,V>> entrySet();
	
	/** Return the value that matches the specified key */
	public V get(K key);
	
	/** Return true if this map doesn't contain any entries */
	public boolean isEmpty();
	
	/** Return a set consisting of the keys in this map */
	public Set<K> keySet();
	
	/** Add an entry for the specified key */
	public V put(K key, V value);
	
	/** Remove an entry for the specified key */
	public void remove(K key);
	
	/** Return the number of mappings in this map */
	public int size();
	
	/** Return a set consisting of the values in this map */
	public Set<V> values();
	
	
	/** Define an inner class for Entry */
	@SuppressWarnings("javadoc")
	public class Entry<K,V>{
		
		K key;
		V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return "[" + key + ", " + value + "]";
		}
	}
}
