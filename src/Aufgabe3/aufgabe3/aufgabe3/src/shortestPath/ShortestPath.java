// O. Bittel;
// 25.3.2021; jetzt mit IndexMinPq
// 30.06.2024; Anpassung auf ungerichtete Graphen

package Aufgabe3.aufgabe3.aufgabe3.src.shortestPath;


import Aufgabe3.aufgabe3.aufgabe3.src.sim.SYSimulation;
import Aufgabe3.aufgabe3.aufgabe3.src.undirectedGraph.UndirectedGraph;

import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 30.06.2024
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; 		// Distanz für jeden Knoten
	Map<V,V> pred; 				// Vorgänger für jeden Knoten
	IndexMinPQ<V,Double> cand; 	// Kandidaten als PriorityQueue PQ
	// ...
	UndirectedGraph<V> g;
	Heuristic<V> h;
	boolean shortestPathFound;
	V startSave;
	V goalSave;


	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(UndirectedGraph<V> g, Heuristic<V> h) {
		dist = new HashMap<>();
		pred = new HashMap<>();
		cand = new IndexMinPQ<>();
		this.g = g;
		this.h = h;
		this.shortestPathFound = false;
		startSave = null;
		goalSave = null;
		// ...
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public boolean searchShortestPath(V s, V g) {
		// ...
		for (V v : this.g.getVertexSet()) {
			dist.put(v, Double.MAX_VALUE);
			pred.put(v, null);
		}
		dist.put(s, 0.);
		cand.add(s, 0 + h.estimatedCost(s, g));

		while (!cand.isEmpty()) {
			V v = cand.removeMin();
			if (v == g) {
				this.shortestPathFound = true;
				this.startSave = s;
				this.goalSave = g;
				return true;
			}

			for (V w : this.g.getNeighborSet(v)) {
				if (dist.get(w) == Double.MAX_VALUE) {
					pred.put(w, v);
					dist.put(w, dist.get(v) + this.g.getWeight(v, w));
					cand.add(w, dist.get(w) + h.estimatedCost(w, g));
				} else if (dist.get(v) + this.g.getWeight(v, w) < dist.get(w)) {
					pred.put(w, v);
					dist.put(w, dist.get(v) + this.g.getWeight(v, w));
					cand.change(w, dist.get(w) + h.estimatedCost(w, g));
				}
			}
		}
		return false;
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		// ...
		if (!shortestPathFound) {
			throw new IllegalArgumentException();
		}
		List<V> res = new LinkedList<>();
		res.add(this.goalSave);
		V cur = this.goalSave;
		while (cur != this.startSave) {
			cur = pred.get(cur);
			res.addFirst(cur);
		}
		res.addFirst(startSave);
		return res;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		// ...
		if (!shortestPathFound) {
			throw new IllegalArgumentException();
		}
		return dist.get(this.goalSave);
	}

}
