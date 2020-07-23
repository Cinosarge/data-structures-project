package node;

public class DLNode<E> {

	private E element;
	private DLNode<E> next;
	private DLNode<E> prev;

	public DLNode() {
		this(null, null, null);
	}

	public DLNode(E e, DLNode<E> p, DLNode<E> n) {
		element = e;
		next = n;
		prev = p;
	}

	public E getElement() {
		return element;
	}

	public DLNode<E> getNext() {
		return next;
	}

	public DLNode<E> getPrev() {
		return prev;
	}

	public void setElement(E newElem) {
		element = newElem;
	}

	public void setNext(DLNode<E> newNext) {
		next = newNext;
	}

	public void setPrev(DLNode<E> newPrev) {
		prev = newPrev;
	}

}
