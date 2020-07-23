package graph;

import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class FindPathDFS<V, E> extends DFS<V, E, Vertex<V>, Iterable<Position>> {

	protected PositionList<Position> path;
	protected boolean done;

	public void setup() {
		path = new NodePositionList<Position>();
		done = false;
	}

	protected void startVisit(Vertex<V> v) {
		path.addLast(v);
		if (v == info)
			done = true;
	}

	protected void finlshVisit(Vertex<V> v) {
		path.remove(path.last());
		if (!path.isEmpty())
			path.remove(path.last());
	}

	protected void traverseDiscovery(Edge<E> e, Vertex<V> from) {
		path.addLast(e);
	}

	protected boolean isDone() {
		return done;
	}

	public Iterable<Position> finalResult(Iterable<Position> r) {
		return path;
	}

}
