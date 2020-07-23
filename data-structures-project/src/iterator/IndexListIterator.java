package iterator;

import java.util.Iterator;

import arrayList.ArrayIndexList;
import arrayList.IndexList;
import exception.NoSuchElementException;

public class IndexListIterator<E> implements Iterator<E> {

	private IndexList<E> A;
	private int size;
	private int index = -1;

	public IndexListIterator(E[] B) {
		A = new ArrayIndexList<E>();
		size = B.length;
		for (int i = 0; i < size; i++)
			A.add(i, B[i]);
		if (size > 0) index = 0;
	}

	public boolean hasNext() {
		return (index > -1 && index < size);
	}

	public E next() {
		if(!hasNext()) throw new NoSuchElementException("Nessun ulteriore elemento nell'iteratore.");
		E elem = A.get(index);
		if (index == size - 1) index = -1;
		else index++;
		return elem;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Operazione di rimozione non implementata.");
	}

}
