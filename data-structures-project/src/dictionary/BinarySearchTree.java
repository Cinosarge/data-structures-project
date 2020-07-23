package dictionary;

import java.util.Comparator;

import comparator.DefaultComparator;
import binaryTree.BTNode;
import binaryTree.LinkedBinaryTree;
import priorityQueue.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import exception.InvalidPositionException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class BinarySearchTree<K, V> extends LinkedBinaryTree<Entry<K, V>> implements Dictionary<K, V> {

	protected Comparator<K> C;
	protected int size = 0;
	protected Position<Entry<K, V>> actionPos;

	public BinarySearchTree() {
		C = new DefaultComparator<K>();
		actionPos = addRoot(null);
	}

	public BinarySearchTree(Comparator<K> c) {
		C = c;
		actionPos = addRoot(null);
	}

	protected static class BSTEntry<K, V> implements Entry<K, V> {

		protected K key;
		protected V value;
		protected Position<Entry<K, V>> pos;

		BSTEntry() {

		}

		BSTEntry(K k, V v, Position<Entry<K, V>> p) {
			key = k;
			value = v;
			pos = p;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public Position<Entry<K, V>> position() {
			return pos;
		}

	}

	protected void checkEntry(Entry<K, V> ent) throws InvalidEntryException {
		if (ent == null || !(ent instanceof BSTEntry))
			throw new InvalidEntryException("Entry non valida.");
	}

	private void checkKey(K key) throws InvalidKeyException {
		try {
			C.compare(key, key);
		} catch (Exception e) {
			throw new InvalidKeyException("Chiave non valida.");
		}
	}

	protected Position<Entry<K, V>> treeSearch(K key, Position<Entry<K, V>> pos) {
		if (isExternal(pos))
			return pos;
		else {
			K curKey = key(pos);
			int comp = C.compare(key, curKey);
			if (comp < 0)
				return treeSearch(key, left(pos));
			else if (comp > 0)
				return treeSearch(key, right(pos));
			return pos;
		}
	}

	protected void removeExternal(Position<Entry<K, V>> v) throws InvalidPositionException {
		if (!isExternal(v))
			throw new InvalidPositionException("Impossibile rimuovere un nodo interno.");
		BTNode<Entry<K, V>> p = (BTNode<Entry<K, V>>) parent(v);
		BTNode<Entry<K, V>> s = (BTNode<Entry<K, V>>) sibling(v);
		if (isRoot(p)) {
			s.setParent(null);
			root = s;
		} else {
			BTNode<Entry<K, V>> g = (BTNode<Entry<K, V>>) parent(p);
			if (p == left(g))
				g.setLeft(s);
			else
				g.setRight(s);
			s.setParent(g);
		}
		size = size - 2;
	}

	public Entry<K, V> remove(Entry<K, V> ent) throws InvalidEntryException {
		checkEntry(ent);
		Position<Entry<K, V>> remPos = ((BSTEntry<K, V>) ent).position();
		Entry<K, V> toReturn = ent;
		if (isExternal(left(remPos)))
			remPos = left(remPos);
		else if (isExternal(right(remPos)))
			remPos = right(remPos);
		else {
			Position<Entry<K, V>> swapPos = remPos;
			remPos = right(swapPos);
			do
				remPos = left(remPos);
			while (isInternal(remPos));
			replaceEntry(swapPos, parent(remPos).element());
		}
		removeExternal(remPos);
		size--;
		return toReturn;
	}

	public Entry<K, V> insert(K k, V x) throws InvalidKeyException {
		checkKey(k);
		Position<Entry<K, V>> insPos = treeSearch(k, root());
		while (!isExternal(insPos))
			insPos = treeSearch(k, left(insPos));
		actionPos = insPos;
		return insertAtExternal(insPos, new BSTEntry<K, V>(k, x, insPos));
	}

	protected Entry<K, V> insertAtExternal(Position<Entry<K, V>> v, Entry<K, V> e) {
		expandExternal(v, null, null);
		replace(v, e);
		size++;
		return e;
	}

	public void expandExternal(Position<Entry<K, V>> v, Entry<K, V> l, Entry<K, V> r) throws InvalidPositionException {
		if (!isExternal(v))
			throw new InvalidPositionException("Impossibile effettuare l'operazione su un nodo interno.");
		insertLeft(v, l);
		insertRight(v, r);
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Position<Entry<K, V>> curPos = treeSearch(key, root());
		actionPos = curPos;
		if (isInternal(curPos))
			return entry(curPos);
		return null;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K, V>> L = new NodePositionList<Entry<K, V>>();
		addAll(L, root(), key);
		return L;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> entries = new NodePositionList<Entry<K, V>>();
		Iterable<Position<Entry<K, V>>> positions = positions();
		for (Position<Entry<K, V>> p : positions)
			entries.addLast(p.element());
		return entries;
	}

	protected K key(Position<Entry<K, V>> position) {
		return position.element().getKey();
	}

	protected V value(Position<Entry<K, V>> position) {
		return position.element().getValue();
	}

	protected Entry<K, V> entry(Position<Entry<K, V>> position) {
		return position.element();
	}

	protected void replaceEntry(Position<Entry<K, V>> pos, Entry<K, V> ent) {
		replace(pos, ent);
	}

	protected void addAll(PositionList<Entry<K, V>> L, Position<Entry<K, V>> v, K k) {
		if (isExternal(v))
			return;
		Position<Entry<K, V>> pos = treeSearch(k, v);
		if (isInternal(pos)) {
			addAll(L, left(pos), k);
			L.addLast(pos.element());
			addAll(L, right(pos), k);
		}
	}

}
