package partition;

import set.Set;

public interface Partition <E> { 

	public int size(); 
	public boolean isEmpty();
	
	public Set<E> makeSet(E x); 
	public Set <E>union(Set<E> A, Set<E> B);
	public Set <E> find(E x);

}
