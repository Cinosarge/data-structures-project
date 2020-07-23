package adaptablePriorityQueue;

import exception.InvalidKeyException;
import priorityQueue.Entry;
import priorityQueue.PriorityQueue;

public interface AdaptablePriorityQueue<K, V> extends PriorityQueue<K, V> {
	
	public Entry<K, V> remove(Entry<K, V> e);
	public K replaceKey(Entry<K, V> e, K key) throws InvalidKeyException;  
	public V replaceValue(Entry<K, V> e, V value);
	
}
