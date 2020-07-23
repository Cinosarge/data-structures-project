package stack;

import exception.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

	private int capacity;
	private E S[];
	private int top = -1;

	public static final int CAPACITY = 1000;

	public ArrayStack() {
		this(CAPACITY);
	}

	public ArrayStack(int capacity) {
		this.capacity = capacity;
		S = (E[]) new Object[capacity];
	}
	
	public int size() {
		return top + 1;
	}

	public boolean isEmpty() {
		return (size() == 0);
	}
	
	public void push(E element) {
		if(size() == capacity){
			E A[] = (E[]) new Object[2 * capacity];
			for(int i = 0; i < size(); i++){
				A[i] = S[i];
			}
			S = A;
		}
		S[++top] = element;
	}

	public E pop() throws EmptyStackException {
		E elem;
		if(isEmpty())
			throw new EmptyStackException("Stack vuoto.");
		elem = S[top];
		S[top--] = null;
		return elem;
	}

	public E top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("Stack vuoto.");
		return S[top];
	}

	public String toString() {
		String s = "[";
		for(int i = 0; i < size() - 1; i++){
			s += S[i] + ", ";
		}
		s += S[top] + "]";
		return s;
	}
	
	public void union(Stack<E> s) {
		Stack<E> tmp = new ArrayStack<E>(2 * (this.size() + s.size()));
		int i;
		
		for(i = 0; i < this.size(); i++)
			tmp.push(this.pop());
		for(i = 0; i < s.size(); i++)
			tmp.push(s.pop());
		for(i = 0; i < tmp.size(); i++)
			this.push(tmp.pop());
	}
	
}
