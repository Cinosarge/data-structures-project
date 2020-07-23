package nodeList;

import iterator.ElementIterator;
import java.util.Iterator;
import exception.BoundaryViolationException;
import exception.EmptyListException;
import exception.InvalidPositionException;
import node.DNode;

public class NodePositionList<E> implements PositionList<E> {
	
	protected DNode<E> header;
	protected DNode<E> trailer;
	protected int size;
	
	public NodePositionList() {
		size = 0;
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(header, null, null);
		header.setNext(trailer);
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("PositionList vuota.");
		return header.getNext();
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("PositionList vuota.");
		return trailer.getPrev();
	}

	public Position<E> prev(Position<E> pos) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(pos);
		DNode<E> prev = nodo.getPrev();
		if(prev == header)
			throw new BoundaryViolationException("Nessun elemento precedente.");
		return prev;
	}

	public Position<E> next(Position<E> pos) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(pos);
		DNode<E> next = nodo.getNext();
		if(next == trailer)
			throw new BoundaryViolationException("Nessun elemento successivo.");
		return next;
	}

	public Position<E> addBefore(Position<E> pos, E elem) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(pos);
		size++;
		DNode<E> newNode = new DNode<E>(nodo.getPrev(), nodo, elem);
		nodo.getPrev().setNext(newNode);
		nodo.setPrev(newNode);
		return newNode;
	}

	public Position<E> addAfter(Position<E> pos, E elem) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(pos);
		size++;
		DNode<E> newNode = new DNode<E>(nodo, nodo.getNext(), elem);
		nodo.getNext().setPrev(newNode);
		nodo.setNext(newNode);
		return newNode;
	}

	public void addFirst(E elem) {
		DNode<E> fist = new DNode<E>(header, header.getNext(), elem);
		fist.getNext().setPrev(fist);
		header.setNext(fist);
		size++;
	}

	public void addLast(E elem) {
		DNode<E> last = new DNode<E>(trailer.getPrev(), trailer, elem);
		last.getPrev().setNext(last);
		trailer.setPrev(last);
		size++;
	}

	public E remove(Position<E> pos) throws InvalidPositionException {
		DNode<E> oldNode = checkPosition(pos);
		E tmp = oldNode.element();
		oldNode.getPrev().setNext(oldNode.getNext());
		oldNode.getNext().setPrev(oldNode.getPrev());
		oldNode.setNext(null);
		oldNode.setPrev(null);
		oldNode.setElement(null);
		size--;
		return tmp;
	}

	public E set(Position<E> pos, E elem) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(pos);
		E tmp = nodo.element();
		nodo.setElement(elem);
		return tmp;
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
	
	public void reverse(Position<E> p){
		if(p == last())
			return;
		reverse(next(p));
		E first = remove(p);
		addLast(first);
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
	
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	
	protected DNode<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		if(pos == null)
			throw new InvalidPositionException("Posizione null.");
		if(pos == header)
			throw new InvalidPositionException("Posizione riservata.");
		if(pos == trailer)
			throw new InvalidPositionException("Posizione riservata.");
		
		try {
			
			DNode<E> temp = (DNode<E>) pos;
			if(temp.getPrev() == null || temp.getNext() == null)
				throw new InvalidPositionException("Posizione non valida per il tipo di dato corrente.");
			return temp;
			
		}catch(ClassCastException e){
			throw new InvalidPositionException("Posizione non compatibile per il tipo di dato corrente.");
		}
	}
}
