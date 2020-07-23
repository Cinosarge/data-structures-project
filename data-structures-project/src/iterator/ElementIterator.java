package iterator;

import java.util.Iterator;

import exception.NoSuchElementException;
import nodeList.Position;
import nodeList.PositionList;

public class ElementIterator<E> implements Iterator<E> {

	protected PositionList<E> list;
	protected Position<E> cursor = null;

	public ElementIterator(){

	}

	public ElementIterator(PositionList<E> L) {
		list = L;
		if (!list.isEmpty())
			cursor = list.first();
	}

	public boolean hasNext() {
		return (cursor != null);
	}

	public E next() throws NoSuchElementException {
		if (!hasNext())
			throw new NoSuchElementException("Nessun ulteriore elemento nell'iteratore.");
		E toReturn = cursor.element();
		if (cursor == list.last())
			cursor = null;
		else
			cursor = list.next(cursor);
		return toReturn;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Operazione di rimozione non implementata.");
	}
	
}
