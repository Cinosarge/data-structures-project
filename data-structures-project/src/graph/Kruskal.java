package graph;

import nodeList.NodePositionList;
import nodeList.PositionList;
import partition.ListPartition;
import partition.Partition;
import priorityQueue.Entry;
import priorityQueue.HeapPriorityQueue;
import priorityQueue.PriorityQueue;

public class Kruskal<V, E> {
	protected Graph<V, E> graph;
	protected Object WEIGHT;
	protected PositionList<Edge<E>> Elist;
	protected PriorityQueue<Double, Edge<E>> Q;

	public Iterable<Edge<E>> execute(Graph<V, E> g, Object w) {
		graph = g;
		WEIGHT = w;
		Q = new HeapPriorityQueue<Double, Edge<E>>();
		Elist = new NodePositionList<Edge<E>>();
		kruskalAlg();
		return Elist;
	}

	public void kruskalAlg() {
		Partition<Vertex<V>> P = new ListPartition<Vertex<V>>();
		for (Vertex<V> w : graph.vertices())
			P.makeSet(w);
		for (Edge<E> e : graph.edges())
			Q.insert((Double) e.get(WEIGHT), e);
		while (Elist.size() < graph.numVertices() - 1) {
			Entry<Double, Edge<E>> e_entry = Q.removeMin();
			Edge<E> e = e_entry.getValue();
			Vertex<V> endV[] = graph.endVertices(e);
			Vertex<V> u = endV[0];
			Vertex<V> v = endV[1];
			if (P.find(u) != P.find(v)) {
				P.union(P.find(u), P.find(v));
				Elist.addLast(e);
			}
		}
	}

}
