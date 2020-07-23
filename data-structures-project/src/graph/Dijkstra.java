package graph;

import priorityQueue.Entry;
import comparator.DefaultComparator;
import adaptablePriorityQueue.AdaptablePriorityQueue;
import adaptablePriorityQueue.HeapAdaptablePriorityQueue;

public class Dijkstra<V, E> {

	protected static final Integer INFINITE = Integer.MAX_VALUE;
	protected Graph<V, E> graph;

	protected Object WEIGHT;
	protected Object DIST = new Object();
	protected Object ENTRY = new Object();
	protected AdaptablePriorityQueue<Integer, Vertex<V>> Q;

	public void execute(Graph<V, E> g, Vertex<V> s, Object w) {
		graph = g;
		WEIGHT = w;
		DefaultComparator<E> dc = new DefaultComparator<>();
		Q = new HeapAdaptablePriorityQueue<Integer, Vertex<V>>(dc);
		dijkstraVisit(s);
	}

	public int getDist(Vertex<V> u) {
		return (Integer) u.get(DIST);
	}

	protected void dijkstraVisit(Vertex<V> v) {
		for (Vertex<V> u : graph.vertices()) {
			int u_dist;
			if (u == v)
				u_dist = 0;
			else
				u_dist = INFINITE;
			Entry<Integer, Vertex<V>> u_entry = Q.insert(u_dist, u);
			u.put(ENTRY, u_entry);
		}
		while (!Q.isEmpty()) {
			Entry<Integer, Vertex<V>> u_entry = Q.min();
			Vertex<V> u = u_entry.getValue();
			int u_dist = u_entry.getKey();
			Q.remove(u_entry);
			u.put(DIST, u_dist);
			u.remove(ENTRY);
			if (u_dist == INFINITE)
				continue;
			for (Edge<E> e : graph.incidentEdges(u)) {
				Vertex<V> z = graph.opposite(u, e);
				Entry<Integer, Vertex<V>> z_entry = (Entry<Integer, Vertex<V>>) z.get(ENTRY);
				if (z_entry != null) {
					int e_weight = (Integer) e.get(WEIGHT);
					int z_dist = z_entry.getKey();
					if (u_dist + e_weight < z_dist)
						Q.replaceKey(z_entry, u_dist + e_weight);
				}
			}
		}
	}
}
