package priorityQueue;

import java.util.Comparator;

import comparator.DefaultComparator;
import completeBinaryTree.ArrayListCompleteBinaryTree;
import completeBinaryTree.CompleteBinaryTree;
import exception.EmptyPriorityQueueException;
import exception.InvalidKeyException;
import nodeList.Position;

public class HeapPriorityQueue<K, V> implements PriorityQueue<K, V> {

	protected CompleteBinaryTree<Entry<K, V>> heap;
	protected Comparator<K> comp;

	protected static class MyEntry<K, V> implements Entry<K, V> {
		protected K key;
		protected V value;

		public MyEntry(K k, V v) {
			key = k;
			value = v;
		}

		public K getKey(){
			return key;
		}

		public V getValue(){
			return value;
		}
		
		public String toString(){
			return "("  +key + "," + value + ")";
		}

	}
	
	public int size() {
		return heap.size();
	}


	public boolean isEmpty() {
		return heap.size()==0;
	}
	
	public HeapPriorityQueue() {
		heap = new ArrayListCompleteBinaryTree<Entry<K, V>>();
		comp = new DefaultComparator<K>();
	}

	public HeapPriorityQueue(Comparator<K> c) {
		heap = new ArrayListCompleteBinaryTree<Entry<K, V>>();
		comp = c;
	}

	public HeapPriorityQueue(K k[], V v[], Comparator<K> c){
		heap = new ArrayListCompleteBinaryTree<Entry<K, V>>();
		comp = c;
		if(k.length != v.length)
			throw new RuntimeException("Array di dimensioni diverse.");
		for(int i = 0; i < k.length; i++) {
			insert(k[i], v[i]);
		}
	}

	protected void checkKey(K key) throws InvalidKeyException{
		try{
			comp.compare(key,key);
		}
		catch(Exception e){
			throw new InvalidKeyException("Chiave non valida.");
		}
	}

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		return heap.root().element();
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> entry = new MyEntry<K, V>(key, value);
		upHeap(heap.add(entry));
		return entry;
	}

	public void upHeap(Position<Entry<K, V>> v){
		Position<Entry<K,V>> u;
		while(!heap.isRoot(v)){
			u = heap.parent(v);
			if(comp.compare(u.element().getKey(), v.element().getKey()) <= 0)
				break;
			swap(u, v);
			v = u;
		}
	}

	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("Coda prioritaria vuota.");
		Entry<K,V> min = heap.root().element();
		if(size() == 1)
			heap.remove();
		else{
			heap.replace(heap.root(), heap.remove());
			downHeap(heap.root());
		}
		return min;
	}

	public void downHeap(Position<Entry<K, V>> r) {
		while(heap.isInternal(r)){
			Position<Entry<K, V>> s;
			if(!heap.hasRight(r)) 
				s = heap.left(r);
			else if(comp.compare(heap.left(r).element().getKey(), heap.right(r).element().getKey()) <= 0)
				s = heap.left(r);
			else
				s=heap.right(r);
			if(comp.compare(s.element().getKey(), r.element().getKey()) < 0){
				swap(r, s);
				r = s;
			}
			else break;
		}
	}

	public void swap(Position<Entry<K, V>> x, Position<Entry<K, V>> y) {
		Entry<K,V> temp = x.element();
		heap.replace(x, y.element());
		heap.replace(y, temp);
	}

}
