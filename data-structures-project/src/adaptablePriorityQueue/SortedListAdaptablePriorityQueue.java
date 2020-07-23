package adaptablePriorityQueue;

import java.util.Comparator;

import priorityQueue.Entry;
import priorityQueue.SortedListPriorityQueue;
import exception.EmptyPriorityQueueException;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import nodeList.Position;

public class SortedListAdaptablePriorityQueue<K,V> extends SortedListPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {

	public SortedListAdaptablePriorityQueue() {
		super();
	}

	public SortedListAdaptablePriorityQueue(Comparator comp) {
		super(comp);
	}

	protected static class LocationAwareEntry<K,V> extends MyEntry<K,V> implements Entry<K,V> {

		protected Position<Entry<K,V>> loc; 

		public LocationAwareEntry(K k, V v) { 
			super(k, v);    
		}   

		public LocationAwareEntry(K k, V v, Position pos) {
			super(k, v);    
			loc = pos;   
		}  

		protected Position<Entry<K,V>> location() {   
			return loc;  
		}    

		protected Position<Entry<K,V>> setLocation(Position<Entry<K,V>> pos) {  
			Position<Entry<K,V>> oldPosition = location();     
			loc = pos;     
			return oldPosition;   
		}   

		protected K setKey(K key) {   
			K oldKey = getKey();      
			k = key;     
			return oldKey;  
		}    

		protected V setValue(V value) {  
			V oldValue = getValue(); 
			v = value;      
			return oldValue;    
		}   
	}

	protected LocationAwareEntry<K,V> checkEntry(Entry<K,V> ent) throws InvalidEntryException {
		if(ent == null || !(ent instanceof LocationAwareEntry))
			throw new InvalidEntryException("Entry non valida.");
		return (LocationAwareEntry<K,V>)ent;
	}

	public Entry<K,V> insert (K k, V v) throws InvalidKeyException {
		checkKey(k);
		LocationAwareEntry<K,V> entry = new LocationAwareEntry<K,V>(k,v);
		insertEntry(entry);
		return entry;
	}

	protected void insertEntry(Entry<K, V> e) {
		if (entries.isEmpty()) {
			entries.addFirst(e);
			((LocationAwareEntry<K,V>)e).setLocation(entries.first());
		} else if (c.compare(e.getKey(), entries.last().element().getKey()) > 0) {
			entries.addLast(e);
			((LocationAwareEntry<K,V>)e).setLocation(entries.last());
		} else {
			Position<Entry<K, V>> curr = entries.first();
			while (c.compare(e.getKey(), curr.element().getKey()) > 0)
				curr = entries.next(curr);
			entries.addBefore(curr, e);
			((LocationAwareEntry<K,V>)e).setLocation(entries.prev(curr));
		}
	}

	public Entry<K, V> remove(Entry<K, V> e) {
		LocationAwareEntry<K, V> entry = checkEntry(e);
		Entry<K,V> ret = entries.remove(entry.location());
		entry.setLocation(null);
		return ret;
	}

	public K replaceKey(Entry<K, V> e, K key) throws InvalidKeyException {
		LocationAwareEntry<K,V> a = checkEntry(e);
		K temp = e.getKey();
		a.setKey(key);
		return temp;
	}

	public V replaceValue(Entry<K,V> e, V value) {
		checkEntry(e);
		V oldValue = ((LocationAwareEntry<K,V>) e).setValue(value);
		return oldValue;
	}

}
