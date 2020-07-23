package arrayList;

import exception.IndexOutOfBoundException;

public class ArrayIndexList<E> implements IndexList<E> {

	protected E[] A;
	protected int size;
	protected int capacity;
	
	protected static final int CAPACITY = 1024;

	public ArrayIndexList() {
		this(CAPACITY);
	}

	public ArrayIndexList(int capacity) {
		this.capacity = capacity;
		size = 0;
		A = (E[]) new Object[capacity];
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	public E get(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		return A[index];
	}

	public E set(int index, E element) throws IndexOutOfBoundException {
		checkIndex(index, size());
		E temp = A[index];
		A[index] = element;
		return temp;
	}

	public void add(int index, E element) throws IndexOutOfBoundException {
		checkIndex(index, size() + 1);
		if(size == capacity){
			E[] tmp = (E[]) new Object[2 * capacity];
			for(int i = 0; i < this.size(); i++)
				tmp[i] = A[i];
			A = tmp;
		}
		for(int i = size - 1; i >= index; i--)
			A[i + 1] = A[i];				
		A[index] = element;
		size++;	
	}

	public E remove(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		E temp = A[index];
		for(int i = index; i < size - 1; i++)
			A[i] = A[i + 1];
		size--;
		return temp;
	}

	public String toString() {
		String s = "[";
		if(!isEmpty()){
			for(int i = 0; i < size - 1; i++)
				s += A[i]+", ";
			s += A[size - 1];
		}
		s += "]";
		return s;
	}
	
	protected void checkIndex(int index, int size) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundException("Indice fuori dai limiti.");
	}
}
