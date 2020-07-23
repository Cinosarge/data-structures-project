package priorityQueue;

import java.util.Comparator;
import java.util.Iterator;

import comparator.DefaultComparator;
import exception.EmptyPriorityQueueException;
import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class SortedListPriorityQueue<K, V> implements PriorityQueue<K, V> {

	protected PositionList<Entry<K, V>> entries;
	protected Comparator<K> c; 

	protected static class MyEntry<K, V> implements Entry<K, V> {
		protected K k;
		protected V v;

		public MyEntry(K key, V value) {
			k = key;
			v = value;
		}
		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}
		
		public String toString() {
			return "(" + k  + "," + v + ")";
		}

	}
	
	public int size() {
		return entries.size();
	}

	public boolean isEmpty() {
		return (size()==0);
	}
	
	public SortedListPriorityQueue() {
		entries = new NodePositionList<Entry<K, V>>();
		c = new DefaultComparator<K>();
	}

	public SortedListPriorityQueue(Comparator<K> comp) {
		entries = new NodePositionList<Entry<K, V>>();
		c = comp;
	}

	public Entry<K,V> min () throws EmptyPriorityQueueException {
		if (entries.isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		return entries.first().element();
	}

	public Entry<K,V> insert (K k, V v) throws InvalidKeyException {
		checkKey(k);
		Entry<K,V> entry = new MyEntry<K,V>(k, v);
		insertEntry(entry);
		return entry;
	}

	protected boolean checkKey(K key) {
		boolean result;
		try {
			result = (c.compare(key, key)==0);
		}catch (ClassCastException e){
			throw new InvalidKeyException("Chiave non valida.");
		}
		return result;
	} 

	protected void insertEntry(Entry<K,V> e) {
		if (entries.isEmpty()) {
			entries.addFirst(e);
		}
		else if(c.compare(e.getKey(), entries.last().element().getKey()) > 0)
			entries.addLast(e);
		else{
			Position<Entry<K,V>> curr = entries.first();
			while (c.compare(e.getKey(), curr.element().getKey()) > 0)
				curr = entries.next(curr);
			entries.addBefore(curr, e);
		}
	}

	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		if (entries.isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		return entries.remove(entries.first()); 
	}

}
