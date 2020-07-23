package sequence;

import arrayList.IndexList;
import exception.BoundaryViolationException;
import exception.EmptySequenceException;
import exception.InvalidPositionException;
import nodeList.Position;
import nodeList.PositionList;

public interface Sequence<E> extends PositionList<E>, IndexList<E> {

	public E getFirst() throws EmptySequenceException;
	public E getLast() throws EmptySequenceException;
	public E removeFirst() throws EmptySequenceException;
	public E removeLast() throws EmptySequenceException;
	
	public Position<E> atIndex(int index) throws BoundaryViolationException;
	public int indexOf(Position <E> position) throws InvalidPositionException;

}
