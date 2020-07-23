package binaryTree;

import java.util.Iterator;

import exception.BoundaryViolationException;
import exception.EmptyTreeException;
import exception.InvalidPositionException;
import exception.NonEmptyTreeException;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class LinkedBinaryTree <E> implements BinaryTree <E> {

	protected int size;
	protected BTPosition<E> root;

	public LinkedBinaryTree() {
		size = 0;
		root = null;
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public BTPosition<E> checkPosition(Position<E> v) {
		if(v == null || !(v instanceof BTPosition))
			throw new InvalidPositionException("Posizione non valida.");
		return (BTPosition<E>) v;
	}
	
	public Iterator<E> iterator() {
		PositionList<E> elements = new NodePositionList<E>();
		for(Position<E> pos: positions())
			elements.addLast(pos.element());
		return elements.iterator();
	} 

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if(size != 0)
			preorderPositions(root(), positions);
		return positions;
	} 

	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
		pos.addLast(v);
		if (hasLeft(v))
			preorderPositions(left(v), pos);
		if (hasRight(v))
			preorderPositions(right(v), pos);
	}

	public Position<E> root() throws EmptyTreeException {
		if(isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		return root;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		if(isRoot(v))
			throw new BoundaryViolationException("La radice non ha un nodo padre."); 
		return vv.getParent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> children = new NodePositionList<Position<E>>();
		if(hasLeft(v))
			children.addLast(left(v));
		if(hasRight(v))
			children.addLast(right(v));
		return children;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (hasLeft(vv) || hasRight(vv));
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !isInternal(v);
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (vv == root);
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		E temp = vv.element();
		vv.setElement(e);
		return temp;
	}

	public Position<E> left(Position<E> v) throws BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		if (leftPos == null)
			throw new BoundaryViolationException("Nessun figlio sinistro.");
		return leftPos;
	}

	public Position<E> right(Position<E> v) throws BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> rightPos = vv.getRight();
		if(rightPos == null)
			throw new BoundaryViolationException("Nessun figlio destro.");
		return rightPos;
	}


	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return vv.getLeft()!= null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return vv.getRight()!= null;
	}

	public BTPosition<E> addRoot(E e) throws NonEmptyTreeException {
		if(!isEmpty())
			throw new NonEmptyTreeException("Albero non vuoto.");
		size = 1;
		root = new BTNode<E>(e, null, null, null);
		return root;
	}

	public BTPosition<E> insertLeft(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		if(hasLeft(v))
			throw new InvalidPositionException("Figlio sinistro gia' esistente.");
		BTPosition<E> newNode = new BTNode<E>(e, vv, null, null);
		vv.setLeft(newNode);
		size++;
		return newNode;
	}

	public BTPosition<E> insertRight(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		if(hasRight(v))
			throw new InvalidPositionException("Figlio destro gia' esistente.");
		BTPosition<E> newNode = new BTNode<E>(e, vv, null, null);
		vv.setRight(newNode);
		size++;
		return newNode;
	}

	public void attachLeaves(PositionList<E> L) {
		if(isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		for(Position<E> pos: positions()){
			if(isExternal(pos)) {
				BTPosition<E> left = new BTNode<E>(L.first().element(), (BTPosition<E>) pos, null, null);
				((BTPosition<E>) pos).setLeft(left);
				L.remove(L.first());
				BTPosition<E> right = new BTNode<E>(L.first().element(), (BTPosition<E>) pos, null, null);
				((BTPosition<E>) pos).setRight(right);
				L.remove(L.first());
			}
		}
	}

	public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();	
		if (parentPos != null) {
			BTPosition<E> sibPos;
			BTPosition<E> leftPos = parentPos.getLeft();
			if (leftPos == vv)
				sibPos = parentPos.getRight();
			else
				sibPos = parentPos.getLeft();
			if (sibPos != null)
				return sibPos;
		}
		throw new BoundaryViolationException("Nodo fratello non esistente.");
	}

	public E remove(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rightPos = vv.getRight();
		if (leftPos != null && rightPos != null) throw new InvalidPositionException("Posizione non valida.");
		BTPosition<E> ww;
		
		if (leftPos != null)
			ww = leftPos;
		else if (rightPos != null)
			ww = rightPos;
		else
			ww = null;
		
		if (vv == root){
			if (ww != null)
				ww.setParent(null);
			root = ww;
		}
		else{ 
			BTPosition<E> uu = vv.getParent();
			if (vv == uu.getLeft())
				uu.setLeft(ww);
			else
				uu.setRight(ww);
			if(ww != null)
				ww.setParent(uu);
		}
		size--;
		return v.element();
	}
	
	public String toString() {
		String s = positions().toString();
		return s;
	}

}
