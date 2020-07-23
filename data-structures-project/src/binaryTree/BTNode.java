package binaryTree;

public class BTNode<E> implements BTPosition<E> {

	private E element;
	private BTPosition<E> left, right, parent;

	public BTNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
		setElement(element);
		setParent(parent);
		setLeft(left);
		setRight(right);
	}

	public E element() {
		return element;
	}

	public BTPosition<E> getParent() {
		return parent;
	}

	public BTPosition<E> getLeft() { 
		return left; 
	}

	public BTPosition<E> getRight() { 
		return right; 
	}

	public void setElement(E o) {
		element=o;
	}

	public void setParent(BTPosition<E> v) { 
		parent=v; 
	}

	public void setLeft(BTPosition<E> v) {
		left=v; 
	}

	public void setRight(BTPosition<E> v) {
		right=v; 
	}
	
	public String toString() {
		return element.toString();
	}

}
