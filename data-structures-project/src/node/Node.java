package node;

public class Node<E> {
	private E elem;
	private Node<E> next;

	public Node() {
		this(null, null);
	}
	
	public Node(E elem, Node<E> next) {
		this.elem = elem;
		this.next = next;
	}
	
	public E getElement() {
		return elem;
	}
	
	public Node<E> getNext() {
		return next;
	}
	
	public void setElement(E elem) {
		this.elem = elem;
	}
	
	public void setNext(Node<E> next) {
		this.next = next;
	}
}
