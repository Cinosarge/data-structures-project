package sequence;

import node.DNode;
import nodeList.NodePositionList;
import nodeList.Position;
import exception.BoundaryViolationException;
import exception.EmptyListException;
import exception.EmptySequenceException;
import exception.IndexOutOfBoundException;
import exception.InvalidPositionException;

public class NodeSequence<E> extends NodePositionList<E> implements Sequence<E> {
	
	public E getFirst() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return first().element();
	}

	public E getLast() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return last().element();
	}

	public E removeFirst() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return remove(first());
	}

	public E removeLast() throws EmptySequenceException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return remove(last());
	}

	public Position<E> atIndex(int index) throws BoundaryViolationException {
		checkIndex(index, size());
		DNode<E> nodo;
		if(index <= size() / 2){
			nodo = header.getNext();
			for(int i = 0; i < index; i++){
				nodo = nodo.getNext();
			}
		} else {
			nodo = trailer.getPrev();
			for(int i = 1; i < size() - index; i++){
				nodo = nodo.getPrev();
			}
		}
		return nodo;
	}

	public int indexOf(Position<E> position) throws InvalidPositionException {
		checkPosition(position);
		Position<E> pos = first();
		int index = 0;
		if(position == last())
			return size() - 1;
		while(pos != position){
			pos = next(pos);
			index++;
		}
		return index;
	}

	public Position<E> first() throws EmptyListException {
		try{
			Position<E> toReturn = super.first();
			return toReturn;
		}catch(EmptyListException e){
			throw new EmptySequenceException("Sequenza vuota.");
		}
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptySequenceException("Sequenza vuota.");
		return super.last();
	}

	public E get(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		return atIndex(index).element();
	}

	public E set(int index, E element) throws IndexOutOfBoundException {
		checkIndex(index, size() - 1);
		return set(atIndex(index), element);
	}

	public void add(int index, E element) throws IndexOutOfBoundException {
		if(index == size())
			addLast(element);
		else{
			checkIndex(index, size());
			addBefore(atIndex(index), element);
		}
	}

	public E remove(int index) throws IndexOutOfBoundException {
		checkIndex(index, size());
		return remove(atIndex(index));
	}
	
	public String toString() {
		String s = "[";
		DNode<E> nodo = header.getNext();
		if(!isEmpty()){
			while(nodo != trailer.getPrev()){
				s += nodo.element() + ", ";
				nodo = nodo.getNext();
			}
			s += nodo.element();
		}
		return s += "]";
	}
	
	protected void checkIndex(int index, int size) throws IndexOutOfBoundException {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundException("Indice non valido.");
	}
}
