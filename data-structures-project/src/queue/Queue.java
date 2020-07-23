package queue;

import exception.EmptyQueueException;

public interface Queue<E> {

	public int size();
	public boolean isEmpty();
	
	public void enqueue (E element);
	public E dequeue() throws EmptyQueueException;
	public E front() throws EmptyQueueException;
	 
}
