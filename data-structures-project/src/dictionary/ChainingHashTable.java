package dictionary;

import priorityQueue.Entry;
import dictionary.LogFile.MyEntry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.PositionList;

public class ChainingHashTable<K, V> implements Dictionary<K, V> {

	protected int size, capacity;
	protected LogFile<K, V>[] bucket;
	protected int scale, shift;

	public ChainingHashTable() {
		this(1023);
	}

	public ChainingHashTable(int cap) {
		capacity = cap;
		size = 0;
		bucket = (LogFile<K, V>[]) new LogFile[capacity];
		for (int i = 0; i < capacity; i++)
			bucket[i] = new LogFile<K, V>();
		java.util.Random rand = new java.util.Random();
		scale = rand.nextInt(capacity - 1) + 1;
		shift = rand.nextInt(capacity);
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	protected MyEntry<K, V> checkEntry(Entry<K, V> entry) throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyEntry))
			throw new InvalidEntryException("Entry non valida.");
		return (MyEntry<K, V>) entry;
	}

	public int hashValue(K key) {
		return Math.abs(key.hashCode() * scale + shift) % capacity;
	}

	protected void rehash() {
		capacity = 2 * capacity;
		LogFile<K, V>[] old = bucket;
		bucket = (LogFile<K, V>[]) new LogFile[capacity];

		for (int i = 0; i < capacity; i++)
			bucket[i] = new LogFile<K, V>();

		java.util.Random rand = new java.util.Random();
		scale = rand.nextInt(capacity - 1) + 1;
		shift = rand.nextInt(capacity);

		for (int i = 0; i < old.length; i++) {
			LogFile<K, V> logfile = old[i];
			for (Entry<K, V> ent : logfile.entries()) {

				int index = hashValue(ent.getKey());
				bucket[index].insert(ent.getKey(), ent.getValue());
			}
		}
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		int index = hashValue(key);
		return bucket[index].find(key);
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		int index = hashValue(key);
		return bucket[index].findAll(key);
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if (size > capacity * 0.9)
			rehash();
		int index = hashValue(key);
		size++;
		return bucket[index].insert(key, value);
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		int index = hashValue(e.getKey());
		Entry<K, V> rem = bucket[index].remove(e);
		size--;
		return rem;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> elements = new NodePositionList<Entry<K, V>>();

		for (int i = 0; i < capacity; i++)
			if (bucket[i].size() > 0) {
				for (Entry<K, V> pos : bucket[i].entries())
					elements.addLast(pos);
			}

		return elements;
	}
	
	protected void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Chiave non valida.");
	}
	
}
