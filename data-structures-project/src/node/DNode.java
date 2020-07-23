package node;

import exception.InvalidPositionException;
import nodeList.Position;

public class DNode<E> implements Position<E> {
	
	private E element;
	private DNode<E> next;
	private DNode<E> prev;
	
	public DNode(DNode<E> prev, DNode<E> next, E elem) {
		this.prev = prev;
		this.next = next;
		this.element = elem;
	}

	public E element() throws InvalidPositionException {
		if((prev == null) || (next == null))
			throw new InvalidPositionException("Posizione non valida.");
		return element;
	}
	
	public DNode<E> getNext() {
		return next;
	}

	public DNode<E> getPrev() {
		return prev;
	}

	public void setElement(E elem) {
		element = elem;
	}

	public void setNext(DNode<E> newNext) {
		next = newNext;
	}

	public void setPrev(DNode<E> newPrev) {
		prev = newPrev;
	}

}
