// O. Bittel;
// 2.8.2023

package Aufgabe2.graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;


/**
 * Klasse zur Analyse von Web-Sites.
 *
 * @author Oliver Bittel
 * @since 30.10.2023
 */
public class AnalyzeWebSite {
    public static void main(String[] args) throws IOException {
        // Graph aus Website erstellen und ausgeben:
        //DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("data/WebSiteKlein");
        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("src/Aufgabe2/data/WebSiteGross");
        System.out.println("Anzahl Seiten: \t" + webSiteGraph.getNumberOfVertexes());
        System.out.println("Anzahl Links: \t" + webSiteGraph.getNumberOfEdges());
        //System.out.println(webSiteGraph);

        // Starke Zusammenhangskomponenten berechnen und ausgeben
        StrongComponents<String> sc = new StrongComponents<>(webSiteGraph);
        System.out.println(sc.numberOfComp());
        //System.out.println(sc);

        // Page Rank ermitteln und Top-100 ausgeben
        pageRank(webSiteGraph);
    }

    /**
     * Liest aus dem Verzeichnis dirName alle Web-Seiten und
     * baut aus den Links einen gerichteten Graphen.
     *
     * @param dirName Name eines Verzeichnis
     * @return gerichteter Graph mit Namen der Web-Seiten als Knoten und Links als gerichtete Kanten.
     */
    private static DirectedGraph buildGraphFromWebSite(String dirName) throws IOException {
        File webSite = new File(dirName);
        DirectedGraph<String> webSiteGraph = new AdjacencyListDirectedGraph();

        for (File f : webSite.listFiles()) {
            String from = f.getName();
            LineNumberReader in = new LineNumberReader(new FileReader(f));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("href")) {
                    String[] s_arr = line.split("\"");
                    String to = s_arr[1];
                    webSiteGraph.addEdge(from, to);
                }
            }
        }
        return webSiteGraph;
    }

    /**
     * pageRank ermittelt Gewichte (Ranks) von Web-Seiten
     * aufgrund ihrer Link-Struktur und gibt sie aus.
     *
     * @param g gerichteter Graph mit Web-Seiten als Knoten und Links als Kanten.
     */
    private static <V> void pageRank(DirectedGraph<V> g) {
        int nI = 10;
        double alpha = 0.5;

        // Definiere und initialisiere rankTable:
        // Ihr Code: ...
        Map<V, Double> rank = new HashMap<>();
        for (V v : g.getVertexSet()) {
            rank.put(v, 1.);
        }
        double a = 0.5;


        double e = 1.;
        while (e > 0.001) {
            for (V w : rank.keySet()) {
                double rw = (1-a);
                double sum = 0.;
                for (V v : g.getPredecessorVertexSet(w)) {
                    sum += rank.get(v) / g.getSuccessorVertexSet(v).size();
                }
                rw += a * sum;
                e = Math.abs(rw - rank.get(w));
                rank.put(w, rw);
            }
        }
        // Iteration:
        // Ihr Code: ...

        // Rank Table ausgeben (nur für data/WebSiteKlein):
        if (rank.size() < 100) {
            for (V v : rank.keySet()) {
                System.out.println(v + ": " + rank.get(v));
            }
        } else {
            // Nach Ranks sortieren Top 100 ausgeben (nur für data/WebSiteGross):

            ArrayList<Map.Entry<V, Double>> li = new ArrayList<>(rank.entrySet());
            li.sort(Comparator.comparing(Map.Entry::getValue));
            for (int i = li.size() - 1; i > li.size() - 101; i--) {
                System.out.println(li.get(i).toString());
            }

            // Top-Seite mit ihren Vorgängern und Ranks ausgeben (nur für data/WebSiteGross):
            System.out.println();
            System.out.println("Top 1. Seite:");
            System.out.println(li.getLast().toString());


        }


        

        
    }
}
