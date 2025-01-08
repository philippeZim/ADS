import java.util.*;

public class TelNet {

    public record TelKnoten(int x, int y) {}

    public record TelVerbindung(TelKnoten u, TelKnoten v, int c) {}

    int lbg;

    HashSet<TelKnoten> knoten;
    PriorityQueue<TelVerbindung> kanten;
    LinkedList<TelVerbindung> minSpanTree;

    public TelNet(int lbg) {
        this.lbg = lbg;
        knoten = new HashSet<>();
        kanten = new PriorityQueue<>(Comparator.comparing(TelVerbindung::c));
        minSpanTree = null;
    }

    private int manhattanDistanz(TelKnoten k1, TelKnoten k2) {
        int manDist = Math.abs(k1.x() - k2.x()) + Math.abs(k1.y() - k2.y());
        return manDist <= lbg ? manDist : Integer.MAX_VALUE;
    }

    public boolean addTelKnoten(int x, int y) {
        TelKnoten toAdd = new TelKnoten(x, y);
        if (knoten.contains(toAdd)) {
            return false;
        } else {
            for (TelKnoten k : knoten) {
                kanten.add(new TelVerbindung(toAdd, k, manhattanDistanz(k, toAdd)));
            }
            knoten.add(toAdd);
            return true;
        }
    }

    public boolean computeOptTelNet() {
        UnionFind<TelKnoten> forest = new UnionFind<>(knoten);
        LinkedList<TelVerbindung> minSpanTree = new LinkedList<>();

        while (forest.size() != 1 && !kanten.isEmpty()) {
            TelVerbindung verb = kanten.poll();
            TelKnoten t1 = forest.find(verb.u());
            TelKnoten t2 = forest.find(verb.v());
            if (!t1.equals(t2)) {
                forest.union(t1, t2);
                minSpanTree.add(verb);
            }
        }

        if (forest.size() != 1) {
            return false;
        } else {
            this.minSpanTree = minSpanTree;
            return true;
        }
    }

    public List<TelVerbindung> getOptTelNet() throws IllegalStateException {
        if (minSpanTree == null) {
            throw new IllegalStateException();
        }
        return minSpanTree;
    }

    public int getOptTelNetKosten() throws IllegalStateException {
        if (minSpanTree == null) {
            throw new IllegalStateException();
        }
        int res = 0;
        for (TelVerbindung v : minSpanTree) {
            res += v.c();
        }
        return res;
    }

    public void drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        if (minSpanTree == null) {
            throw new IllegalStateException();
        }

        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);

        // Draw lines


        for (TelVerbindung v : minSpanTree) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(v.u().x(), v.u().y(), v.v().x(), v.v().y());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.circle(v.u().x(), v.u().y(), 4.);
            StdDraw.circle(v.v().x(), v.v().y(), 4.);
        }
    }

    public void generateRandomTelNet(int n, int xMax, int yMax) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            addTelKnoten(r.nextInt(xMax + 1), r.nextInt(yMax + 1));
        }
    }

    public int size() {
        return knoten.size();
    }

    public static void testAbb3() {
        // Abb. 3
        TelNet tn = new TelNet(7);

        tn.addTelKnoten(1, 1);
        tn.addTelKnoten(3, 1);
        tn.addTelKnoten(4, 2);
        tn.addTelKnoten(3, 4);
        tn.addTelKnoten(2, 6);
        tn.addTelKnoten(4, 7);
        tn.addTelKnoten(7, 6);

        tn.computeOptTelNet();

        System.out.println(tn.getOptTelNetKosten()); // 17

        tn.drawOptTelNet(7, 7);
    }

    public static void testRandom() {
        TelNet tn = new TelNet(100);

        int xs = 1000;
        int ys = 1000;

        tn.generateRandomTelNet(1000, xs, ys);

        tn.computeOptTelNet();

        tn.drawOptTelNet(xs, ys);
    }

    public static void main(String[] args) {
        //TelNet.testAbb3();
        TelNet.testRandom();
    }

}
