package graph;

import map.HashTableMap;
import nodeList.NodePositionList;
import nodeList.Position;
import nodeList.PositionList;
import exception.InvalidKeyException;
import exception.InvalidPositionException;
import priorityQueue.Entry;

public class AdjacencyListGraph<V, E> implements Graph<V, E> {

	protected NodePositionList<Vertex<V>> VList;
	protected NodePositionList<Edge<E>> EList;

	public AdjacencyListGraph() {
		VList = new NodePositionList<Vertex<V>>();
		EList = new NodePositionList<Edge<E>>();
	}

	protected class MyPosition<T> extends HashTableMap<Object, Object> implements DecorablePosition<T> {
		protected T elem;

		public T element() {
			return elem;
		}

		public void setElement(T o) {
			elem = o;
		}
	}

	protected class MyVertex<V> extends MyPosition<V> implements Vertex<V> {
		protected PositionList<Edge<E>> incEdges;
		protected Position<Vertex<V>> loc;

		MyVertex(V o) {
			elem = o;
			incEdges = new NodePositionList<Edge<E>>();
		}

		public int degree() {
			return incEdges.size();
		}

		public Iterable<Edge<E>> incidentEdges() {
			return incEdges;
		}

		public Position<Edge<E>> insertIncidence(Edge<E> e) {
			incEdges.addLast(e);
			return incEdges.last();
		}

		public void removeIncidence(Position<Edge<E>> p) {
			incEdges.remove(p);
		}

		public Position<Vertex<V>> location() {
			return loc;
		}

		public void setLocation(Position<Vertex<V>> p) {
			loc = p;
		}

	}

	protected class MyEdge<E> extends MyPosition<E> implements Edge<E> {
		protected MyVertex<V>[] endVertices;
		protected Position<Edge<E>>[] Inc;
		protected Position<Edge<E>> loc;

		MyEdge(Vertex<V> v, Vertex<V> w, E o) {
			elem = o;
			endVertices = (MyVertex<V>[]) new MyVertex[2];
			endVertices[0] = (MyVertex<V>) v;
			endVertices[1] = (MyVertex<V>) w;
			Inc = (Position<Edge<E>>[]) new Position[2];
		}

		public MyVertex<V>[] endVertices() {
			return endVertices;
		}

		public Position<Edge<E>>[] incidences() {
			return Inc;
		}

		public void setIncidences(Position<Edge<E>> pv, Position<Edge<E>> pw) {
			Inc[0] = pv;
			Inc[1] = pw;
		}

		public Position<Edge<E>> location() {
			return loc;
		}

		public void setLocation(Position<Edge<E>> p) {
			loc = p;
		}

	}

	public int numVertices() {
		return VList.size();
	}

	public int numEdges() {
		return EList.size();
	}

	protected MyPosition checkPosition(Position p) throws InvalidPositionException {
		if (p == null || !(p instanceof MyPosition))
			throw new InvalidPositionException("Posizione non valida.");
		return (MyPosition) p;
	}

	private MyVertex<V> checkVertex(Vertex<V> v) {
		if (v == null || !(v instanceof MyVertex))
			throw new InvalidPositionException("Vertice non valido.");
		return (MyVertex<V>) v;
	}

	private MyEdge<E> checkEdge(Edge<E> e) {
		if (e == null || !(e instanceof MyEdge))
			throw new InvalidPositionException("Lato non valido.");
		return (MyEdge<E>) e;
	}

	public int degree(Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> vv = checkVertex(v);
		return vv.degree();
	}

	public Vertex<V>[] endVertices(Edge<E> e) throws InvalidPositionException {
		MyEdge<E> ee = checkEdge(e);
		return ee.endVertices;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidPositionException {
		checkVertex(v);
		MyEdge<E> ee = checkEdge(e);
		Vertex<V>[] endv = ee.endVertices();
		if (v == endv[0])
			return endv[1];
		else if (v == endv[1])
			return endv[0];
		else
			throw new InvalidPositionException("Il vertice non e' un punto terminale dell'arco.");
	}

	public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidPositionException {
		Iterable<Edge<E>> iterToSearch;
		if (degree(u) < degree(v)) {
			iterToSearch = incidentEdges(u);
		} else {
			iterToSearch = incidentEdges(v);
		}
		for (Edge<E> e : iterToSearch) {
			Vertex<V>[] endV = endVertices(e);
			if ((endV[0] == u && endV[1] == v) || (endV[0] == v && endV[1] == u))
				return true;
		}
		return false;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> vv = checkVertex(v);
		return vv.incidentEdges();
	}

	public Iterable<Vertex<V>> vertices() {
		return VList;
	}

	public Iterable<Edge<E>> edges() {
		return EList;
	}

	public V replace(Vertex<V> p, V o) throws InvalidPositionException {
		V temp = p.element();
		MyVertex<V> vv = checkVertex(p);
		vv.setElement(o);
		return temp;
	}

	public E replace(Edge<E> p, E o) throws InvalidPositionException {
		E temp = p.element();
		MyEdge<E> ee = checkEdge(p);
		ee.setElement(o);
		return temp;
	}

	public Vertex<V> insertVertex(V o) {
		MyVertex<V> vv = new MyVertex<V>(o);
		VList.addLast(vv);
		Position<Vertex<V>> p = VList.last();
		vv.setLocation(p);
		return vv;
	}

	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o) throws InvalidPositionException {
		MyVertex<V> uu = checkVertex(u);
		MyVertex<V> vv = checkVertex(v);
		MyEdge<E> ee = new MyEdge<E>(u, v, o);
		Position<Edge<E>> pu = uu.insertIncidence(ee);
		Position<Edge<E>> pv = vv.insertIncidence(ee);
		ee.setIncidences(pu, pv);
		EList.addLast(ee);
		Position<Edge<E>> pe = EList.last();
		ee.setLocation(pe);
		return ee;
	}

	public V removeVertex(Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> vv = checkVertex(v);
		Iterable<Edge<E>> iterToSearch = incidentEdges(v);
		for (Edge<E> e : iterToSearch)
			if (((MyEdge<E>) e).location() != null)
				removeEdge(e);
		VList.remove(vv.location());
		return v.element();
	}

	public E removeEdge(Edge<E> e) throws InvalidPositionException {
		MyEdge<E> ee = checkEdge(e);
		MyVertex<V>[] endv = ee.endVertices();
		Position<Edge<E>>[] inc = ee.incidences();
		endv[0].removeIncidence(inc[0]);
		endv[1].removeIncidence(inc[1]);
		EList.remove(ee.location());
		ee.setLocation(null);
		return e.element();
	}

}
