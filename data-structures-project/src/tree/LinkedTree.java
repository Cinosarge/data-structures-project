package tree;

import java.util.Iterator;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;
import exception.BoundaryViolationException;
import exception.EmptyTreeException;
import exception.InvalidPositionException;
import exception.NonEmptyTreeException;
import exception.UndeletableNodeException;

public class LinkedTree<E> implements Tree<E> {

	protected TreePosition<E> root;
	protected int size;

	public LinkedTree(){
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	protected TreePosition<E> checkPosition(Position<E> v) {
		if ((v == null) || !(v instanceof TreePosition))
			throw new InvalidPositionException("Posizione non valida.");
		return (TreePosition<E>) v;
	}

	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new NodePositionList<E>();
		for(Position<E> pos : positions)
			elements.addLast(pos.element());
		return elements.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if(size != 0) preorderPositions(root, positions);
		return positions;
	}

	public void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
		checkPosition(v);
		pos.addLast(v);
		if(isInternal(v))
			for(Position<E> w : children(v))
				preorderPositions(w,pos);
	}

	public Position<E> root() throws EmptyTreeException {
		if(isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		return root;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TreePosition<E> vv = checkPosition(v);
		if(isRoot(v))
			throw new BoundaryViolationException("La radice non ha un nodo padre."); 
		return vv.getParent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		if(isExternal(v))
			throw new InvalidPositionException("Un nodo esterno non ha figli.");
		return vv.getChildren();
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		return !isExternal(v);
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		return (vv.getChildren() == null) || (vv.getChildren().isEmpty());
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return (v == root());
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		E temp = vv.element();
		vv.setElement(e);
		return temp;
	}

	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if(!isEmpty())
			throw new NonEmptyTreeException("Albero non vuoto.");
		size = 1;
		root = new TreeNode<E>(e, null, null);
		return root;
	}

	public Position<E> addChild(E e, Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> ww = new TreeNode<E>(e,vv,null);
		PositionList<Position<E>> children = vv.getChildren();
		if(children == null){
			children = new NodePositionList<Position<E>>();
			vv.setChildren(children);
		}
		children.addLast(ww);
		size++;
		return ww;
	}

	public E removeRoot() throws EmptyTreeException, UndeletableNodeException {
		if(isEmpty())
			throw new EmptyTreeException("Albero vuoto.");
		if(size > 1)
			throw new UndeletableNodeException("L'albero contiene più di un nodo.");
		TreePosition<E> pos = checkPosition(root);
		E value = pos.element();
		pos.setElement(null);
		pos.setParent(null);
		size--;
		return value;
	}

	public E removeExternalChild(Position<E> v) throws UndeletableNodeException, InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		Position<E> children = vv.getChildren().first().element();
		if(isExternal(v))
			throw new InvalidPositionException("Posizione non valida.");
		if(isInternal(children))
			throw new UndeletableNodeException("Impossibile rimuovere il figlio: non e' un nodo esterno.");
		return remove(children);
	}

	public E remove(Position<E> v) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("Albero vuoto.");
		else{
			TreePosition<E> pos = checkPosition(v);
			E value = pos.element();
			if(isExternal(v)){
				PositionList<Position<E>> parChildList = pos.getParent().getChildren();
				parChildList.remove(parChildList.first());
				pos.setElement(null);
				pos.setParent(null);
				size--;
			}
			return value;
		}
	}

}
