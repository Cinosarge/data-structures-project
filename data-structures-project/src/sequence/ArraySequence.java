package sequence;

import iterator.ElementIterator;
import nodeList.ArrayPosition;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

import java.util.Iterator;

import exception.BoundaryViolationException;
import exception.EmptyListException;
import exception.EmptySequenceException;
import exception.IndexOutOfBoundException;
import exception.InvalidPositionException;

public class ArraySequence<E> implements Sequence<E> {

	protected ArrayPosition<E>[] A;
	protected int capacity;
	protected int size = 0;
	
	public static final int CAPACITY = 1024;
	
	public ArraySequence() {
		this(CAPACITY);
	}

	public ArraySequence(int capacity) {
		A = (ArrayPosition<E>[]) new ArrayPosition[capacity];
		this.capacity = capacity;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public Position<E> atIndex(int index) throws BoundaryViolationException {
		checkIndex(index, size());
		return A[index];
	}

	public int indexOf(Position<E> position) throws InvalidPositionException {
		ArrayPosition<E> pos = checkPosition(position);
		return pos.getIndex();
	}

	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("Sequenza vuota.");
		return A[0];
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("Sequenza vuota.");
		return A[size() - 1];
	}

	public Position<E> prev(Position<E> pos) throws InvalidPositionException, BoundaryViolationException {
		checkPosition(pos);
		int index = indexOf(pos);
		if(index == 0)
			throw new BoundaryViolationException("Nessun elemento precedente.");
		return A[index - 1];
	}

	public Position<E> next(Position<E> pos) throws InvalidPositionException, BoundaryViolationException {
		checkPosition(pos);
		int index = indexOf(pos);
		if(index == size() - 1)
			throw new BoundaryViolationException("Nessun elemento successivo.");
		return A[index + 1];
	}

	public Position<E> addBefore(Position<E> pos, E elem) throws InvalidPositionException {
		checkPosition(pos);
		int index = indexOf(pos);
		add(index, elem);
		return A[index];
	}

	public Position<E> addAfter(Position<E> pos, E elem) throws InvalidPositionException {
		checkPosition(pos);
		int index = indexOf(pos);
		add(index + 1, elem);
		return A[index + 1];
	}

	public void addFirst(E elem) {
		add(0, elem);
	}

	public void addLast(E elem) {
		add(size(), elem);
	}

	public E remove(Position<E> pos) throws InvalidPositionException {
		checkPosition(pos);
		int index = indexOf(pos);
		return remove(index);
	}

	public E set(Position<E> pos, E elem) throws InvalidPositionException {
		checkPosition(pos);
		int index = indexOf(pos);
		return set(index,elem);
	}

	public E get(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		return A[index].element();
	}

	public E set(int index, E element) throws IndexOutOfBoundException {
		checkIndex(index, size());
		E temp = A[index].element();
		A[index].setElement(element);
		return temp;
	}

	public void add(int index, E element) throws IndexOutOfBoundException {
		checkIndex(index, size()+1);
		if(size == capacity) {
			ArrayPosition<E>[] B = (ArrayPosition<E>[]) new ArrayPosition[2 * capacity];
			for(int i = 0; i < size; i++)
				B[i] = A[i];
			A = B;
		}
		for(int i = size - 1; i >= index; i--){
			A[i+1] = A[i];
			A[i+1].setIndex(i + 1);
		}
		A[index] = new ArrayPosition<E>(index, element);
		size++;
	}

	public E remove(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		E temp = A[index].element();
		for(int i = index; i < size - 1; i++){
			A[i] = A[i + 1];
			A[i].setIndex(i);
		}
		size--;
		return temp;
	}

	public E getFirst() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return A[0].element();
	}

	public E getLast() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return A[size - 1].element();
	}

	public E removeFirst() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return remove(0);
	}

	public E removeLast() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return remove(size - 1);
	}

	public String toString() {
		int size = size();
		String s = "[";
		if(!isEmpty()){
			for(int i = 0; i < size - 1; i++)
				s += get(i) + ", ";
			s += get(size - 1);
		}
		s += "]";
		return s;
	}

	public Iterable<Position<E>> positions() {
		PositionList <Position<E>> toReturn = new NodePositionList<Position<E>>();		
		if(!isEmpty()){
			Position<E> current = first();
			for(int i = 0; i < size() - 1; i++){
				toReturn.addLast(current);
				current = next(current);
			}
			toReturn.addLast(last());
		}
		return toReturn;
	}

	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}
	
	protected void checkIndex(int index, int size) throws IndexOutOfBoundException {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundException("Indice non valido.");
	}
	
	protected ArrayPosition<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		if(pos == null)
			throw new InvalidPositionException("Posizione null.");
		try{
			ArrayPosition<E> a = (ArrayPosition<E>) pos;
			return a;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Posizione non compatibile.");
		}
	}

}
