package set;

import java.util.Comparator;

import comparator.DefaultComparator;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class OrderedListSet<E> implements Set<E> {

	Comparator c;
	PositionList<E> L;
	protected Position<Set<E>> loc;

	public OrderedListSet() {
		L = new NodePositionList<E>();
		c = new DefaultComparator();
	}

	public OrderedListSet(E x, Comparator<E> c) {
		L = new NodePositionList<E>();
		this.c = c;
		L.addFirst(x);
	}

	public int size() {
		return L.size();
	}

	public boolean isEmpty() {
		return (L.isEmpty());
	}

	public Set<E> union(Set<E> B) {
		PositionList<E> C = new NodePositionList<E>();
		Merge<E> op = new UnionMerge<E>();
		op.merge(L, ((OrderedListSet<E>) B).L, c, C);
		L = C;
		return this;
	}

	public Set<E> intersect(Set<E> B) {
		PositionList<E> C = new NodePositionList<E>();
		Merge<E> op = new IntersectMerge<E>();
		op.merge(L, ((OrderedListSet<E>) B).L, c, C);
		L = C;
		return this;
	}

	public Set<E> subtract(Set<E> B) {
		PositionList<E> C = new NodePositionList<E>();
		Merge<E> op = new SubtractMerge<E>();
		op.merge(L, ((OrderedListSet<E>) B).L, c, C);
		L = C;
		return this;
	}

	public Set<E> fastUnion(Set<E> B) {
		PositionList<E> blist = ((OrderedListSet<E>) B).getSet();
		while (!blist.isEmpty())
			L.addFirst(blist.remove(blist.last()));
		return this;
	}

	public PositionList<E> getSet() {
		return L;
	}

	public E fastInsert(E x) {
		L.addLast(x);
		return x;
	}

	public Position<Set<E>> location() {
		return loc;
	}

	public void setLocation(Position<Set<E>> p) {
		loc = p;
	}

}
