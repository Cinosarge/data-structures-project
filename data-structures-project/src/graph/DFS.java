package graph;

public class DFS<V, E, I, R> {
	protected Graph<V, E> graph;
	protected Vertex<V> start;
	protected I info;
	protected R visitResult;

	protected static Object STATUS = new Object();
	protected static Object VISITED = new Object();
	protected static Object UNVISITED = new Object();

	protected void visit(DecorablePosition<?> p) {
		p.put(STATUS, VISITED);
	}

	protected void unVisit(DecorablePosition<?> p) {
		p.put(STATUS, UNVISITED);
	}

	protected boolean isVisited(DecorablePosition<?> p) {
		return (p.get(STATUS) == VISITED);
	}

	protected void setup() {

	}

	protected void initResult() {

	}

	protected void startVisit(Vertex<V> v) {

	}

	protected void finishVisit(Vertex<V> v) {

	}

	protected void traverseDiscovery(Edge<E> e, Vertex<V> from) {

	}

	protected void traverseBack(Edge<E> e, Vertex<V> from) {

	}

	protected boolean isDone() {
		return false;
	}

	protected R result() {
		return null;
	}

	protected R finalResult(R r) {
		return r;
	}

	public R execute(Graph<V, E> g, Vertex<V> s, I in) {
		graph = g;
		start = s;
		info = in;

		for (Vertex<V> v : graph.vertices())
			unVisit(v);
		for (Edge<E> e : graph.edges())
			unVisit(e);

		setup();
		return finalResult(dfsTraversal(start));
	}

	protected R dfsTraversal(Vertex<V> v) {
		initResult();
		if (!isDone())
			startVisit(v);
		if (!isDone()) {
			visit(v);
			for (Edge<E> e : graph.incidentEdges(v)) {
				if (!isVisited(e)) {
					visit(e);
					Vertex<V> w = graph.opposite(v, e);
					if (!isVisited(w)) {
						traverseDiscovery(e, v);
						if (isDone())
							break;
						visitResult = dfsTraversal(w);
						if (isDone())
							break;
					} else {
						traverseBack(e, v);
						if (isDone())
							break;
					}
				}
			}
		}
		if (!isDone())
			finishVisit(v);
		return result();
	}

}
