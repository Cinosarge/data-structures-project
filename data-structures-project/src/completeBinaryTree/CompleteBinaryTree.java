package completeBinaryTree;

import binaryTree.BinaryTree;
import nodeList.Position;

public interface CompleteBinaryTree<E> extends BinaryTree<E> {
	
	public Position<E> add(E elem);
	public E remove();

}
