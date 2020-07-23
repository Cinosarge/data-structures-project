package partition;

import comparator.DefaultComparator;

import map.ListMap;
import map.Map;
import nodeList.NodePositionList;
import nodeList.PositionList;
import set.OrderedListSet;
import set.Set;

public class ListPartition<E> implements Partition<E> {

	PositionList<Set<E>> partition;
	Map<E, Set<E>> element; // permette di effettuare la find(p)

	public ListPartition() {
		partition = new NodePositionList<Set<E>>();
		element = new ListMap<E, Set<E>>();
	}

	public int size() {
		return partition.size();
	}

	public boolean isEmpty() {
		return partition.isEmpty();
	}

	public Set<E> makeSet(E x) {
		OrderedListSet<E> L = new OrderedListSet<E>(x, new DefaultComparator<E>());
		element.put(x, L);
		partition.addLast(L);
		L.setLocation(partition.last());
		return L;
	}

	public Set<E> union(Set<E> A, Set<E> B) {
		if (A.size() < B.size()) {
			A.fastUnion(B);
			return A;
		} else {
			B.fastUnion(A);
			return B;
		}
	}

	public Set<E> find(E x) {
		return element.get(x);
	}

}
