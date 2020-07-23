package dictionary;

import exception.InvalidEntryException;
import exception.InvalidKeyException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;
import priorityQueue.Entry;

public class LogFile<K, V> implements Dictionary<K, V> {

	protected PositionList<Entry<K, V>> logFile;

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

	public LogFile() {
		logFile = new NodePositionList<Entry<K, V>>();
	}

	public int size() {
		return logFile.size();
	}

	public boolean isEmpty() {
		return logFile.isEmpty();
	}

	protected void checkKey(K k) throws InvalidKeyException {
		if (k == null)
			throw new InvalidKeyException("Chiave non valida.");
	}

	protected Entry<K, V> checkEntry(Entry<K, V> k) throws InvalidEntryException {
		if (k == null || !(k instanceof MyEntry))
			throw new InvalidEntryException("Entry non valida.");
		return (MyEntry<K, V>) k;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		for (Position<Entry<K, V>> pos : logFile.positions()) {
			Entry<K, V> p = pos.element();
			if (p.getKey().equals(key)) {
				return p;
			}
		}
		return null;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K, V>> elem = new NodePositionList<Entry<K, V>>();
		for (Position<Entry<K, V>> pos : logFile.positions()) {
			Entry<K, V> p = pos.element();
			if (p.getKey().equals(key)) {
				Entry<K, V> e = new MyEntry<K, V>(p.getKey(), p.getValue());
				elem.addLast(e);
			}
		}
		return elem;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K, V> k = new MyEntry<K, V>(key, value);
		logFile.addLast(k);
		return k;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		for (Position<Entry<K, V>> pos : logFile.positions()) {
			Entry<K, V> k = pos.element();
			if (k.getKey().equals(e.getKey()) && k.getValue().equals(e.getValue()))
				return logFile.remove(pos);
		}
		return null;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> L = new NodePositionList<Entry<K, V>>();
		for (Position<Entry<K, V>> pos : logFile.positions()) {
			Entry<K, V> e = new MyEntry<K, V>(pos.element().getKey(), pos.element().getValue());
			L.addLast(e);
		}
		return L;
	}

}
