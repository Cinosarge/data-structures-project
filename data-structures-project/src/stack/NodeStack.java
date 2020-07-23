package stack;

import node.Node;
import exception.EmptyStackException;

public class NodeStack<E> implements Stack<E> {
	
	protected Node<E> top;
	protected int size;
	
	public NodeStack() {
		top = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	public void push(E elem) {
		Node<E> nodo = new Node<E>(elem, top);
		top = nodo;
		size++;
	}

	public E pop() throws EmptyStackException {
		if (isEmpty())
			throw new EmptyStackException("Stack vuoto.");
		E elem = top.getElement();
		top = top.getNext();
		size--;
		return elem;
	} 

	public E top() throws EmptyStackException {
		 if (isEmpty())
			 throw new EmptyStackException("Stack vuoto.");
		 return top.getElement();
	}
	
	public String toString(){
		Node<E> temp = top;
		String s = "[";
		for(int i = 0; i < size() - 1; i++){
			s += temp.getElement() + ", ";
			temp = temp.getNext();
		}
		s += temp.getElement() + "]";
		return s;
	}

}
