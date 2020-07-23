package nodeList;

public class ArrayPosition<E> implements Position<E>{
	
	int index;
	E element;

	public ArrayPosition() {

	}

	public ArrayPosition(int i, E e) {
		index = i;
		element = e;
	}

	public E element(){
		return element;
	}
	
	public int getIndex(){
		return index;
	}
	
	public E setElement(E e){
		E temp = element;
		element = e;
		return temp;
	}
	
	public int setIndex(int i){
		int temp = index;
		index = i;
		return temp;
	}
}
