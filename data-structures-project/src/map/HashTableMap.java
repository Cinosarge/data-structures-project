package map;

import java.util.Random;

import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.PositionList;
import priorityQueue.Entry;

public class HashTableMap<K, V> implements Map<K, V> {

	private class KeyValueEntry<K, V> implements Entry<K, V> {

		protected K key;
		protected V value;

		public KeyValueEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public String toString() {
			return "[" + key + ", " + value + "]";
		}

		public boolean equals(Object object) {
			KeyValueEntry<K, V> entry;
			try {
				entry = (KeyValueEntry<K, V>) object;
			} catch (ClassCastException e) {
				return false;
			}
			return (entry.getKey() == key) && (entry.getValue() == value);
		}
	}

	private class HashMapEntry<K, V> extends KeyValueEntry<K, V> {

		public HashMapEntry(K key, V value) {
			super(key, value);
		}

		public void setValue(V value) {
			this.value = value;
		}

	}

	private static float LOAD_FACTOR = 0.5f;

	private Entry<K, V> LastAvailable = new HashMapEntry<K, V>(null, null);
	private int size = 0;
	private int numPrimo;
	private int capacity;
	private Entry<K, V>[] bucket;
	private long scale;
	private long shift;
	private Random random = new java.util.Random();

	public HashTableMap() {
		this(31, 100);
	}

	public HashTableMap(int capacity) {
		this(31, capacity);
	}

	public HashTableMap(int numPrim, int capacity) {
		this.numPrimo = numPrim;
		this.capacity = capacity;
		this.bucket = new HashMapEntry[capacity];
		this.scale = random.nextInt(numPrim - 1) + 1;
		this.shift = random.nextInt(numPrim);
	}

	private void checkKey(K key) {
		if (key == null)
			throw new InvalidKeyException(null);
	}

	private int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode() * scale + shift) % numPrimo) % capacity);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new NodePositionList<K>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != LastAvailable))
				keys.addLast(bucket[i].getKey());
		return keys;

	}

	private int findEntry(K key) throws InvalidKeyException {
		int available = -1;
		checkKey(key);
		int i = hashValue(key);
		int j = i;
		do {
			Entry<K, V> e = bucket[i];
			if (e == null) {
				if (available < 0)
					available = i;
				break;
			}

			if (key.equals(e.getKey()))
				return i;

			if (e == LastAvailable) {
				if (available < 0)
					available = i;
			}
			i = (i + 1) % capacity;
		} while (i != j);

		return -(available + 1);
	}

	public V get(K key) throws InvalidKeyException {
		int i = findEntry(key);
		if (i < 0)
			return null;
		return bucket[i].getValue();
	}

	public V put(K key, V value) throws InvalidKeyException {
		int i = findEntry(key);
		if (i >= 0) {
			HashMapEntry<K, V> e = (HashMapEntry<K, V>) bucket[i];
			V oldValue = e.getValue();
			e.setValue(value);
			return oldValue;
		}
		if (size >= capacity * LOAD_FACTOR) {
			rehash();
			i = findEntry(key);
		}
		bucket[-i - 1] = new HashMapEntry<K, V>(key, value);
		size++;
		return null;
	}

	private void rehash() {
		capacity = 2 * capacity;
		Entry<K, V>[] old = bucket;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		scale = random.nextInt(numPrimo - 1) + 1;
		shift = random.nextInt(numPrimo);
		for (int i = 0; i < old.length; i++) {
			Entry<K, V> e = old[i];
			if ((e != null) && (e != LastAvailable)) {
				int j = -1 - findEntry(e.getKey());
				bucket[j] = e;
			}
		}
	}

	public V remove(K key) throws InvalidKeyException {

		int i = findEntry(key);
		if (i < 0)
			return null;
		V toReturn = bucket[i].getValue();
		bucket[i] = LastAvailable;
		size--;
		return toReturn;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> entries = new NodePositionList<Entry<K, V>>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != LastAvailable))
				entries.addLast(bucket[i]);
		return entries;
	}

	public Iterable<V> values() {
		PositionList<V> values = new NodePositionList<V>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != LastAvailable))
				values.addLast(bucket[i].getValue());
		return values;
	}

}
