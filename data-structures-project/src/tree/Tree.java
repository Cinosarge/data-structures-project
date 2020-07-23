package tree;

import exception.BoundaryViolationException;
import exception.EmptyTreeException;
import exception.InvalidPositionException;
import nodeList.Position;

public interface Tree<E> extends Iterable<E> {
	
	public int size();
	public boolean isEmpty();
	public Iterable<Position<E>> positions();
	
	public Position<E> root() throws EmptyTreeException;
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException;
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException;
	
	public boolean isInternal(Position<E> v) throws InvalidPositionException;
	public boolean isExternal(Position<E> v) throws InvalidPositionException;
	public boolean isRoot(Position<E> v) throws InvalidPositionException;
	
	public E replace(Position<E> v, E e) throws InvalidPositionException;

}
