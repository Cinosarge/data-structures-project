package set;

import nodeList.PositionList;

public class UnionMerge<E> extends Merge<E> {

	protected void aIsLess(E a, PositionList<E> C) {
		C.addLast(a);
	}

	protected void bothAreEqual(E a, E b, PositionList<E> C) {
		C.addLast(a);
	}

	protected void bIsLess(E b, PositionList<E> C) {
		C.addLast(b);
	}

}
