package arrayList;

import exception.IndexOutOfBoundException;

public interface IndexList<E> {
	
	public int size();
	public boolean isEmpty();
	
	public E get(int index) throws IndexOutOfBoundException;
	public E set(int index, E element) throws IndexOutOfBoundException;
	public void add(int index, E element) throws IndexOutOfBoundException;
	public E remove(int index) throws IndexOutOfBoundException;

}
