package graph;

public class ComponentsDFS<V, E> extends DFS<V, E, Object, Integer> {
	protected Integer compNumber;
	protected Object COMPONENT = new Object();

	protected void setup() {
		compNumber = 1;
	}

	protected void startVisit(Vertex<V> v) {
		v.put(COMPONENT, compNumber);
	}

	protected Integer finalResult(Integer dfsResult) {
		for (Vertex<V> v : graph.vertices())
			if (v.get(STATUS) == UNVISITED) {
				compNumber += 1;
				dfsTraversal(v);
			}

		return compNumber;
	}

}
