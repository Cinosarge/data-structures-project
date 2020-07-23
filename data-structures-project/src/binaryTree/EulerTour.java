package binaryTree;

import nodeList.Position;

public abstract class EulerTour<E, R> {

	protected BinaryTree<E> tree;

	public abstract R execute(BinaryTree<E> T);

	protected void init(BinaryTree<E> T) {
		tree = T;
	}

	protected R eulerTour(Position<E> v) {
		TourResult<R> r = new TourResult<R>();
		visitLeft(v, r);
		if (tree.hasLeft(v))
			r.left = eulerTour(tree.left(v));
		visitBelow(v, r);
		if (tree.hasRight(v))
			r.right = eulerTour(tree.right(v));
		visitRight(v, r);
		r.out = processNode(v, r);
		return r.out;
	}

	public abstract R processNode(Position<E> v, TourResult<R> r);

	protected void visitLeft(Position<E> v, TourResult<R> r) {

	}

	protected void visitBelow(Position<E> v, TourResult<R> r) {

	}

	protected void visitRight(Position<E> v, TourResult<R> r) {

	}

}
