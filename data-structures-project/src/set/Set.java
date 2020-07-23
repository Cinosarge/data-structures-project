package set;

public interface Set<E> {
	
	public int size();
	public boolean isEmpty();
	
	public Set<E> union(Set<E> B);
	public Set<E> intersect(Set<E> B);
	public Set <E>subtract(Set <E> B);
	
	public Set<E> fastUnion(Set<E> B);
	public E fastInsert(E x);
	
}
