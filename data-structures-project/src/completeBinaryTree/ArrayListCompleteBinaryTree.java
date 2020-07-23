package completeBinaryTree;

import java.util.Iterator;

import arrayList.ArrayIndexList;
import arrayList.IndexList;
import exception.BoundaryViolationException;
import exception.EmptyTreeException;
import exception.InvalidPositionException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class ArrayListCompleteBinaryTree<E> implements CompleteBinaryTree<E> {

	IndexList<BTPos<E>> T;

	protected static class BTPos<E> implements Position<E> {
		E element;
		int index;

		public BTPos(E elt, int i) {
			element = elt;
			index = i;
		}

		public E element() {
			return element;
		}

		public int index() {
			return index;
		}

		public E setElement(E elt) {
			E temp = element;
			element = elt;
			return temp;
		}
	}

	public ArrayListCompleteBinaryTree() {
		T = new ArrayIndexList<BTPos<E>>();
		T.add(0, null);
	}

	protected BTPos<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if (v == null || !(v instanceof BTPos))
			throw new InvalidPositionException("Posizione non valida.");
		return (BTPos<E>) v;
	}

	public Position<E> left(Position<E> v) throws BoundaryViolationException {
		if (!hasLeft(v))
			throw new BoundaryViolationException("Figlio sinistro non esistente.");
		BTPos<E> vv = checkPosition(v);
		return T.get(vv.index() * 2);
	}

	public Position<E> right(Position<E> v) throws BoundaryViolationException {
		if (!hasRight(v))
			throw new BoundaryViolationException("Figlio destro non esistente.");
		BTPos<E> vv = checkPosition(v);
		return T.get(vv.index() * 2 + 1);
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPos<E> vv = checkPosition(v);
		return 2 * vv.index() <= size();
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPos<E> vv = checkPosition(v);
		return 1 + 2 * vv.index() <= size();
	}

	public int size() {
		return T.size() - 1;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		for (int i = 0; i < T.size(); i++)
			positions.addLast(T.get(i));
		return positions;
	}

	public Position<E> root() throws EmptyTreeException {
		if (isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		return T.get(1);
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		if (isRoot(v))
			throw new BoundaryViolationException("La radice non ha un nodo padre.");
		BTPos<E> vv = checkPosition(v);
		return T.get(vv.index() / 2);
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> children = new NodePositionList<Position<E>>();
		if (hasLeft(v))
			children.addLast(left(v));
		if (hasRight(v))
			children.addLast(right(v));
		return children;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		return hasLeft(v);
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !isInternal(v);
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPos<E> vv = checkPosition(v);
		return vv.index() == 1;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPos<E> vv = checkPosition(v);
		return vv.setElement(e);
	}

	public Iterator<E> iterator() {
		NodePositionList<E> list = new NodePositionList<E>();
		for (int i = 1; i < T.size(); i++)
			list.addLast(T.get(i).element());
		return list.iterator();
	}

	public Position<E> add(E elem) {
		int i = size() + 1;
		BTPos<E> p = new BTPos<E>(elem, i);
		T.add(i, p);
		return p;
	}

	public E remove() throws EmptyTreeException {
		if (isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		return T.remove(size()).element();
	}

}
