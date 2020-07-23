package queue;

import node.Node;
import exception.EmptyQueueException;

public class NodeQueue<E> implements Queue<E> {

	protected Node<E> head;
	protected Node<E> tail;
	protected int size;

	public NodeQueue() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size==0);
	}
	
	public void enqueue(E element) {
		Node<E> elem = new Node<E>(element, tail);
		if(isEmpty())
			head = elem;
		else
			tail.setNext(elem);
		tail = elem;
		size++;
	}

	public E dequeue() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException("Coda vuota.");
		E elem = head.getElement();
		head = head.getNext();
		size--;
		if (isEmpty())
			tail = null;
		return elem;
	}

	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("Coda vuota.");
		else
			return head.getElement();
	}
	
	public String toString() {
		Node<E> temp = head;
		String s = "[";
		for(int i = 0; i < size() - 1; i++){
			s += temp.getElement() + ", ";
			temp = temp.getNext();
		}
		s += temp.getElement() + "]";
		return s;
	}

}
