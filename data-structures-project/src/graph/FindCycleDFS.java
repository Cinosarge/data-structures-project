package graph;

import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;

public class FindCycleDFS<V, E> extends DFS<V, E, Object, Iterable<Position>> {

	protected PositionList<Position> cycle;
	protected boolean done;
	protected Vertex<V> cycleStart;

	public void setup() {
		cycle = new NodePositionList<Position>();
		done = false;
	}

	protected void startVisit(Vertex<V> v) {
		cycle.addLast(v);
	}

	protected void finishVisit(Vertex<V> v) {
		cycle.remove(cycle.last());
		if (!cycle.isEmpty())
			cycle.remove(cycle.last());
	}

	protected void traverseDiscovery(Edge<E> e, Vertex<V> from) {
		cycle.addLast(e);
	}

	protected void traverseBack(Edge<E> e, Vertex<V> from) {
		cycle.addLast(e);
		cycleStart = graph.opposite(from, e);
		cycle.addLast(cycleStart);
		done = true;
	}

	protected boolean isDone() {
		return done;
	}

	public Iterable<Position> finalResult(Iterable<Position> r) {
		if (!cycle.isEmpty()) {
			for (Position<Position> p : cycle.positions()) {
				if (p.element() == cycleStart)
					break;
				cycle.remove(p);
			}
		}
		return cycle;
	}

}
