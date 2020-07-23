package deque;

import node.DLNode;
import node.Node;
import exception.EmptyDequeException;

public class NodeDeque<E> implements Deque<E> {
	
	protected DLNode<E> header;
	protected DLNode<E> trailer;
	protected int size;
	
	public NodeDeque() {
		header = new DLNode<E>();
		trailer = new DLNode<E>();
		header.setNext(trailer);
		trailer.setPrev(header);
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size()==0);
	}
	
	public void addFirst(E element) {
		DLNode<E> second = header.getNext();
		DLNode<E> first = new DLNode<E>(element, header, second);
		second.setPrev(first);
		header.setNext(first);
		size++;
	}

	public void addLast(E element) {
		DLNode<E> beforeLast = trailer.getPrev();
		DLNode<E> last = new DLNode<E>(element, beforeLast, trailer);
		beforeLast.setNext(last);
		trailer.setPrev(last);
		size++;
	}

	public E removeFirst() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("La coda doppia e' vuota.");
		DLNode<E> first = header.getNext();
		E tmp = first.getElement();
		DLNode<E> second = first.getNext();
		header.setNext(second);
		second.setPrev(header);
		size--;
		return tmp;
	}

	public E removeLast() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("La coda doppia e' vuota.");
		DLNode<E> ultimo = trailer.getPrev();
		E temp = ultimo.getElement();
		DLNode<E> penultimo = ultimo.getPrev();
		trailer.setPrev(penultimo);
		penultimo.setNext(trailer);
		size--;
		return temp;
	}

	public E getFirst() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("La coda doppia e' vuota.");
		return header.getNext().getElement();
	}

	public E getLast() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("La coda doppia e' vuota.");
		return trailer.getPrev().getElement();
	}
	
	public String toString() {
		DLNode<E> temp = header.getNext();
		String s = "[";
		for(int i = 0; i < size() - 1; i++){
			s += temp.getElement() + ", ";
			temp = temp.getNext();
		}
		s += temp.getElement() + "]";
		return s;
	}

}
