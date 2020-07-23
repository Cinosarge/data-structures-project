package queue;

import exception.EmptyQueueException;

public class ArrayQueue<E> implements Queue<E> {
	
	private int capacity;
	private E Q[];
	private int front = 0;
	private int rear = 0;
	public static final int CAPACITY = 1024;
	
	public ArrayQueue() {
		this(CAPACITY); 
	}
	
	public ArrayQueue(int capacity) {
		this.capacity = capacity;
		Q = (E[]) new Object[capacity];
	}
	
	public int size() {
		return (capacity - front + rear) % capacity;
	}

	public boolean isEmpty() {
		return (front == rear);
	}
	
	public void enqueue(E elem) {
		if(size() == capacity - 1){
			E A[] = (E[]) new Object[2 * capacity];
			for(int i = 0; i < size(); i++){
				A[i] = Q[i];
			}
			Q = A;
		}
		Q[rear] = elem;
		rear = (rear + 1) % capacity;
	}

	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("Coda vuota.");
		E elem = Q[front];
		front = (front + 1) % capacity;
		return elem;
	}

	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("Coda vuota.");
		return Q[front];
	}
	
	public String toString() {
		String s = "[";
		for(int i = front; i < (rear - 1); i++){
			s += Q[i] + ", ";
		}
		s += Q[rear - 1] + "]";
		return s;
	}

}
