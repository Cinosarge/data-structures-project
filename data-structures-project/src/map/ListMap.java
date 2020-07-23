package map;

import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;
import priorityQueue.Entry;

public class ListMap<K, V> implements Map<K, V> {

	private PositionList<Entry<K, V>> L;

	protected static class MyEntry<K, V> implements Entry<K, V> {
		protected K key;
		protected V value;

		public MyEntry(K k, V e) {
			key = k;
			value = e;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public String toString() {
			return ("(" + key + "," + value + ")");
		}
	}

	public ListMap() {
		L = new NodePositionList<Entry<K, V>>();
	}

	public int size() {
		return L.size();
	}

	public boolean isEmpty() {
		return L.isEmpty();
	}

	public void checkKey(K key) {
		if (key == null)
			throw new InvalidKeyException("Chiave non valida.");
	}

	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		for (Position<Entry<K, V>> p : L.positions()) {
			Entry<K, V> e = p.element();
			if (e.getKey().equals(key))
				return e.getValue();
		}
		return null;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		for (Position<Entry<K, V>> p : L.positions()) {
			Entry<K, V> e = p.element();
			if (e.getKey().equals(key)) {
				V v = p.element().getValue();
				L.set(p, new MyEntry<K, V>(key, value));
				return v;
			}
		}
		L.addLast(new MyEntry<K, V>(key, value));
		return null;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		for (Position<Entry<K, V>> p : L.positions()) {
			Entry<K, V> e = p.element();
			if (e.getKey().equals(key)) {
				V v = p.element().getValue();
				L.remove(p);
				return v;
			}
		}
		return null;
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new NodePositionList<K>();
		Iterable<Entry<K, V>> it = entries();
		for (Entry<K, V> a : it)
			keys.addLast(a.getKey());
		return keys;
	}

	public Iterable<V> values() {
		PositionList<V> values = new NodePositionList<V>();
		Iterable<Entry<K, V>> it = entries();
		for (Entry<K, V> a : it)
			values.addLast(a.getValue());
		return values;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> entries = new NodePositionList<Entry<K, V>>();
		if (!L.isEmpty()) {
			Position<Entry<K, V>> current = L.first();
			while (true) {
				entries.addLast(current.element());
				if (current == L.last())
					break;
				current = L.next(current);
			}
		}
		return entries;
	}

}
