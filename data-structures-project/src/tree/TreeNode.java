package tree;

import nodeList.Position;
import nodeList.PositionList;

public class TreeNode<E> implements TreePosition<E> {
	
	private E elem;
	private TreePosition<E> parent;
	private PositionList<Position<E>> children;
	
	public TreeNode() {
		
	}
	
	public TreeNode(E element, TreePosition<E> parent, PositionList<Position<E>> children) {
		setElement(element);
		setParent(parent);
		setChildren(children);
	}
	
	public E element() {
		return elem;
	}

	public void setElement(E o) {
		elem = o;
	}

	public void setChildren(PositionList<Position<E>> c) {
		children = c;
	}
	
	public void setParent(TreePosition<E> v) {
		parent = v;
	}
	
	public PositionList<Position<E>> getChildren() {
		return children;
	}
	
	public TreePosition<E> getParent() {
		return parent;
	}
	
	public String toString() {
		return elem.toString();
	}

}
