package dictionary;

import java.util.Random;

import priorityQueue.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.PositionList;

public class LinearProbingHashTable<K, V> implements Dictionary<K, V> {

	protected Entry<K, V> AVAILABLE;
	protected int size = 0;
	protected int capacity;
	protected Entry<K, V>[] bucket;
	protected long scale, shift;

	class HashEntry<K, V> implements Entry<K, V> {
		protected K key;
		protected V value;

		public HashEntry(K k, V v) {
			key = k;
			value = v;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V val) {
			V oldValue = value;
			value = val;
			return oldValue;
		}

		public boolean equals(Object o) {
			HashEntry<K, V> ent;
			try {
				ent = (HashEntry<K, V>) o;
			} catch (ClassCastException ex) {
				return false;
			}
			return (ent.getKey() == key) && (ent.getValue() == value);
		}
	}

	public LinearProbingHashTable() {
		this(1023);
	}

	public LinearProbingHashTable(int cap) {
		capacity = cap;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		Random rand = new Random();
		scale = rand.nextInt(capacity - 1) + 1;
		shift = rand.nextInt(capacity);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		int i = this.hashValue(key);
		int p = 0;
		Entry<K, V> e = bucket[i];
		do {
			if (e == null)
				break;
			else {
				if (key.equals(bucket[i].getKey()))
					return e;
				else
					i = (i + 1) % capacity;
				p = p + 1;
			}
		} while (p == capacity);
		return null;
	}

	public Iterable<Entry<K, V>> findAll(K k) throws InvalidKeyException {
		checkKey(k);
		PositionList<Entry<K, V>> L = new NodePositionList<Entry<K, V>>();
		int i = hashValue(k);
		int p = 0;
		do {
			Entry<K, V> entry = bucket[i];
			if (entry == null)
				break;
			if (k.equals(entry.getKey()))
				L.addLast(entry);
			i = (i + 1) % capacity;
			p = p + 1;
		} while (p == capacity);
		return null;
	}

	public int findEntry(Entry<K, V> ent) throws InvalidKeyException {
		checkEntry(ent);
		int avail = capacity;
		int i = hashValue(ent.getKey());
		int j = i;
		do {
			Entry<K, V> e = bucket[i];
			if (e == null) {
				if (avail == capacity)
					avail = i;
				return -(avail + 1);
			}
			if (ent.equals(e))
				return i;
			if (e == AVAILABLE) {
				if (avail == capacity)
					avail = i;
			}
			i = (i + 1) % capacity;
		} while (i != j);
		return -(avail + 1);
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if (size / capacity > 0.5) {
			rehash();
		}
		int i = this.hashValue(key);
		int p = 0;
		Entry<K, V> e = bucket[i];
		do {
			if ((e == null) || (e == AVAILABLE)) {
				bucket[i] = new HashEntry<K, V>(key, value);
				return bucket[i];
			}
			i = (i + 1) % capacity;
			p = p + 1;
		} while (p == capacity);
		return null;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> L = new NodePositionList<Entry<K, V>>();
		for (int i = 0; i < capacity; i++)
			L.addLast(bucket[i]);
		return L;
	}

	protected int hashValue(K key) {
		return (int) (Math.abs(key.hashCode() * scale + shift) % capacity);
	}

	protected void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Chiave non valida.");
	}

	protected void checkEntry(Entry<K, V> entry) {
		if ((entry == null) || !(entry instanceof HashEntry))
			throw new InvalidEntryException("Entry non valida.");
	}

	protected void rehash() {
		capacity = 2 * capacity;
		Entry<K, V>[] old = bucket;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		java.util.Random rand = new java.util.Random();
		scale = rand.nextInt(capacity - 1) + 1;
		shift = rand.nextInt(capacity);
		for (int i = 0; i < old.length; i++) {
			Entry<K, V> e = old[i];
			int j = this.hashValue(e.getKey());
			int p = 0;
			do {
				if ((e != null) || (e != AVAILABLE)) {
					bucket[j] = e;
					break;
				} else {
					j = (j + 1) % capacity;
					p++;
				}
			} while (p == capacity);
		}
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		int i = this.hashValue(e.getKey());
		int p = 0;
		Entry<K, V> en;
		do {
			en = bucket[i];
			if (en == null)
				break;
			else {
				if (en.equals(e)) {
					bucket[i] = AVAILABLE;
					size--;
					return en;
				}
			}
			i = (i + 1) % capacity;
			p = p + 1;
		} while (p == capacity);
		return null;
	}

}
