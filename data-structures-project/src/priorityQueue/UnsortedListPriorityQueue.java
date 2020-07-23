package priorityQueue;

import java.util.Comparator;
import java.util.Iterator;

import comparator.DefaultComparator;
import node.DNode;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;
import exception.EmptyPriorityQueueException;
import exception.InvalidKeyException;

public class UnsortedListPriorityQueue<K, V> implements PriorityQueue<K, V> {

	private PositionList<Entry<K, V>> entries;
	private Comparator<K> c; 

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
	
	public UnsortedListPriorityQueue () {
		entries = new NodePositionList<Entry<K, V>>();
		c = new DefaultComparator<K>();
	}

	public UnsortedListPriorityQueue(Comparator<K> comp) {
		entries = new NodePositionList<Entry<K, V>>();
		c = comp;
	}

	protected boolean checkKey(K key) {
		boolean result;
		try{
			result = (c.compare(key, key) == 0);
		}catch(ClassCastException e){
			throw new InvalidKeyException("Chiave non valida.");
		}
		return result;
	} 

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(entries.isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		Iterator<Entry<K, V>> it = entries.iterator();
		K min = entries.first().element().getKey();
		Entry<K,V> x = entries.first().element();
		while(it.hasNext()){
			Entry<K,V> temp = it.next();
			K eTemp = temp.getKey();
			if(c.compare(min, eTemp)>0){
				min = eTemp;
				x = temp;
			}
		}
		return x;
	}

	public Entry<K,V> insert (K k, V v) throws InvalidKeyException {
		checkKey(k);
		Entry<K,V> entry = new MyEntry<K,V>(k, v);
		if (entries.isEmpty())
			entries.addFirst(entry);
		else
			entries.addLast(entry);
		return entry;
	}

	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(entries.isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		Iterator<Position<Entry<K, V>>> it = entries.positions().iterator();
		K min = entries.first().element().getKey();
		Entry<K,V> x = entries.first().element();
		Position<Entry<K,V>> posIniz = entries.first();
		while(it.hasNext()){
			Position<Entry<K,V>> posTemp = it.next();
			Entry<K, V> temp = posTemp.element();
			K eTemp = temp.getKey();
			if(c.compare(min,eTemp) > 0){
				min = eTemp;
				x = temp;
				posIniz = posTemp;
			}
		}
		entries.remove(posIniz);
		return x;
	}

}
