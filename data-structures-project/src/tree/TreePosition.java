package tree;

import nodeList.Position;
import nodeList.PositionList;

public interface TreePosition<E> extends Position<E> {
	
	public void setElement(E o);
	public void setChildren(PositionList<Position<E>> c);
	public void setParent(TreePosition<E> v);
	
	public PositionList<Position<E>> getChildren();
	public TreePosition<E> getParent();
	
}
