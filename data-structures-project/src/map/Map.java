package map;

import priorityQueue.Entry;
import exception.InvalidKeyException;

public interface Map<K,V> {
	
	public int size();
	public boolean isEmpty();
	
	public V get(K key) throws InvalidKeyException;
	public V put(K keys, V values) throws InvalidKeyException;
	public V remove(K key) throws InvalidKeyException;
	public Iterable<K> keys();
	public Iterable<V> values();
	public Iterable<Entry<K,V>> entries();

}
